package inoneweekend

import kotlin.math.sqrt

class Vec3(var x: Double, var y: Double, var z: Double) {

    operator fun plusAssign(v: Vec3) {
        x += v.x
        y += v.y
        z += v.z
    }

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

    override fun toString(): String {
        return "[$x, $y, $z]"
    }
}

operator fun Double.times(v: Vec3): Vec3 = v * this

typealias Point3 = Vec3
typealias Color = Vec3

fun main() {
    val v1 = Vec3(1.0, 1.0, 1.0)
    -v1
    println("${v1.x} ${v1.y} ${v1.z}")
    val v2 = Vec3(1.0, 1.0, 1.0)
    v1 += v2
    println("${v1.x} ${v1.y} ${v1.z}")
    println("$v2")
}
