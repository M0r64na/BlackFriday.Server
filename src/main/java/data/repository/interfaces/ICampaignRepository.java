package data.repository.interfaces;

import data.domain.Campaign;
import data.repository.base.IRepository;

public interface ICampaignRepository extends IRepository<Campaign> {
    Campaign findLastCreated();
}