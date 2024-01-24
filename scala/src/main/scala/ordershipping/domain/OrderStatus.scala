package ordershipping.domain

sealed trait OrderStatus
object OrderStatus {
  final case object Approved extends OrderStatus
  final case object Rejected extends OrderStatus
  final case object Shipped extends OrderStatus
  final case object Created extends OrderStatus
}