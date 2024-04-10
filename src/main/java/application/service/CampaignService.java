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
import java.rmi.RemoteException;
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
    public void startCampaign(String username, Map<String, Double> productNamesAndDiscountPercentages) throws RemoteException {
        if(this.isActiveCampaignPresent())
            throw new IllegalStateException("Active campaign present. New campaign cannot be created until the active one is stopped");

        Campaign campaign = new Campaign(this.userService.getUserByUsername(username));
        this.applyDiscountProductPricesAndCreateCampaignItems(username, campaign, productNamesAndDiscountPercentages);
        this.campaignRepository.create(campaign);
    }

    @Override
    public void stopCurrentCampaign(String username) throws RemoteException {
        if(!this.isActiveCampaignPresent()) throw new IllegalStateException("No active campaign present to be stopped");

        List<Campaign> campaigns = this.campaignRepository.getAll();
        Campaign campaign = campaigns.get(campaigns.size() - 1);
        this.restoreProductPrices(username, campaign);

        CampaignStop campaignStop = new CampaignStop(campaign, this.userService.getUserByUsername(username));
        this.campaignStopRepository.create(campaignStop);
    }

    private boolean isActiveCampaignPresent() {
        List<Campaign> campaigns = this.campaignRepository.getAll();

        if(!campaigns.isEmpty()) {
            Campaign currentCampaign = campaigns.get(campaigns.size() - 1);
            CampaignStop campaignStop = this.campaignStopRepository.findByCampaignId(currentCampaign.getId());

            return campaignStop == null;
        }

        return false;
    }

    private void createCampaignItem(Campaign campaign, Product product, double discountPercentage) {
        CampaignItem campaignItem = new CampaignItem(campaign, product, discountPercentage);
        campaign.getItems().add(campaignItem);
    }

    private void applyDiscountProductPricesAndCreateCampaignItems(String username, Campaign campaign,
                                                           Map<String, Double> productNamesAndDiscountPercentages) throws RemoteException {
        for(String productName : productNamesAndDiscountPercentages.keySet()) {
            Product product = this.productService.findOrderByName(productName);

            double discountPercentage = productNamesAndDiscountPercentages.get(productName);
            BigDecimal priceWithDiscount = product.getCurrPrice()
                    .multiply(BigDecimal.valueOf(1 - discountPercentage / 100));
            this.productService.updateCurrPriceOfProduct(productName, priceWithDiscount, username);

            this.createCampaignItem(campaign, product, discountPercentage);
        }
    }

    private void restoreProductPrices(String username, Campaign campaign) throws RemoteException {
        Set<CampaignItem> campaignItems = campaign.getItems();

        for(CampaignItem campaignItem : campaignItems) {
            Product product = campaignItem.getProduct();

            double discountPercentage = campaignItem.getDiscountPercentage();
            BigDecimal priceWithoutDiscount = product.getCurrPrice()
                    .divide(BigDecimal.valueOf(1 - discountPercentage / 100), RoundingMode.UNNECESSARY);
            this.productService.updateCurrPriceOfProduct(product.getName(), priceWithoutDiscount, username);
        }
    }
}