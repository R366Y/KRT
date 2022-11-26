package inoneweekend

import com.sksamuel.scrimage.MutableImage
import com.sksamuel.scrimage.color.RGBColor
import kotlin.math.round
import kotlin.math.sqrt

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

    // Divide color by the number of samples and gamma-correct for gamma=2.0
    val scale = 1.0 / samplesPerPixel
    r = sqrt( scale * r)
    g = sqrt(scale * g)
    b = sqrt(scale * b)

    val ir = (256 * r.coerceIn(0.0, 0.999)).toInt()
    val ig = (256 * g.coerceIn(0.0, 0.999)).toInt()
    val ib = (256 * b.coerceIn(0.0, 0.999)).toInt()
    return "$ir $ig $ib\n"
}

fun writeColor(image: MutableImage, x:Int, y: Int, pixelColor: Color, samplesPerPixel: Int) {
    var r = pixelColor.x
    var g = pixelColor.y
    var b = pixelColor.z

    // Divide color by the number of samples and gamma-correct for gamma=2.0
    val scale = 1.0 / samplesPerPixel
    r = sqrt( scale * r)
    g = sqrt(scale * g)
    b = sqrt(scale * b)

    val ir = (256 * r.coerceIn(0.0, 0.999)).toInt()
    val ig = (256 * g.coerceIn(0.0, 0.999)).toInt()
    val ib = (256 * b.coerceIn(0.0, 0.999)).toInt()
    image.setColor(x, y, RGBColor(ir, ig, ib))
}