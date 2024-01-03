package ordershipping.domain

class OrderItem(
    val product: ordershipping.domain.Product,
    var quantity: Int,
    var taxedAmount: Double,
    var tax: Double
) {
  val unitaryTax =
    roundAt(2)((p.price / 100) * p.category.taxPercentage)
  val unitaryTaxedAmount = roundAt(2)(p.price + unitaryTax)
  val taxedAmount =
    roundAt(2)(unitaryTaxedAmount * itemRequest.quantity)
  val taxAmount = roundAt(2)(unitaryTax * itemRequest.quantity)


}
