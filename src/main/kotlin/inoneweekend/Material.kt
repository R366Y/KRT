package inoneweekend

data class Scattered(val scattered: Ray, val attenuation: Color)

interface Material {
    fun scatter(rIn: Ray, rec: HitRecord): Scattered?
}

class lambertian(private val albedo: Color) : Material {
    override fun scatter(rIn: Ray, rec: HitRecord): Scattered {
        var scatterDirection = rec.normal + randomUnitVector()

        // Catch degenerate scatter direction
        if(scatterDirection.nearZero()) {
            scatterDirection = rec.normal
        }

        return Scattered(Ray(rec.p, scatterDirection), albedo)
    }
}