//> using file utils.sc

import utils.readInput
import scala.collection.mutable.Queue
import scala.collection.mutable.Set

type Board = Vector[String]
type Point = (Int, Int)

def findStart(board: Board): (Int, Int) =
  import scala.util.boundary, scala.util.boundary.break
  boundary:
    for
      x <- 0 until dim
      y <- 0 until dim
    do if board(x)(y) == 'S' then break(x -> y)

    (-1, -1)

val board = readInput(args).toVector
val dim = board.size
val start = findStart(board)
val seen = Set(start)
val queue = Queue(start)

def at(point: Point): Char = board(point._1)(point._2)

def add(point: Point) =
  if !seen.contains(point) then
    queue.enqueue(point)
    seen.add(point)

extension (c: Char) infix def in(str: String): Boolean = str.contains(c)

while !queue.isEmpty do
  val (r, c) = queue.dequeue()
  val current = at(r, c)

  var nr = r - 1 // up
  if r > 0 && current.in("S|JL") && at(nr, c).in("|7F") then add(nr, c)

  nr = r + 1 // down
  if r < dim - 1 && current.in("S|7F") && at(nr, c).in("|JL") then add(nr, c)

  var nc = c + 1 // right
  if c < dim - 1 && current.in("S-LF") && at(r, nc).in("-J7") then add(r, nc)

  nc = c - 1 // left
  if c > 0 && current.in("S-J7") && at(r, nc).in("-FL") then add(r, nc)

println(seen.size / 2)
