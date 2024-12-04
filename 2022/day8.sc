import scala.collection.mutable
import java.nio.file.Paths
import scala.io.Source

enum Direction:
  case Left
  case Right
  case Down
  case Up

type Forest = mutable.Map[Int, IndexedSeq[Char]]
val forest: Forest = mutable.Map.empty

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines
  .zipWithIndex
  .foreach((line, x) => forest.put(x, line.toIndexedSeq))

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

def getHeight(p: (Int, Int)): Int = forest(p(1))(p(0)) // x and y are switched

def isVisible(x: Int, y: Int)(direction: Direction): Boolean =
  val tree = forest(y)(x)
  !neighbours(x, y, direction).exists(getHeight(_) >= tree)

def calculateScore(x: Int, y: Int)(direction: Direction): Int =
  val treeHeight = getHeight((x, y))
  var result = 0
  var found = false
  val it = neighbours(x, y, direction) ++ Seq((-1, -1))
  while !found && it.hasNext do
    val p = it.next()
    if (p(0) == -1) then result -= 1 // we reached the edge
    else
      if getHeight(p) >= treeHeight then found = true;
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
