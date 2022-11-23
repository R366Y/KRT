package inoneweekend

class HitRecord(val p: Point3, outwardNormal: Vec3, val t: Double, r: Ray) {
    val frontFace: Boolean
    val normal: Vec3

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