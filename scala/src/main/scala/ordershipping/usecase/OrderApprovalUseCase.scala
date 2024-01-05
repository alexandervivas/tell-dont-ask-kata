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
        orderRepository.save(order.approveOrder(request.approved))
      })
  }
}
