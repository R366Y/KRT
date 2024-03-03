package renders

import inoneweekend.*
import java.io.File
import kotlin.random.Random


fun main() {

    val random = Random.Default

    fun rayColor(r: Ray, world: Hittable, depth: Int): Color {
        // If we've exceeded the ray bounce limit, no more light is gathered
        if (depth <= 0)
            return Color(0.0, 0.0, 0.0)

        val h = world.hit(r, 0.001, infinity)
        if (h != null) {
            val target = h.p + h.normal + randomInUnitSphere()
            return 0.5 * rayColor(Ray(h.p, target - h.p), world, depth - 1)
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
    val maxDepth = 50

    // World
    val world = HittableList()
    world.add(Sphere(Point3(0.0, 0.0, -1.0), 0.5))
    world.add(Sphere(Point3(0.0, -100.5, -1.0), 100.0))

    // Camera
    val camera = Camera()

    // Render
    val image = buildString {
        val ppmHeader = "P3\n$imageWidth $imageHeight\n255\n"
        append(ppmHeader)

        for (j in imageHeight - 1 downTo 0) {
            System.err.print("\rScalines remaining: $j ")
            for (i in 0 until imageWidth) {
                var pixelColor = Color(0.0, 0.0, 0.0)
                for (s in 0 until samplesPerPixel) {
                    val u = (i + random.nextDouble()) / (imageWidth - 1)
                    val v = (j + random.nextDouble()) / (imageHeight - 1)
                    val r = camera.getRay(u, v)
                    pixelColor += rayColor(r, world, 50)
                }
                append(writeColor(pixelColor, samplesPerPixel))
            }
        }
    }
    File("image7.ppm").delete()
    File("image7.ppm").appendText(image)
}