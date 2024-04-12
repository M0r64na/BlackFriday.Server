package application.service;

import application.service.interfaces.ICampaignService;
import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import data.model.entity.Campaign;
import data.model.entity.CampaignItem;
import data.model.entity.CampaignStop;
import data.model.entity.Product;
import data.repository.interfaces.ICampaignRepository;
import data.repository.interfaces.ICampaignStopRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CampaignService implements ICampaignService {
    private final ICampaignRepository campaignRepository;
    private final ICampaignStopRepository campaignStopRepository;
    private final IUserService userService;
    private final IProductService productService;

    public CampaignService(ICampaignRepository campaignRepository, ICampaignStopRepository campaignStopRepository, IUserService userService, IProductService productService) {
        this.campaignRepository = campaignRepository;
        this.campaignStopRepository = campaignStopRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void startCampaign(String username, Map<String, Double> productNamesAndDiscountPercentages) {
        if(this.isActiveCampaignPresent())
            throw new IllegalStateException("Active campaign present. New campaign cannot be created until the active one is stopped");

        Campaign campaign = new Campaign(this.userService.getUserByUsername(username));
        this.applyDiscountProductPricesAndCreateCampaignItems(username, campaign, productNamesAndDiscountPercentages);
        this.campaignRepository.create(campaign);
    }

    @Override
    public void stopCurrentCampaign(String username) {
        if(!this.isActiveCampaignPresent()) throw new IllegalStateException("No active campaign present to be stopped");

        Campaign campaign = this.getLastCreatedCampaign();
        this.restoreProductPrices(username, campaign);

        CampaignStop campaignStop = new CampaignStop(campaign, this.userService.getUserByUsername(username));
        this.campaignStopRepository.create(campaignStop);
    }

    @Override
    public Campaign getLastCreatedCampaign() {
        return this.campaignRepository.findLastCreated();
    }

    private boolean isActiveCampaignPresent() {
        Campaign campaign = this.getLastCreatedCampaign();

        if(campaign != null) {
            CampaignStop campaignStop = this.campaignStopRepository.findByCampaignId(campaign.getId());

            return campaignStop == null;
        }

        return false;
    }

    private void createCampaignItem(Campaign campaign, Product product, double discountPercentage) {
        CampaignItem campaignItem = new CampaignItem(campaign, product, discountPercentage);
        campaign.getItems().add(campaignItem);
    }

    private void applyDiscountProductPricesAndCreateCampaignItems(String username, Campaign campaign,
                                                           Map<String, Double> productNamesAndDiscountPercentages) {
        for(String productName : productNamesAndDiscountPercentages.keySet()) {
            Product product = this.productService.getProductByName(productName);

            double discountPercentage = productNamesAndDiscountPercentages.get(productName);
            BigDecimal priceWithDiscount = product.getCurrPrice()
                    .multiply(BigDecimal.valueOf(1 - discountPercentage / 100));
            product = this.productService.updateProduct(productName, product.getDescription(), product.getNumberInStock(),
                    product.getMinPrice(), priceWithDiscount, username);

            this.createCampaignItem(campaign, product, discountPercentage);
        }
    }

    private void restoreProductPrices(String username, Campaign campaign) {
        Set<CampaignItem> campaignItems = campaign.getItems();

        for(CampaignItem campaignItem : campaignItems) {
            Product product = campaignItem.getProduct();

            double discountPercentage = campaignItem.getDiscountPercentage();
            BigDecimal priceWithoutDiscount = product.getCurrPrice()
                    .divide(BigDecimal.valueOf(1 - discountPercentage / 100), RoundingMode.CEILING);
            this.productService.updateProduct(product.getName(), product.getDescription(), product.getNumberInStock(),
                    product.getMinPrice(), priceWithoutDiscount, username);
        }
    }
}