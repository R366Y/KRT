package inoneweekend

import kotlin.math.round

fun writeColor(pixelColor: Color): String {
    val ir = (256 * pixelColor.x.coerceIn(0.0, 0.999)).toInt()
    val ig = (256 * pixelColor.y.coerceIn(0.0, 0.999)).toInt()
    val ib = (256 * pixelColor.z.coerceIn(0.0, 0.999)).toInt()
    return "$ir $ig $ib\n"
}