package ordershipping.domain

import ordershipping.domain.OrderStatus.OrderStatus
import ordershipping.exception.{ApprovedOrderCannotBeRejectedException, RejectedOrderCannotBeApprovedException, ShippedOrdersCannotBeChangedException}

import scala.collection.mutable

class Order(
    var total: Double = 0,
    var currency: String = "",
    var items: mutable.MutableList[OrderItem] = mutable.MutableList.empty,
    var tax: Double = 0,
    var status: OrderStatus,
    var id: Int
) {
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
