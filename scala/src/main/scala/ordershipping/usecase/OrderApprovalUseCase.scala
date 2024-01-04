package ordershipping.usecase

import ordershipping.domain.OrderStatus
import ordershipping.exception.{ApprovedOrderCannotBeRejectedException, RejectedOrderCannotBeApprovedException, ShippedOrdersCannotBeChangedException}
import ordershipping.repository.OrderRepository
import ordershipping.request.OrderApprovalRequest

class OrderApprovalUseCase(val orderRepository: OrderRepository) {
  def run(request: OrderApprovalRequest): Unit = {
    orderRepository
      .getById(request.orderId)
      .foreach(order => {
        /*if (order.status == OrderStatus.Shipped)
          throw new ShippedOrdersCannotBeChangedException
        if (request.approved && order.status == OrderStatus.Rejected)
          throw new RejectedOrderCannotBeApprovedException
        if (!request.approved && order.status == OrderStatus.Approved)
          throw new ApprovedOrderCannotBeRejectedException

        order.status =
          if (request.approved) OrderStatus.Approved else OrderStatus.Rejected*/
        order.approveOrder(request.approved)
        orderRepository.save(order)
      })
  }
}
