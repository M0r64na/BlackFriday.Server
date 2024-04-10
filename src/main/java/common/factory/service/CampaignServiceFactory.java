package common.factory.service;

import application.service.CampaignService;
import application.service.interfaces.ICampaignService;
import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import common.factory.repository.CampaignRepositoryFactory;
import common.factory.repository.CampaignStopRepositoryFactory;
import data.repository.interfaces.ICampaignRepository;
import data.repository.interfaces.ICampaignStopRepository;

public final class CampaignServiceFactory {
    private static ICampaignService instance;

    private CampaignServiceFactory() {}

    public static ICampaignService getInstance() {
        if(instance == null) {
            ICampaignRepository campaignRepository = CampaignRepositoryFactory.getInstance();
            ICampaignStopRepository campaignStopRepository = CampaignStopRepositoryFactory.getInstance();
            IUserService userService = UserServiceFactory.getInstance();
            IProductService productService = ProductServiceFactory.getInstance();
            instance = new CampaignService(campaignRepository, campaignStopRepository, userService, productService);
        }

        return instance;
    }
}