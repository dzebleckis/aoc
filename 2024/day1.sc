import java.nio.file.Paths
import scala.io.Source

var left = Seq.empty[Int]
var right = Seq.empty[Int]

val content = Source
  .fromFile(Paths.get(".", args.toSeq.head).toUri())
  .getLines()
  .map(_.split("   ").map(_.toInt))
  .foreach: ll =>
    left = left :+ ll(0)
    right = right :+ ll(1)

var count = 0;

left.sorted
  .zip(right.sorted)
  .foreach: (l, r) =>
    count += Math.abs(r - l)

println(s"part1: $count")

val rightIdentity = right.groupBy(identity)

count = 0

for
  i <- left
  k <- rightIdentity.get(i)
do count += i * k.length

println(s"part2: $count")
