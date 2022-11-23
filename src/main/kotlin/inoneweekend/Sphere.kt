package inoneweekend

import kotlin.math.sqrt

class Sphere(private val center: Point3, private val radius: Double): Hittable  {

    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {
        val oc = r.origin - center
        val a = r.direction.lengthSquared()
        val halfB = oc dot r.direction
        val c = oc.lengthSquared() - radius * radius

        val discriminant = halfB * halfB - a * c
        if (discriminant < 0) return null
        val sqrtd = sqrt(discriminant)

        // Find the nearest root that lies in the acceptable range
        var root = (-halfB - sqrtd) / a
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtd) / a
            if (root < tMin || tMax < root) {
                return null
            }
        }

        val t = root
        val p = r.at(root)
        val normal = (p - center) / radius
        return HitRecord(p, normal, t, r)
    }
}