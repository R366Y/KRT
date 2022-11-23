package inoneweekend

class HittableList : Hittable {

    private val hittableObjects = mutableListOf<Hittable>()

    fun clear() {
        hittableObjects.clear()
    }

    fun add(obj: Hittable) {
        hittableObjects.add(obj)
    }

    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {
        var hitRec: HitRecord? = null
        var closestSoFar = tMax

        for (obj in hittableObjects) {
            val h = obj.hit(r, tMin, closestSoFar)
            if (h != null) {
                closestSoFar = h.t
                hitRec = h
            }
        }
        return hitRec
    }

}