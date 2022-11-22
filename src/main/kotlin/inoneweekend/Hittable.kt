package inoneweekend

data class HitRecord(val p: Point3, val normal: Vec3, val t: Double);

interface Hittable {
    fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord?
}