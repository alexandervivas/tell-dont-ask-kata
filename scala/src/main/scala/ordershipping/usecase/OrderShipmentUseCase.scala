package ordershipping.usecase

import ordershipping.domain.OrderStatus
import ordershipping.exception.{OrderCannotBeShippedException, OrderCannotBeShippedTwiceException}
import ordershipping.repository.OrderRepository
import ordershipping.request.OrderShipmentRequest
import ordershipping.service.ShipmentService

case class OrderShipmentUseCase(
     orderRepository: OrderRepository,
     shipmentService: ShipmentService
) {
  def run(request: OrderShipmentRequest): Unit = {
    orderRepository
      .getById(request.orderId)
      .foreach(order => {


        shipmentService.ship(order)
        //order.status = OrderStatus.Shipped
        orderRepository.save(order.shipOrder())
      })
  }
}
