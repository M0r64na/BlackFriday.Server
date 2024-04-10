package common.factory.service;

import application.service.StatusService;
import application.service.interfaces.IStatusService;
import common.factory.repository.StatusRepositoryFactory;
import data.repository.interfaces.IStatusRepository;

public final class StatusServiceFactory {
    private static IStatusService instance = null;

    private StatusServiceFactory() {}

    public static IStatusService getInstance() {
        if(instance == null) {
            IStatusRepository statusRepository = StatusRepositoryFactory.getInstance();
            instance = new StatusService(statusRepository);
        }

        return instance;
    }
}