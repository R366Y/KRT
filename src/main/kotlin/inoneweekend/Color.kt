package inoneweekend

fun writeColor(pixelColor: Color): String {
    val ir = (255.999 * pixelColor.x).toInt()
    val ig = (255.999 * pixelColor.y).toInt()
    val ib = (255.999 * pixelColor.z).toInt()
    return "$ir $ig $ib\n"
}