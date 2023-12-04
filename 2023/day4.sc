//> using file utils.sc

import utils.readInput

val digitReqex = """(\d+)""".r

def parseLine(line: String): Set[String] =
  val Array(_, rest) = line.split(":")
  val Array(winning, onHand) = rest.split("\\|")

  def split(l: String) =
    digitReqex.findAllMatchIn(l).map(_.group(0)).toSet

  split(winning).intersect(split(onHand))

val content = readInput(args)
val common = content.map(parseLine)

val part1 =
  common
    .filter(!_.isEmpty)
    .map(s => Math.pow(2, s.size - 1).toInt)
    .sum

println(s"Part 1: $part1")

val counters = collection.mutable.Map.empty[Int, Int].withDefault(_ => 1)

common.zipWithIndex.foreach { (c, idx) =>
  counters.put(idx, counters(idx)) // just so we have all entries in a map
  (idx + 1 to idx + c.size).foreach { next =>
    val copies = counters(idx)
    counters.put(next, counters(next) + copies)
  }
}

println(s"Part 2: ${counters.values.sum}")
