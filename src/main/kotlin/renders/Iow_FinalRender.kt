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

fun randomScene(): Hittable {
    val world = HittableList()
    val groundMaterial = Lambertian(Color(0.5, 0.5, 0.5))
    world.add(Sphere(Point3(0.0, -1000.0, 0.0), 1000.0, groundMaterial))
    val n = 5

    for (a in -n until n) {
        for (b in -n until n) {
            val choseMat = randomDouble()
            val center = Point3(a + 0.9 * randomDouble(), 0.2, b + 0.9 * randomDouble())

            if ((center - Point3(4.0, 0.2, 0.0)).length() > 0.9) {

                val sphereMaterial: Material = if (choseMat < 0.8) {
                    // diffuse
                    val albedo = randomVector() * randomVector()
                    Lambertian(albedo)
                } else if (choseMat < 0.95) {
                    // metal
                    val albedo = randomVector(0.5, 1.0)
                    val fuzz = randomDouble(0.0, 0.5)
                    Metal(albedo, fuzz)
                } else {
                    // glass
                    Dielectric(1.5)
                }
                world.add(Sphere(center, 0.2, sphereMaterial))
            }
        }
    }

    val material1 = Dielectric(1.5)
    world.add(Sphere(Point3(0.0, 1.0, 0.0), 1.0, material1))

    val material2 = Lambertian(Color(0.4, 0.2, 0.1))
    world.add(Sphere(Point3(-4.0, 1.0, 0.0), 1.0, material2))

    val material3 = Metal(Color(0.7, 0.6, 0.5), 0.0)
    world.add(Sphere(Point3(4.0, 1.0, 0.0), 1.0, material3))
    return world
}

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
    val aspectRatio = 3.0 / 2.0
    val imageWidth = 800
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 100
    val maxDepth = 50

    // World
    val world = randomScene()

    // Camera
    val lookFrom = Point3(13.0, 2.0, 3.0)
    val lookAt = Point3(0.0, 0.0, 0.0)
    val vUp = Vec3(0.0, 1.0, 0.0)
    val distToFocus = 10.0
    val aperture = 0.1
    val camera = CameraWithFocus(lookFrom, lookAt, vUp, 20.0, aspectRatio, aperture, distToFocus)

    // Render
    // Render
    var image = ImmutableImage.create(imageWidth, imageHeight)

    for (j in imageHeight - 1 downTo 0) {
        System.err.print("\rScalines remaining: $j ")
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
    image.output(PngWriter.NoCompression, File("images/final_render.png"))
}