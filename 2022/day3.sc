import java.nio.file.Paths
import java.nio.file.Files
import scala.jdk.StreamConverters._

val content =
  Files
    .lines(Paths.get(".", args.toSeq.head))
    .toScala(List)

def priority(c: Char): Int =
  if (c <= 'Z') then c - 'A' + 27
  else c - 'a' + 1

def findBadge(group: Iterable[String]): Char =
  // there migth be more than 1 same letter, lets take first one
  group.reduce(_.intersect(_)).head

val result1 = content
  .map(s => s.splitAt(s.length / 2))
  .map(p => findBadge(List(p(0), p(1))))
  .map(priority)
  .sum

println(s"Pat1 ${result1}")

val result2 = content
  .grouped(3)
  .map(findBadge)
  .map(priority)
  .sum

println(s"Pat2 ${result2}")
