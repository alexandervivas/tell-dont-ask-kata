package ordershipping.domain
//TODO transform into ADT
object OrderStatus extends Enumeration {
  type OrderStatus = Value
  val Approved, Rejected, Shipped, Created = Value
}
