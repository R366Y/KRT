import inoneweekend.Color
import inoneweekend.writeColor
import java.io.File
import kotlin.math.round

const val imageWidth = 256
const val imageHeight = 256

fun main() {
    var image = ""
    val ppmHeader = "P3\n$imageWidth $imageHeight\n255\n"
    image += ppmHeader

    for (j in imageHeight - 1 downTo 0) {
        System.err.println("\rScalines remaining: $j ")
        for (i in 0 until imageWidth) {
            val r = i.toDouble() / (imageWidth - 1)
            val g = j.toDouble() / (imageHeight - 1)
            val b = 0.25

            val pixelColor = Color(r, g, b)
            val s = writeColor(pixelColor)
            image += s
        }
    }
    File("image.ppm").appendText(image)
}