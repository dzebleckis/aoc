//> using file utils.sc

import utils.readInput

def scanLine(
    prev: Option[String],
    current: String,
    next: Option[String]
): Seq[Int] =
  var result = Seq.empty[Int]
  var acc = ""
  var adjacent = false

  for (c, idx) <- current.zipWithIndex do
    if c.isDigit then
      acc = acc + c
      adjacent = adjacent || isAdjacent(idx, prev, current, next)
    else
      if adjacent then result = result :+ acc.toInt
      acc = ""
      adjacent = false

  if adjacent then result = result :+ acc.toInt

  result

def isAdjacent(
    idx: Int,
    prev: Option[String],
    current: String,
    next: Option[String]
): Boolean =
  isAdjacent(idx, prev) ||
    isAdjacent(idx, Some(current)) ||
    isAdjacent(idx, next) ||
    prev.fold(false)(l => isSymbol(l.charAt(idx))) ||
    next.fold(false)(l => isSymbol(l.charAt(idx)))

def isAdjacent(idx: Int, line: Option[String]): Boolean =
  line.fold(false) { l =>
    val leftIdx = Math.max(0, idx - 1)
    val rightIdx = Math.min(l.length() - 1, idx + 1)

    isSymbol(l.charAt(leftIdx)) || isSymbol(l.charAt(rightIdx))
  }

def isSymbol(c: Char) = !(c.isDigit || c == '.')

val content = readInput(args)

def getLine(idx: Int): Option[String] =
  if idx < 0 || idx >= content.length then None else Some(content(idx))

val part1 =
  for (line, idx) <- content.zipWithIndex
  yield scanLine(getLine(idx - 1), line, getLine(idx + 1))

println(s"Part 1: ${part1.flatten.sum}")

case class Number(x: Int, start: Int, end: Int, value: Int)
class Numbers:

  val inner = collection.mutable.Map[Int, collection.mutable.Map[Int, Int]]()

  def add(number: Number) =
    if inner.isDefinedAt(number.x) then
      val m = inner(number.x)
      (number.start until number.end).foreach(i => m.put(i, number.value))
    else
      inner.put(
        number.x,
        collection.mutable.Map.from(
          (number.start until number.end).map((_, number.value))
        )
      )

  def get(x: Int, y: Int): Option[Int] =
    if inner.isDefinedAt(x) && inner(x).isDefinedAt(y) then Some(inner(x)(y))
    else None

  override def toString(): String = inner.toString()

def neighbours(x: Int, y: Int): Seq[(Int, Int)] =
  for {
    xi <- Seq(x - 1, x, x + 1)
    yi <- Seq(y - 1, y, y + 1)
    if (xi, yi) != (x, y)
  } yield (xi, yi)

val numbers = Numbers()

val r = """(\d+)""".r

for
  (line, idx) <- content.zipWithIndex
  m <- r.findAllMatchIn(line)
do numbers.add(Number(idx, m.start, m.end, m.group(0).toInt))

val part2 = for
  (line, x) <- content.zipWithIndex
  (c, y) <- line.zipWithIndex if c == '*'
yield neighbours(x, y)
  .flatMap((nx, ny) => numbers.get(nx, ny))
  .toSet // we should deduplicate by coordinates and not only value

val result2 = part2.filter(_.size == 2).map(s => s.head * s.last).sum

println(s"Part 2: $result2")
