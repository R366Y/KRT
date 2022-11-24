package renders

import inoneweekend.*
import java.io.File
import kotlin.math.sqrt
import kotlin.random.Random


fun main() {

    val random = Random.Default

    fun rayColor(r: Ray, world: Hittable): Color {
        val h = world.hit(r, 0.0, infinity)
        if (h != null) {
            return 0.5 * (h.normal + Color(1.0, 1.0, 1.0))
        }
        val unitDirection = r.direction.unitVector()
        val t = 0.5 * (unitDirection.y + 1.0)
        return (1.0 - t) * Color(1.0, 1.0, 1.0) + t * Color(0.5, 0.7, 1.0)
    }

    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 100

    // World
    val world = HittableList()
    world.add(Sphere(Point3(0.0, 0.0, -1.0), 0.5))
    world.add(Sphere(Point3(0.0, -100.5, -1.0), 100.0))

    // Camera
    val camera = Camera()

    // Render
    var image = ""
    val ppmHeader = "P3\n$imageWidth $imageHeight\n255\n"
    image += ppmHeader

    for (j in imageHeight - 1 downTo 0) {
        System.err.print("\rScalines remaining: $j ")
        for (i in 0 until imageWidth) {
            var pixelColor = Color(0.0, 0.0, 0.0)
            for(s in 0 until  samplesPerPixel){
                val u = (i + random.nextDouble()) / (imageWidth - 1)
                val v = (j + random.nextDouble()) / (imageHeight - 1)
                val r = camera.getRay(u, v)
                pixelColor = pixelColor + rayColor(r, world)
            }
            image += writeColor(pixelColor, samplesPerPixel)
        }
    }
    File("image6.ppm").delete()
    File("image6.ppm").appendText(image)
}