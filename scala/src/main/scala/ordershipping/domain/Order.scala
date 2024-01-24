package ordershipping.domain

import ordershipping.domain.OrderStatus.OrderStatus
import ordershipping.exception.{ApprovedOrderCannotBeRejectedException, OrderCannotBeShippedException, OrderCannotBeShippedTwiceException, RejectedOrderCannotBeApprovedException, ShippedOrdersCannotBeChangedException}

import scala.collection.mutable

case class Order(
     currency: String = "",
     status: OrderStatus,
id: Int,
orderItems:  List[OrderItem]
) {
  val total: Double = orderItems.map(i => i.taxedAmount).sum
  val orderTax: Double = orderItems.map(i => i.tax).sum
  private def ensureCanBeApproved(approved: Boolean): Unit = {
    if (status == OrderStatus.Shipped)
      throw new ShippedOrdersCannotBeChangedException
    if (approved && status == OrderStatus.Rejected)
      throw new RejectedOrderCannotBeApprovedException
    if (!approved && status == OrderStatus.Approved)
      throw new ApprovedOrderCannotBeRejectedException
  }
  private def ensureCanBeShipped(): Unit = {
    if (
      status == OrderStatus.Created ||
        status == OrderStatus.Rejected
    ) throw new OrderCannotBeShippedException

    if (status == OrderStatus.Shipped)
      throw new OrderCannotBeShippedTwiceException
  }

  @throws(classOf[ShippedOrdersCannotBeChangedException])
  @throws(classOf[RejectedOrderCannotBeApprovedException])
  @throws(classOf[ApprovedOrderCannotBeRejectedException])
  def approve(approved: Boolean): Order = {
    ensureCanBeApproved(approved)
    copy(status = if (approved) OrderStatus.Approved else OrderStatus.Rejected)
  }


  def shipOrder(): Order = {
    ensureCanBeShipped()
    copy(status = OrderStatus.Shipped)
  }

}
