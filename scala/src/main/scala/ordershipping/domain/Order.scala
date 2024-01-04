package ordershipping.domain

import ordershipping.domain.OrderStatus.OrderStatus
import ordershipping.exception.{ApprovedOrderCannotBeRejectedException, RejectedOrderCannotBeApprovedException, ShippedOrdersCannotBeChangedException}

import scala.collection.mutable

class Order(
    var currency: String = "",
    var status: OrderStatus,
    var id: Int
) {
  var total: Double = 0
  var items: List[OrderItem] = List.empty
  var tax: Double = 0

  def addItem(orderItem: OrderItem): Unit = {
    items += orderItem
    total += orderItem.taxedAmount
    tax += orderItem.tax
  }

  def approveOrder() = {
    if (status == OrderStatus.Shipped)
      throw new ShippedOrdersCannotBeChangedException
    if (request.approved && order.status == OrderStatus.Rejected)
      throw new RejectedOrderCannotBeApprovedException
    if (!request.approved && order.status == OrderStatus.Approved)
      throw new ApprovedOrderCannotBeRejectedException

    order.status =
      if (request.approved) OrderStatus.Approved else OrderStatus.Rejected
  }
}
