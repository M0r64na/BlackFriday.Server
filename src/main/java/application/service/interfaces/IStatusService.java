package application.service.interfaces;

import data.domain.Status;
import data.domain.enums.OrderStatus;

public interface IStatusService {
    Status findStatusByName(OrderStatus orderStatus);
    void initializeStatuses();
}