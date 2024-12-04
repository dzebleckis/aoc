//> using file utils.sc

import utils.readInput

enum Direction:
  case Left
  case Right
  case Up
  case Down
  case DiagonalUpLeft
  case DiagonalUpRight
  case DiagonalDownLeft
  case DiagonalDownRight

case class Point(col: Int, row: Int)

case class Dim(maxX: Int, maxY: Int)

def buildPath(direction: Direction, point: Point, dim: Dim, maxLength: Int): Iterator[Point] =
  new Iterator[Point]:

    import Direction._

    var current: Point = point
    var path: Int = 1

    override def hasNext =
      val validLeft = current.col > 0
      val validRight = current.col < dim.maxX - 1
      val validUp = current.row > 0
      val validDown = current.row < dim.maxY - 1

      path <= maxLength && (direction match
        case Left => validLeft
        case Right => validRight
        case Up => validUp
        case Down => validDown
        case DiagonalUpLeft => validLeft && validUp
        case DiagonalUpRight => validRight && validUp
        case DiagonalDownLeft => validLeft && validDown
        case DiagonalDownRight => validRight && validDown
        )

    override def next() =
      path += 1
      current = direction match
        case Left => current.copy(col = current.col - 1)
        case Right => current.copy(col = current.col + 1)
        case Up => current.copy(row = current.row - 1)
        case Down => current.copy(row = current.row + 1)
        case DiagonalUpLeft => current.copy(col = current.col - 1, row = current.row - 1)
        case DiagonalDownLeft => current.copy(col = current.col - 1, row = current.row + 1)
        case DiagonalUpRight => current.copy(col = current.col + 1, row = current.row - 1)
        case DiagonalDownRight => current.copy(col = current.col + 1, row = current.row + 1)

      current

val content = readInput(args)

//println(content.mkString("\n"))

val dim = Dim(content.head.size, content.size)

println(s"dim $dim")


def getLetters(direction: Direction, point: Point, length: Int): String =
  buildPath(direction, point, dim, length).map(p => content(p.row)(p.col)).mkString("")

val part1 = for
  y <- 0 until dim.maxY
  x <- 0 until dim.maxX
  if content(y)(x) == 'X'
  d <- Direction.values
  part = getLetters(d, Point(x, y), 3)
  if part == "MAS"
yield part

println(s"part1: ${part1.size}")

def getSortedWord(directions: Seq[Direction], point: Point): String =
  directions.map(d => getLetters(d, point, 1)).mkString("").sorted

val part2 = for
  y <- 0 until dim.maxY
  x <- 0 until dim.maxX
  if content(y)(x) == 'A'
  point = Point(x, y)
  if getSortedWord(Seq(Direction.DiagonalUpLeft, Direction.DiagonalDownRight), point) == "MS"
  if getSortedWord(Seq(Direction.DiagonalDownLeft, Direction.DiagonalUpRight), point) == "MS"
yield 1

println(s"part2: ${part2.size}")
