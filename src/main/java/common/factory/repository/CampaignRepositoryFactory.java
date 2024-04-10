package common.factory.repository;

import data.repository.CampaignRepository;
import data.repository.interfaces.ICampaignRepository;

public final class CampaignRepositoryFactory {
    private static ICampaignRepository instance;

    private CampaignRepositoryFactory() {}

    public static ICampaignRepository getInstance() {
        if(instance == null) instance = new CampaignRepository();

        return instance;
    }
}