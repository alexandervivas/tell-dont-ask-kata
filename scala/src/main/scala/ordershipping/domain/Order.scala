package ordershipping.domain

import ordershipping.domain.OrderStatus.OrderStatus
import ordershipping.exception.{ApprovedOrderCannotBeRejectedException, RejectedOrderCannotBeApprovedException, ShippedOrdersCannotBeChangedException}

import scala.collection.mutable

case class Order(
     currency: String = "",
     status: OrderStatus,
     id: Int,
     orderItems:  List[OrderItem]
) {
  val total: Double = orderItems.map(i => i.taxedAmount).sum
  val orderTax: Double = orderItems.map(i => i.tax).sum

  @throws(classOf[ShippedOrdersCannotBeChangedException])
  @throws(classOf[RejectedOrderCannotBeApprovedException])
  @throws(classOf[ApprovedOrderCannotBeRejectedException])
  def approveOrder(approved: Boolean): Order = {
    if (status == OrderStatus.Shipped)
      throw new ShippedOrdersCannotBeChangedException
    if (approved && status == OrderStatus.Rejected)
      throw new RejectedOrderCannotBeApprovedException
    if (!approved && status == OrderStatus.Approved)
      throw new ApprovedOrderCannotBeRejectedException
    copy(status = if (approved) OrderStatus.Approved else OrderStatus.Rejected)
  }
}
