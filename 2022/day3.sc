import java.nio.file.Paths
import java.nio.file.Files
import scala.jdk.StreamConverters._

val content =
  Files
    .lines(Paths.get(".", args.toSeq.head))
    .toScala(List)

def calculatePriority(c: Char): Int =
  if (c <= 'Z') then c - 'A' + 27
  else c - 'a' + 1

def findBadge(group: Iterable[String]): Char =
  // there migth be more than 1 same letter, lets take first one
  group.reduce(_.intersect(_)).head

def solution(groups: Iterable[Iterable[String]]): Int =
  groups
    .map(findBadge)
    .map(calculatePriority)
    .sum

val part1 = content
  .map(s => s.splitAt(s.length / 2).toList)

println(s"Pat1 ${solution(part1)}")

val part2 = content
  .grouped(3)
  .toSeq

println(s"Pat2 ${solution(part2)}")
