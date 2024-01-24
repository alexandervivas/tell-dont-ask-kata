package ordershipping.usecase

import ordershipping.domain.{Order, OrderItem, OrderStatus}
import ordershipping.exception.UnknownProductException
import ordershipping.repository.{OrderRepository, ProductCatalog}
import ordershipping.request.SellItemsRequest

case class OrderCreationUseCase(
     orderRepository: OrderRepository,
     productCatalog: ProductCatalog
) {
  def run(request: SellItemsRequest): Unit = {
    val orderItems = request.requests.map { itemRequest =>
      val product = productCatalog.getByName(itemRequest.productName)
      product.fold(throw new UnknownProductException) { product =>
        OrderItem(product, itemRequest.quantity)
      }
    }

    val order = Order(
      currency = "EUR",
      status = OrderStatus.Created,
      id = 1,
      items = orderItems
    )
    orderRepository.save(order)
  }
}
