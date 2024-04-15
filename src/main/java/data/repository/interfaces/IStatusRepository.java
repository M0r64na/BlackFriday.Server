package data.repository.interfaces;

import data.domain.Status;
import data.domain.enums.OrderStatus;
import data.repository.base.IRepository;

public interface IStatusRepository extends IRepository<Status> {
    Status findByOrderStatus(OrderStatus orderStatus);
}