package renders

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.color.RGBColor
import com.sksamuel.scrimage.nio.PngWriter
import java.awt.Color
import java.io.File

fun main() {
    var image = ImmutableImage.create(1200, 400)
    image = image.fill(Color.BLACK)
    for (x in 0 until 1200)
        image.setColor(x, 200, RGBColor(255, 0, 0))
    image.output(PngWriter.NoCompression, File("test_img.png"))
}