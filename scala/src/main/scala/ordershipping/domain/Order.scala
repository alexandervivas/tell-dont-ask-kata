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


  def addItem(orderItem: OrderItem): Unit = {
    items += orderItem
    total += orderItem.taxedAmount
  }
  def computeTax(): Double = items.map(i => i.tax).sum

  def approveOrder(approved: Boolean): Unit = {
    if (status == OrderStatus.Shipped)
      throw new ShippedOrdersCannotBeChangedException
    if (approved && status == OrderStatus.Rejected)
      throw new RejectedOrderCannotBeApprovedException
    if (!approved && status == OrderStatus.Approved)
      throw new ApprovedOrderCannotBeRejectedException

   status =
      if (approved) OrderStatus.Approved else OrderStatus.Rejected
  }
}
