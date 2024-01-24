package ordershipping.domain

import ordershipping.exception._

case class Order(
                  currency: String = "",
                  status: OrderStatus,
                  id: Int,
                  items:  List[OrderItem]
) {
  val total: Double = items.map(i => i.taxedAmount).sum
  val tax: Double = items.map(i => i.tax).sum
  private def ensureCanBeApproved(approved: Boolean): Unit = {
    if (status == OrderStatus.Shipped)
      throw new ShippedOrdersCannotBeChangedException
    if (approved && status == OrderStatus.Rejected)
      throw new RejectedOrderCannotBeApprovedException
    if (!approved && status == OrderStatus.Approved)
      throw new ApprovedOrderCannotBeRejectedException
  }
  def ensureCanBeShipped(): Unit = {
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

  def markAsShipped(): Order = {
    copy(status = OrderStatus.Shipped)
  }
}
