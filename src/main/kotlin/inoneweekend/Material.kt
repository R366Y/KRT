package inoneweekend

data class Scattered(val scattered: Ray, val attenuation: Color)

interface Material {
    fun scatter(rIn: Ray, rec: HitRecord): Scattered?
}

class Lambertian(private val albedo: Color) : Material {
    override fun scatter(rIn: Ray, rec: HitRecord): Scattered {
        var scatterDirection = rec.normal + randomUnitVector()

        // Catch degenerate scatter direction
        if (scatterDirection.nearZero()) {
            scatterDirection = rec.normal
        }

        return Scattered(Ray(rec.p, scatterDirection), albedo)
    }
}

class Metal(private val albedo: Color, f: Double = 0.0) : Material {
    private val fuzz: Double

    init {
        fuzz = if (f < 1) f else 1.0
    }

    override fun scatter(rIn: Ray, rec: HitRecord): Scattered? {
        val reflected = reflect(rIn.direction.unitVector(), rec.normal)
        val scattered = Ray(rec.p, reflected + fuzz * randomInUnitSphere())
        return if ((scattered.direction dot rec.normal) > 0.0) {
            Scattered(scattered, albedo)
        } else {
            null
        }

    }

}