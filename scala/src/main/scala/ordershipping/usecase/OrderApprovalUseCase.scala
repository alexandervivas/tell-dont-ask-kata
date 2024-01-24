package ordershipping.usecase

import ordershipping.repository.OrderRepository
import ordershipping.request.OrderApprovalRequest

case class OrderApprovalUseCase(orderRepository: OrderRepository) {
  def run(request: OrderApprovalRequest): Unit = {
    orderRepository
      .getById(request.orderId)
      .foreach(order => {
        orderRepository.save(order.approve(request.approved))
      })
  }
}
