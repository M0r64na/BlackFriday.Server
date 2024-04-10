package common.factory.repository;

import data.repository.StatusRepository;
import data.repository.interfaces.IStatusRepository;

public final class StatusRepositoryFactory {
    private static IStatusRepository instance = null;

    private StatusRepositoryFactory() {}

    public static IStatusRepository getInstance() {
        if(instance == null) instance = new StatusRepository();

        return instance;
    }
}