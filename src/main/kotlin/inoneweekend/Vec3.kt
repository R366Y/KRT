package inoneweekend

import kotlin.math.abs
import kotlin.math.sqrt

class Vec3(var x: Double, var y: Double, var z: Double) {


    operator fun timesAssign(v: Vec3) {
        x *= v.x
        y *= v.y
        z *= v.z
    }

    operator fun divAssign(v: Vec3) {
        x /= v.x
        y /= v.y
        z /= v.z
    }

    operator fun unaryMinus() = Vec3(-x, -y, -z)
    operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
    operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)
    operator fun times(t: Double) = Vec3(t * x, t * y, t * z)
    operator fun div(t: Double) = Vec3(x, y, z) * (1 / t)


    fun lengthSquared(): Double {
        return x * x + y * y + z * z
    }

    fun length(): Double {
        return sqrt(lengthSquared())
    }

    infix fun dot(v: Vec3): Double {
        return x * v.x + y * v.y + z * v.z
    }

    infix fun cross(v: Vec3): Vec3 {
        return Vec3(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        )
    }

    fun unitVector(): Vec3 {
        return this / length()
    }

    fun nearZero(): Boolean {
        // Return true if the vector is close to zero in all dimensions
        val s = 1e-8
        return (abs(x) < s) && (abs(y) < s) && (abs(z) < s)
    }

    override fun toString(): String {
        return "[$x, $y, $z]"
    }
}

fun random(): Vec3 = Vec3(randomDouble(), randomDouble(), randomDouble())

fun random(min: Double, max: Double) = Vec3(randomDouble(min, max), randomDouble(min, max), randomDouble(min, max))

fun randomInUnitSphere(): Vec3 {
    while (true) {
        val p = random(-1.0, 1.0)
        if (p.lengthSquared() >= 1) continue;
        return p;
    }
}

fun randomUnitVector(): Vec3 = randomInUnitSphere().unitVector()

fun randomInHemisphere(normal: Vec3): Vec3 {
    val inUnitSphere = randomInUnitSphere()
    return if ((inUnitSphere dot normal) > 0.0) { // In the same hemisphere as the normal
        inUnitSphere
    } else {
        -inUnitSphere
    }
}

operator fun Double.times(v: Vec3): Vec3 = v * this

typealias Point3 = Vec3
typealias Color = Vec3
