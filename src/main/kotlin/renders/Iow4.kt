package renders

import inoneweekend.*
import java.io.File
import kotlin.math.sqrt


fun main() {

    fun hitSphere(center: Point3, radius: Double, r: Ray): Double {
        val oc = r.origin - center
        val a = r.direction.lengthSquared()
        val halfB = oc dot r.direction
        val c = oc.lengthSquared() - radius * radius
        val discriminant = halfB * halfB - a * c
        return if (discriminant < 0) {
            -1.0
        } else {
            (-halfB - sqrt(discriminant)) / a
        }
    }

    fun rayColor(r: Ray): Color {
        var t = hitSphere(Point3(0.0, 0.0, -1.0), 0.5, r)
        if (t > 0.0) {
            val n = (r.at(t) - Vec3(0.0, 0.0, -1.0)).unitVector()
            return 0.5 * Color(n.x + 1, n.y + 1, n.z + 1)
        }
        val unitDirection = r.direction.unitVector()
        t = 0.5 * (unitDirection.y + 1.0)
        return (1.0 - t) * Color(1.0, 1.0, 1.0) + t * Color(0.5, 0.7, 1.0)
    }

    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()
    // Camera
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    val origin = Point3(0.0, 0.0, 0.0)
    val horizontal = Vec3(viewportWidth, 0.0, 0.0)
    val vertical = Vec3(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)

    // Render
    val image = buildString {
        val ppmHeader = "P3\n$imageWidth $imageHeight\n255\n"
        append(ppmHeader)

        for (j in imageHeight - 1 downTo 0) {
            System.err.print("\rScalines remaining: $j ")
            for (i in 0 until imageWidth) {
                val u = i.toDouble() / (imageWidth - 1)
                val v = j.toDouble() / (imageHeight - 1)
                val r = Ray(origin, lowerLeftCorner + u * horizontal + v * vertical - origin)
                val pixelColor = rayColor(r)
                val s = writeColor(pixelColor)
                append(s)
            }
        }
    }
    File("image4.ppm").delete()
    File("image4.ppm").appendText(image)
}