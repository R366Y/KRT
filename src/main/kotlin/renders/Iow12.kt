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
    val imageWidth = 800
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 100
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
    val lookFrom = Point3(3.0, 3.0, 2.0)
    val lookAt = Point3(0.0, 0.0, -1.0)
    val vUp = Vec3(0.0, 1.0, 0.0)
    val distToFocus = (lookFrom - lookAt).length()
    val aperture = 2.0
    val camera = CameraWithFocus(lookFrom, lookAt, vUp, 20.0, aspectRatio, aperture, distToFocus)

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
                    pixelColor += rayColor(r, world, maxDepth)
                }
                append(writeColor(pixelColor, samplesPerPixel))
            }
        }
    }
    File("image12.ppm").delete()
    File("image12.ppm").appendText(image)
}