package ordershipping.usecase

import ordershipping.domain.{Order, OrderItem, OrderStatus}
import ordershipping.exception.UnknownProductException
import ordershipping.repository.{OrderRepository, ProductCatalog}
import ordershipping.request.SellItemsRequest

import scala.collection.mutable

class OrderCreationUseCase(
    val orderRepository: OrderRepository,
    val productCatalog: ProductCatalog
) {
  def run(request: SellItemsRequest): Unit = {

    val orderItems = request.requests.flatMap {  itemRequest =>
      val product = productCatalog.getByName(itemRequest.productName)
      if (product.isEmpty)
        throw new UnknownProductException
      else {
        product.map(p => OrderItem(p, itemRequest.quantity))
      }
    }

    val order = Order(
      currency = "EUR",
      status = OrderStatus.Created,
      id = 1,
      orderItems = orderItems
    )
    orderRepository.save(order)
  }
}
