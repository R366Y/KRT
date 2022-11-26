package inoneweekend

import kotlin.math.tan

class Camera(
    lookFrom: Point3 = Point3(0.0, 0.0, 0.0),
    lookAt: Point3 = Point3(0.0, 0.0, -1.0),
    vUp: Vec3 = Vec3(0.0, 1.0, 0.0),
    vfov: Double = 90.0,
    aspectRatio: Double = 16.0 / 9.0
) {
    private val origin: Point3
    private val horizontal: Vec3
    private val vertical: Vec3
    private val lowerLeftCorner: Vec3

    init {
        val theta = Math.toRadians(vfov)
        val h = tan(theta / 2)
        val viewportHeight = 2.0 * h
        val viewportWidth = aspectRatio * viewportHeight

        val w = (lookFrom - lookAt).unitVector()
        val u = (vUp cross w).unitVector()
        val v = w cross u

        origin = lookFrom
        horizontal = viewportWidth * u
        vertical = viewportHeight * v
        lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - w
    }


    fun getRay(s: Double, t: Double): Ray {
        return Ray(origin, lowerLeftCorner + s * horizontal + t * vertical - origin)
    }
}

class CameraWithFocus(
    lookFrom: Point3 = Point3(0.0, 0.0, 0.0),
    lookAt: Point3 = Point3(0.0, 0.0, -1.0),
    vUp: Vec3 = Vec3(0.0, 1.0, 0.0),
    vfov: Double = 90.0,
    aspectRatio: Double = 16.0 / 9.0,
    aperture: Double = 0.1,
    focusDist:Double = 10.0
) {
    private val origin: Point3
    private val horizontal: Vec3
    private val vertical: Vec3
    private val lowerLeftCorner: Vec3
    private val u: Vec3
    private val v: Vec3
    private val w: Vec3
    private val lensRadius: Double

    init {
        val theta = Math.toRadians(vfov)
        val h = tan(theta / 2)
        val viewportHeight = 2.0 * h
        val viewportWidth = aspectRatio * viewportHeight

        w = (lookFrom - lookAt).unitVector()
        u = (vUp cross w).unitVector()
        v = w cross u

        origin = lookFrom
        horizontal = focusDist * viewportWidth * u
        vertical = focusDist * viewportHeight * v
        lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - focusDist * w
        lensRadius = aperture / 2;
    }


    fun getRay(s: Double, t: Double): Ray {
        val rd = lensRadius * randomInUnitDisk()
        val offset = u * rd.x + v * rd.y
        return Ray(origin + offset, lowerLeftCorner + s * horizontal + t * vertical - origin - offset)
    }
}