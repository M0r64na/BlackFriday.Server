package application.service.interfaces;

import data.model.entity.Status;
import data.model.entity.enums.OrderStatus;

public interface IStatusService {
    Status findStatusByName(OrderStatus orderStatus);
    void initializeStatuses();
}