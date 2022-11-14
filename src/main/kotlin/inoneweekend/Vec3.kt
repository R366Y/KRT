package inoneweekend

import kotlin.math.sqrt

class Vec3(var x: Double, var y: Double, var z: Double) {

    operator fun unaryMinus() {
        x = -x
        y = -y
        z = -z
    }

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

    fun lengthSquared(): Double {
        return x*x + y*y + z*z
    }

    fun length(): Double {
        return sqrt(lengthSquared())
    }

    override fun toString(): String {
        return "$x $y $z"
    }
}

fun main() {
    val v1 = Vec3(1.0, 1.0, 1.0)
    -v1
    println("${v1.x} ${v1.y} ${v1.z}")
    val v2 = Vec3(1.0, 1.0, 1.0)
    v1 += v2
    println("${v1.x} ${v1.y} ${v1.z}")
}
