package ordershipping.utils

object MathHelpers {

   def roundAt(p: Int)(n: Double): Double = {
    val s = math pow(10, p)
    (math round n * s) / s
  }

}
