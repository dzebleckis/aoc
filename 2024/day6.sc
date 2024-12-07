//> using file utils.sc

import utils.readInput

case class Position(row: Int, col: Int, direction: Direction)
enum Direction:
  case Left
  case Right
  case Up
  case Down

val content = readInput(args)
print("\t")
content(0).zipWithIndex.foreach { (_, i) => print(i) }
println()
content.zipWithIndex.foreach((c, i) => println(s"$i \t$c"))

println(content(0)(4))

val position = for
  (row, y) <- content.zipWithIndex
  (col, x) <- row.zipWithIndex
  if col == '^'
yield Position(y, x, Direction.Up)

println(position)

import Direction._

var count = 0

def rotate(direction: Direction): Direction = direction match
  case Up    => Right
  case Right => Down
  case Down  => Left
  case Left  => Up

def nextPosition(position: Position): Position = position.direction match
  case Up    => position.copy(row = position.row - 1)
  case Right => position.copy(col = position.col + 1)
  case Down  => position.copy(row = position.row + 1)
  case Left  => position.copy(col = position.col - 1)

def recurse(position: Position, visited: Seq[Position]): Seq[Position] =
  if position.col == 0 ||
    position.col == content(0).size - 1 ||
    position.row == 0 ||
    position.row == content.size - 1
  then visited :+ position
  else
    val next = nextPosition(position)

    if content(next.row)(next.col) == '#' then
      val newDirection = rotate(position.direction)
      recurse(position.copy(direction = newDirection), visited :+ position)
    else recurse(next, visited :+ position)

val visited = recurse(position.head, Seq.empty)

println(visited)
println(s"visited ${visited.size}")
// we don't care about direction while counting uniq visited cells
println(s"part1: ${visited.map(p => (p.row, p.col)).toSet.size}")

for 
    // idx <- 1 until visited.size - 1
    (position, idx) <- visited.zipWithIndex
    if idx < visited.size - 1
    next = visited(idx + 1)
    if position.direction == next.direction
do

    val simulation = nextPosition(position.copy(direction = rotate(position.direction)))
    println(s"$position, $simulation")    
    val found = visited.find(p => p == simulation)
    if found.isDefined then
        println(s"to block $next found ${found.get}")


    // (position, idx) <- visited.zipWithIndex
    // if position.direction == visited(idx + 1).direction

// Part 2 - condition for loop: it's on the visited path and obstacle would
// force to move to visited place
