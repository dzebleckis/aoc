import scala.collection.mutable
import java.nio.file.Paths
import scala.io.Source

case class Tree(val height: Char, val x: Int, val y: Int):
  override def toString(): String = s"T(h=$height, x=$x, y=$y)"

enum Direction:
  case Left
  case Right
  case Down
  case Up

type Forest = mutable.Map[Int, IndexedSeq[Tree]]
val forest: Forest = mutable.Map.empty

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines
  .zipWithIndex
  .foreach((line, x) =>
    val trees = line.zipWithIndex
      .map((height, y) => Tree(height, x, y))
    forest.put(x, trees)
  )

def neighboursGenerator(
    s: Int
)(x: Int, y: Int, direction: Direction): Iterator[(Int, Int)] =
  new Iterator[(Int, Int)] {
    var ix = x;
    var iy = y;
    override def hasNext: Boolean = ix < s && ix > 0 && iy < s && iy > 0
    override def next(): (Int, Int) =
      direction match
        case Direction.Up    => iy -= 1
        case Direction.Down  => iy += 1
        case Direction.Left  => ix -= 1
        case Direction.Right => ix += 1
      (ix, iy)
  }

def isVisible(x: Int, y: Int)(direction: Direction): Boolean =
  val tree = forest(y)(x)
  !neighbours(x, y, direction)
    .exists((x, y) => forest(y)(x).height >= tree.height)

def calculateScore(x: Int, y: Int)(direction: Direction): Int =
  val tree = forest(y)(x)
  var result = 0
  var found = false
  val it = neighbours(x, y, direction) ++ Seq((-1, -1))
  while !found && it.hasNext do
    val (x, y) = it.next()
    if (x == -1) then result -= 1
    else
      val current = forest(y)(x)
      if current.height >= tree.height then found = true;
      else result += 1
  result + 1

if (forest.size != forest(0).size)
  throw Error("Not a square")

val dimensions = forest.size - 1
def neighbours = neighboursGenerator(dimensions)

var visible = 4 * forest.size - 4 // all border trees are visible
var maxScore = 0

for x <- 1 until dimensions do
  for y <- 1 until dimensions do
    val directions = Direction.values

    if directions.exists(isVisible(x, y)) then visible += 1

    val score = directions.map(calculateScore(x, y)).reduce(_ * _)
    if (score > maxScore) then maxScore = score

println(s"Part1: $visible")
println(s"Part2: $maxScore")
