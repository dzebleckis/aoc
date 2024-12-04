def wins(timeToStart: Long, totalTime: Long, distance: Long) =
  timeToStart * (totalTime - timeToStart) > distance

def findMin(totalTime: Long, distance: Long): Long =

  def d(waitTime: Long) = waitTime * (totalTime - waitTime)

  var lo = 0L
  var hi = totalTime / 2

  while lo + 1 < hi do
    val middle = (lo + hi) / 2
    if d(middle) >= distance then hi = middle
    else lo = middle

  // it should be larger than distance
  if d(hi) == distance then hi + 1 else hi

def distance(totalTime: Long, distance: Long): Long =
  val first = findMin(totalTime, distance)
  val middle = totalTime / 2
  // interval is symetric
  var last = middle + middle - first
  last = if wins(last + 1, totalTime, distance) then last + 1 else last
  last - first + 1

var data1 = Map(59 -> 543, 68 -> 1020, 82 -> 1664, 74 -> 1022)

val part1 = data1.map((t, d) => distance(t, d)).product
println(s"Part 1: $part1")

val part2 = distance(59688274, 543102016641022L)
println(s"Part 2: $part2")
