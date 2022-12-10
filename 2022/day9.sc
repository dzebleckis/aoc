import java.nio.file.Paths
import scala.io.Source

val cmd = raw"^([U,D,L,R]) (\d+)$$".r

case class Position(x: Int, y: Int):
  def isSameRow(other: Position): Boolean = y == other.y

  def isSameColumn(other: Position): Boolean = x == other.x

  def maxDistance(other: Position): Int =
    Math.max(Math.abs(x - other.x), Math.abs(y - other.y))

  def adjacentColumn(other: Position): Position =
    if x > other.x then Position(x - 1, y)
    else Position(x + 1, y)

  def adjacentRow(other: Position): Position =
    if y > other.y then Position(x, y - 1)
    else Position(x, y + 1)

def diagonal(head: Position, tail: Position): Position =
  val x = tail.x + (if head.x > tail.x then 1 else -1)
  val y = tail.y + (if head.y > tail.y then 1 else -1)
  Position(x, y)

def getTailPosition(head: Position, tail: Position): Position =
  if head == tail then tail
  else if tail.isSameRow(head) then head.adjacentColumn(tail)
  else if tail.isSameColumn(head) then head.adjacentRow(tail)
  else if head.maxDistance(tail) < 2 then tail
  else diagonal(head, tail)

var headMovements = Seq(Position(0, 0))

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines()
  .foreach(line =>
    line match
      case cmd(direction, stepsS) =>
        val steps = stepsS.toInt
        val head = headMovements.last
        val m = direction match
          case "U" => (1 to steps).map(i => Position(head.x, head.y + i))
          case "D" => (1 to steps).map(i => Position(head.x, head.y - i))
          case "L" => (1 to steps).map(i => Position(head.x - i, head.y))
          case "R" => (1 to steps).map(i => Position(head.x + i, head.y))
        headMovements = headMovements ++ m
      case _ => println("Oh nooo")
  )

val iterations = collection.mutable.Map.empty[Int, Seq[Position]]
(1 to 9).foreach(iterations.put(_, Seq(Position(0,0))))

headMovements
  .drop(1)
  .foreach(m =>
    val tails = iterations(1)
    val nextPosition = getTailPosition(m, tails.last)
    if nextPosition != tails.last then
      iterations.put(1, tails :+ nextPosition)

    (2 to 9).foreach(i =>
      val tails = iterations(i)
      val heads = iterations(i - 1)
      val next = getTailPosition(heads.last, tails.last)
      if next != tails.last then
        iterations.put(i, tails :+ next)
    )
  )

println(s"Part1: ${iterations(1).toSet.size}")
println(s"Part2: ${iterations(9).toSet.size}")
