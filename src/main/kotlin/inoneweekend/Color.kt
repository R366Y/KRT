package inoneweekend

import kotlin.math.round

fun writeColor(pixelColor: Color): String {
    val ir = (256 * pixelColor.x.coerceIn(0.0, 0.999)).toInt()
    val ig = (256 * pixelColor.y.coerceIn(0.0, 0.999)).toInt()
    val ib = (256 * pixelColor.z.coerceIn(0.0, 0.999)).toInt()
    return "$ir $ig $ib\n"
}

fun writeColor(pixelColor: Color, samplesPerPixel: Int): String {
    var r = pixelColor.x
    var g = pixelColor.y
    var b = pixelColor.z

    // Divide color by the number of samples
    val scale = 1.0 / samplesPerPixel
    r *= scale
    g *= scale
    b *= scale

    val ir = (256 * r.coerceIn(0.0, 0.999)).toInt()
    val ig = (256 * g.coerceIn(0.0, 0.999)).toInt()
    val ib = (256 * b.coerceIn(0.0, 0.999)).toInt()
    return "$ir $ig $ib\n"
}