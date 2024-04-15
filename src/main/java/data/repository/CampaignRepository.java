package data.repository;

import data.domain.Campaign;
import data.repository.base.RepositoryBase;
import data.repository.interfaces.ICampaignRepository;
import org.hibernate.Session;

public class CampaignRepository extends RepositoryBase<Campaign> implements ICampaignRepository {
    @Override
    public Campaign findLastCreated() {
        Session newSession = sessionFactory.openSession();

        Campaign res = newSession
                .createQuery("FROM Campaign c ORDER BY c.startedOn DESC", Campaign.class)
                .setMaxResults(1)
                .uniqueResult();

        newSession.close();

        return res;
    }
}