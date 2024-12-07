//> using file utils.sc

import utils.readInput

val equations = readInput(args)
  .map(l => l.split(": ").toList)
  .map { line => (line.head.toLong, line(1).split(" ").map(_.toLong)) }

def recurse(answer: Long, accumulator: Long, rest: Seq[Long]): Boolean =
  if rest.isEmpty then answer == accumulator
  else if accumulator > answer then false
  else
    recurse(answer, accumulator + rest.head, rest.tail) ||
    recurse(answer, accumulator * rest.head, rest.tail)

val (solved, notSolved) = equations
  .partition { case (answer, equation) => recurse(answer, equation.head, equation.tail) }

val total1 = solved.map(_._1).sum

println(s"part1: $total1")

def recurse2(
    answer: Long,
    accumulator: Long,
    rest: Seq[Long]
): Boolean =
  if rest.isEmpty then answer == accumulator
  else if accumulator > answer then false
  else
    recurse2(answer, s"$accumulator${rest.head}".toLong, rest.tail) ||
    recurse2(answer, accumulator + rest.head, rest.tail) ||
    recurse2(answer, accumulator * rest.head, rest.tail)

val total2 = notSolved
  .filter { case (answer, equation) => recurse2(answer, equation.head, equation.tail) }
  .map(_._1)
  .sum

println(s"part2: ${total1 + total2}")
