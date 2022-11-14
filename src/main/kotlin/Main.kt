import java.io.File
import kotlin.math.round

const val imageWidth = 256
const val imageHeight = 256

fun main(args: Array<String>) {
    var image = ""
    val ppmHeader = "P3\n$imageWidth $imageHeight\n255\n"
    image += ppmHeader

    for (j in imageHeight - 1 downTo 0) {
        System.err.println("\rScalines remaining: $j ")
        System.err.flush()
        for (i in 0 until imageWidth) {
            val r = i.toDouble() / (imageWidth - 1)
            val g = j.toDouble() / (imageHeight - 1)
            val b = 0.25

            val ir = round(255.999 * r)
            val ig = round(255.999 * g)
            val ib = round(255.999 * b)

            val s = "$ir $ig $ib\n"
            image += s
        }
    }
    File("image.ppm").appendText(image)
}