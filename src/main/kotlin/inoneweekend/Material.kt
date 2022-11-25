package inoneweekend

import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

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

class Dielectric(indexOfRefraction: Double) : Material {
    private val ir = indexOfRefraction

    override fun scatter(rIn: Ray, rec: HitRecord): Scattered {
        val attenuation = Color(1.0, 1.0, 1.0)
        val refractionRatio = if (rec.frontFace) (1.0 / ir) else ir

        val unitDirection = rIn.direction.unitVector()
        val cosTheta = min(-unitDirection dot rec.normal, 1.0)
        val sinTheta = sqrt(1.0 - cosTheta * cosTheta)

        val cannotRefract = refractionRatio * sinTheta > 1.0
        val direction = if (cannotRefract || reflectance(cosTheta, refractionRatio) > randomDouble()) {
            reflect(unitDirection, rec.normal)
        } else {
            refract(unitDirection, rec.normal, refractionRatio)
        }

        return Scattered(Ray(rec.p, direction), attenuation)
    }

    private fun reflectance(cosine: Double, refIdx: Double): Double {
        // Use Schlick's approximation
        var r0 = (1 - refIdx) / (1 + refIdx)
        r0 *= r0
        return r0 + (1 - r0) * (1 - cosine).pow(5)
    }

}