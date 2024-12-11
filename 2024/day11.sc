var stones = collection.mutable.Map(
  (125L, 1L),
  (17L, 1L)
)

for i <- 1 to 75
do
  var stonesAfterBlink = collection.mutable.Map.empty[Long, Long].withDefaultValue(0)
  stones.foreach { (key, value) =>
    key match
      case 0 =>
        stonesAfterBlink.put(1, stonesAfterBlink(1) + value)
      case n if n.toString().length() % 2 == 0 =>
        val middle = n.toString().length() / 2
        val left = Math.floor(n / Math.pow(10, middle)).toInt
        val right = n % Math.pow(10, middle).toInt

        stonesAfterBlink.put(left, stonesAfterBlink(left) + value)
        stonesAfterBlink.put(right, stonesAfterBlink(right) + value)

      case _: Long =>
        val newKey = key * 2024
        stonesAfterBlink.put(newKey, stonesAfterBlink(newKey) + value)
  }
  stones = stonesAfterBlink

  if i == 25 then println(s"Part1: ${stones.values.sum}")

val count = stones.values.sum
println(s"Part2: ${stones.values.sum}")
