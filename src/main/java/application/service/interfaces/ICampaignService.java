package application.service.interfaces;

import data.model.entity.Campaign;

import java.util.Map;

public interface ICampaignService {
    void startCampaign(String username, Map<String, Double> productNamesAndDiscountPercentages);
    void stopCurrentCampaign(String username);

    Campaign getLastCreatedCampaign();
}