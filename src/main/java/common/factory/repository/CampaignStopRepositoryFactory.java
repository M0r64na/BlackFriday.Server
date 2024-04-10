package common.factory.repository;

import data.repository.CampaignStopRepository;
import data.repository.interfaces.ICampaignStopRepository;

public final class CampaignStopRepositoryFactory {
    private static ICampaignStopRepository instance = null;

    private CampaignStopRepositoryFactory() {}

    public static ICampaignStopRepository getInstance() {
        if(instance == null) instance = new CampaignStopRepository();

        return instance;
    }
}