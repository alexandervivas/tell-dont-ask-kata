package ordershipping.domain

import ordershipping.request.SellItemRequest
import ordershipping.utils.MathHelpers.roundAt


class OrderItem(
    val product: ordershipping.domain.Product,
    var quantity: Int,
    var taxedAmount: Double,
    var tax: Double
) {

  def computeTax(itemRequest:SellItemRequest): Unit = {

    val unitaryTax =
      roundAt(2)((product.price / 100) * product.category.taxPercentage)
    val unitaryTaxedAmount = roundAt(2)(product.price + unitaryTax)
    taxedAmount =
      roundAt(2)(unitaryTaxedAmount * itemRequest.quantity)
     tax = roundAt(2)(unitaryTax * itemRequest.quantity)
  }


}
