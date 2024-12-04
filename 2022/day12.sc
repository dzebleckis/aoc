import scala.collection.mutable.PriorityQueue
import java.nio.file.Paths
import scala.io.Source

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines()
  .toSeq

case class Position(
    x: Int,
    y: Int,
    elevation: Char,
    distance: Int = Int.MaxValue,
    end: Boolean = false
):
  def diff(other: Position): Int = elevation - other.elevation

val board = Array.ofDim[Position](content.size, content.head.size)
var startPosition: Position = _

content.zipWithIndex.foreach((line, idx) =>
  val chars = line.toCharArray()
  val startIdx = chars.indexOf('S')
  val endIdx = chars.indexOf('E')

  if startIdx != -1 then
    chars(startIdx) = 'a'
    startPosition = Position(startIdx, idx, 'a', 0)

  board(idx) = chars.zipWithIndex.map((c, x) =>
    c match
      case 'S' => Position(x, idx, 'a', 0)
      case 'E' => Position(x, idx, 'z', Int.MaxValue, true)
      case c   => Position(x, idx, c)
  )
)

val distances = collection.mutable.Map.empty[Position, Int]

def getNeighboursFn(maxX: Int, maxY: Int)(position: Position): Seq[(Int, Int)] =
  val x = position.x
  val y = position.y
  Seq((x + 1, y), (x, y + 1), (x - 1, y), (x, y - 1))
    .filter((x, y) => x < maxX && x >= 0 && y < maxY && y >= 0)

def getNeighbours = getNeighboursFn(board.head.size, board.size)

def getElement(position: (Int, Int)) = board(position(1))(position(0))

object PositionOrdering extends Ordering[Position] {
  def compare(a: Position, b: Position) = a.distance.compare(b.distance)
}

def find(
    current: Position,
    isFound: Position => Boolean,
    validMove: (Position, Position) => Boolean
): Option[Position] =
  var queue =
    PriorityQueue.empty(Ordering.by[Position, Int](_.distance).reverse)

  val visited = collection.mutable.Set.empty[(Int, Int)]
  queue += current

  while queue.nonEmpty do
    val current = queue.dequeue()
    // println(s"current: $current")
    visited += (current.x -> current.y)

    if isFound(current) then
      //   println(s"Found: $current")
      return Some(current)

    getNeighbours(current).foreach(n =>
      var b = getElement(n)

      if !visited.contains(n) && validMove(b, current) then
        val l = current.distance + 1
        if l < b.distance then
          queue = queue.filterNot(p => p.x == b.x && p.y == b.y)
          queue += b.copy(distance = l)
    )
  None

val endPosition =
  find(startPosition, _.end, (next, current) => next.diff(current) <= 1)
val e = endPosition.get.copy(distance = 0)
val optimalStart =
  find(e, _.elevation == 'a', (next, current) => current.diff(next) <= 1)

println(s"Part1: ${endPosition.map(_.distance)}")
println(s"Part2: ${optimalStart.map(_.distance)}")
