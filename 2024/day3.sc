//> using file utils.sc

import utils.readInput

val content = readInput(args).reduce(_ + _)

val regEx = """mul\((\d{1,3}),(\d{1,3})\)""".r

def count(line: String): Int =
  regEx
    .findAllMatchIn(line)
    .map { case regEx(x, y) =>
      x.toInt * y.toInt
    }
    .sum

val a = count(content)

println(s"part1: $a")

val b = count(content.replaceAll("""don't\(\).*?do\(\)|don't\(\).*$""", ""))

println(s"part2: $b")
