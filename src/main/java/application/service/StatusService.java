package application.service;

import application.service.interfaces.IStatusService;
import data.domain.Status;
import data.domain.enums.OrderStatus;
import data.repository.interfaces.IStatusRepository;

public class StatusService implements IStatusService {
    private final IStatusRepository statusRepository;

    public StatusService(IStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status findStatusByName(OrderStatus orderStatus) {
        return this.statusRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public void initializeStatuses() {
        if(!this.statusRepository.getAll().isEmpty()) return;

        this.statusRepository.create(new Status(OrderStatus.IN_PROCESS));
        this.statusRepository.create(new Status(OrderStatus.SHIPPING));
        this.statusRepository.create(new Status(OrderStatus.DELIVERED));
    }
}