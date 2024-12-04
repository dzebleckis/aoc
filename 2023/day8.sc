//> using file utils.sc

import scala.util.boundary
import scala.util.matching.Regex.Match

import utils.readInput

val content = readInput(args)
val instructions = content.head.trim
val rest = content.drop(2)

val r = """(\w+)""".r

def parse(m: Iterator[Match]): (String, String, String) =
  val List(a, b, c) = m.toList
  (a.group(1), b.group(1), c.group(1))

type GameMap = Map[String, (String, String)]

val input = rest
  .map(r.findAllMatchIn(_))
  .map(parse)
  .map((k, l, r) => (k -> (l, r)))
  .toMap

def find(
    gameMap: GameMap,
    next: String,
    instructions: String,
    end: String => Boolean
): Int =
  def inner(
      map: GameMap,
      next: String,
      instructions: String,
      counter: Int,
      idx: Int
  ): Int =
    if end(next) then counter
    else
      val node = gameMap(next)
      val n = if instructions(idx) == 'L' then node(0) else node(1)
      val i = if idx + 1 == instructions.length then 0 else idx + 1
      inner(map, n, instructions, counter + 1, i)

  inner(gameMap, next, instructions, 0, 0)

val part1 = find(input, "AAA", instructions, _ == "ZZZ")
println(s"Part 1: $part1")

def lcm(a: BigInt, b: BigInt): BigInt =
  var aa = a
  var bb = b
  while !aa.equals(bb) do
    while aa < bb do aa += a
    while bb < aa do bb += b
  aa

val part2 = input
  .filter((k, _) => k.endsWith("A"))
  .map(i => find(input, i(0), instructions, _.endsWith("Z")))
  .map(BigInt(_))
  .reduce(lcm)

println(s"Part 2: $part2")
