package inoneweekend

class HitRecord(val p: Point3, outwardNormal: Vec3, val t: Double, r: Ray, var material: Material? = null) {
    val normal: Vec3
    private val frontFace: Boolean

    init {
        frontFace = (r.direction dot outwardNormal) < 0
        normal = when (frontFace) {
            true -> outwardNormal
            false -> -outwardNormal
        }
    }
}

interface Hittable {
    fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord?
}