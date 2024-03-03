package renders

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.PngWriter
import inoneweekend.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.random.Random


fun main() {

    val random = Random.Default

    fun rayColor(r: Ray, world: Hittable, depth: Int): Color {
        // If we've exceeded the ray bounce limit, no more light is gathered
        if (depth <= 0)
            return Color(0.0, 0.0, 0.0)

        val rec = world.hit(r, 0.001, infinity)
        rec?.apply {
            val scatter = material?.scatter(r, this)
            return scatter?.let {
                it.attenuation * rayColor(it.scattered, world, depth - 1)
            } ?: Color(0.0, 0.0, 0.0)
        }

        val unitDirection = r.direction.unitVector()
        val t = 0.5 * (unitDirection.y + 1.0)
        return (1.0 - t) * Color(1.0, 1.0, 1.0) + t * Color(0.5, 0.7, 1.0)
    }

    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 500
    val maxDepth = 50

    // World
    val world = HittableList()
    val materialGround = Lambertian(Color(0.8, 0.8, 0.0))
    val materialCenter = Lambertian(Color(0.1, 0.2, 0.5))
    val materialLeft = Dielectric(1.5)
    val materialRight = Metal(Color(0.8, 0.6, 0.2), 0.0)

    world.add(Sphere(Point3(0.0, -100.5, -1.0), 100.0, materialGround))
    world.add(Sphere(Point3(0.0, 0.0, -1.0), 0.5, materialCenter))
    world.add(Sphere(Point3(-1.0, 0.0, -1.0), 0.5, materialLeft))
    world.add(Sphere(Point3(-1.0, 0.0, -1.0), -0.4, materialLeft))
    world.add(Sphere(Point3(1.0, 0.0, -1.0), 0.5, materialRight))

    // Camera
    val camera = Camera()

    // Render
    var image = ImmutableImage.create(imageWidth, imageHeight)

    for (j in imageHeight - 1 downTo 0) {
        System.err.print("\rScanlines remaining: $j ")
        runBlocking {
            withContext(Dispatchers.Default) {
                for (i in 0 until imageWidth) {
                    launch {
                        var pixelColor = Color(0.0, 0.0, 0.0)
                        for (s in 0 until samplesPerPixel) {
                            val u = (i + random.nextDouble()) / (imageWidth - 1)
                            val v = (j + random.nextDouble()) / (imageHeight - 1)
                            val r = camera.getRay(u, v)
                            pixelColor += rayColor(r, world, maxDepth)
                        }
                        writeColor(image, i, j, pixelColor, samplesPerPixel)
                    }
                }
            }
        }
    }
    image = image.flipY()
    image.output(PngWriter.NoCompression, File("image9.png"))
}