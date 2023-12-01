import scala.io.Source
import java.nio.file.Paths
import java.nio.file.Files

val content = Source
  .fromFile(Paths.get(".", args.toSeq.head).toUri())
  .getLines()
  .toList

val part1 = content
  .map(s => (s.find(_.isDigit).get, s.findLast(_.isDigit).get))
  .map { case (l, r) => (l.asDigit * 10 + r.asDigit) }
  .sum

println(s"Part 1: $part1")

val digits = (List(
  "one",
  "two",
  "three",
  "four",
  "five",
  "six",
  "seven",
  "eight",
  "nine"
).zip(1 to 9) ++ (1 to 9).map(d => (d.toString(), d))).toMap

def found(e: (Int, Int)) = e._1 != -1

def finder(search: (String) => Int) =
  digits
    .map((k, v) => (search(k), v))
    .filter(found)

val part2 = content
  .map(s => {
    val (_, left) = finder(s.indexOf).minBy(_._1)
    val (_, right) = finder(s.lastIndexOf).maxBy(_._1)

    left * 10 + right
  })

println(s"Part 2: ${part2.sum}")
