package inoneweekend

import kotlin.random.Random

private val random = Random.Default

fun randomDouble(): Double = random.nextDouble()
fun randomDouble(min: Double, max: Double): Double  = random.nextDouble(min, max)