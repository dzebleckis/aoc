import java.nio.file.Paths
import java.nio.file.Files
import scala.jdk.StreamConverters._

val content =
  Files
    .lines(Paths.get(".", args.toSeq.head))
    .toScala(List)
    .map(l => l.split(","))
    .map(a =>
      val pair1 = a.head.split("-")
      val pair2 = a.last.split("-")

      val r1 = Range.Inclusive(pair1.head.toInt, pair1.last.toInt, 1)
      val r2 = Range.Inclusive(pair2.head.toInt, pair2.last.toInt, 1)
      (r1, r2)
    )

val part1 = content
  .filter((r1, r2) =>
    // check if intersection is equal to smaller range lengt
    r1.intersect(r2).length == Math.min(r1.length, r2.length)
  )

val part2 = content
  .filter((r1, r2) => r1.intersect(r2).length > 0)

println(s"Part1: ${part1.length}")
println(s"Part2: ${part2.length}")
