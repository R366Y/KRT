package inoneweekend

class Camera {
    val aspectRatio = 16.0 / 9.0
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    private val origin = Point3(0.0, 0.0, 0.0)
    private val horizontal = Vec3(viewportWidth, 0.0, 0.0)
    private val vertical = Vec3(0.0, viewportHeight, 0.0)
    private val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)

    fun getRay(u: Double, v: Double): Ray {
        return Ray(origin, lowerLeftCorner + u * horizontal + v * vertical - origin)
    }
}