//> using file utils.sc

import utils.readInput

case class Button(x: Long, y: Long)
case class Prize(x: Long, y: Long)

val buttonRegex = raw"Button [A,B]: X\+(\d+), Y\+(\d+)".r
val prizeRegex = """Prize: X=(\d+), Y=(\d+)""".r

def parseButton(l: String): Button =
  l match
    case buttonRegex(x, y) => Button(x.toLong, y.toLong)

def parse(group: Seq[String]): (Button, Button, Prize) =
  val a = parseButton(group(0))
  val b = parseButton(group(1))
  group(2) match
    case prizeRegex(x, y) =>
      (a, b, Prize(x.toLong, y.toLong))

// https://en.wikipedia.org/wiki/Cramer%27s_rule      
def solve(t: (Button, Button, Prize)): Option[(Long, Long)] =
  val (a, b, p) = t
  val determinant = (a.x * b.y - a.y * b.x)
  val A = (p.x * b.y - p.y * b.x) / determinant
  val B = (a.x * p.y - a.y * p.x) / determinant

  if a.x * A + b.x * B == p.x && a.y * A + b.y * B == p.y then Some((A, B))
  else None

val content = readInput(args)
  .filter(l => l.length > 0)
  .grouped(3)
  .map(parse)
  .toList

val part1 =
  content
    .flatMap(solve)
    .map((a, b) => 3 * a + b)
    .sum


println(s"Part1: $part1")

val part2 = 
  content
    .map((a, b, p) => (a, b, Prize(p.x + 10000000000000l, p.y + 10000000000000l)))
    .flatMap(solve)
    .map((a, b) => 3 * a + b)
    .sum

println(s"Part2: $part2")    