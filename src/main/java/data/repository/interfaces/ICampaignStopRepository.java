package data.repository.interfaces;

import data.domain.CampaignStop;
import data.repository.base.IRepository;
import java.util.UUID;

public interface ICampaignStopRepository extends IRepository<CampaignStop> {
    CampaignStop findByCampaignId(UUID campaignId);
}