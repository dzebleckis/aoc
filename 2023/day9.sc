//> using file utils.sc

import utils.readInput

val r = """(-?\d+)""".r

def parseLine(line: String): Vector[Long] =
  r.findAllMatchIn(line)
    .map(_.group(1))
    .map(_.trim)
    .map(_.toLong)
    .toVector

def createSequence(current: Vector[Long]): Vector[Vector[Long]] =
  def build(
      result: Vector[Vector[Long]]
  ): Vector[Vector[Long]] =
    val current = result.last
    if current.forall(_ == 0) then return result
    else
      val a =
        for i <- current.length - 1 to 1 by -1
        yield current(i) - current(i - 1)

      val c = a.reverse.toVector
      build(result = result :+ c)

  build(Vector(current))

def solve(
    sequence: Vector[Vector[Long]],
    op: (data: Vector[Long], additional: Long) => Long
): Long =

  def innerSolve(idx: Int, additional: Long): Long =
    if idx == -1 then additional
    else innerSolve(idx - 1, op(sequence(idx), additional))

  innerSolve(sequence.size - 1, 0)

val common = readInput(args)
  .map(parseLine)
  .map(createSequence)

val part1 = common
  .map(solve(_, (d, a) => d.last + a))
  .sum

println(s"Part 1: $part1")

val part2 = common
  .map(solve(_, (d, a) => d.head - a))
  .sum

println(s"Part 2: $part2")
