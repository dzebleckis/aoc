import java.nio.file.Paths
import scala.io.Source

type Context = Map[String, Long]
type Expr = Context => Long
object Expr:
  def number(n: Long): Expr = _ => n
  def plus(left: Expr, right: Expr): Expr = c => left(c) + right(c)
  def times(left: Expr, right: Expr): Expr = c => left(c) * right(c)
  def variable(name: String): Expr = c => c.getOrElse(name, 0)

case class Monkey(
    operation: Expr,
    items: Seq[Long],
    test: Int,
    onTrue: Int,
    onFalse: Int,
    activity: Long = 0
):
  override def toString(): String =
    s"M(items=${items.mkString(",")}, activity=$activity)"

def parseNumberOrVariable(input: String): Expr =
  if input == "old" then Expr.variable("old") else Expr.number(input.toInt)

def parseOperation(input: String): Expr =
  val e = input.trim.split(" ").map(_.trim)

  if e.size != 3 then
    throw Exception(s"Could not parse expression ${e.mkString("-")}")

  val left = parseNumberOrVariable(e(0))
  val right = parseNumberOrVariable(e(2))

  e(1) match
    case "+" => Expr.plus(left, right)
    case "*" => Expr.times(left, right)
    case _   => throw Exception(s"Could not parse operation ${e(1)}")

def lastInt(input: String) = input.split(" ").last.trim.toInt

def parseMoney(it: Iterator[String]): Monkey =
  val items = it.next().split(":").last.split(",").map(_.trim.toLong)
  val operation = parseOperation(it.next().split("=").last)
  val test = lastInt(it.next())
  val onTrue = lastInt(it.next())
  val onFalse = lastInt(it.next())

  Monkey(operation, items, test, onTrue, onFalse)

def play(
    index: Int,
    division: (Long) => Long,
    monkeys: IndexedSeq[Monkey]
): IndexedSeq[Monkey] =
  val monkey = monkeys(index)
  var result = monkeys
  monkey.items.foreach(item =>
    val level = division(monkey.operation(Map("old" -> item)))
    val idx = if level % monkey.test == 0 then monkey.onTrue else monkey.onFalse

    val monkeyToUpdate = result(idx)
    result = result.updated(
      idx,
      monkeyToUpdate.copy(items = monkeyToUpdate.items :+ level)
    )
  )

  result.updated(
    index,
    monkey.copy(
      items = Seq.empty,
      activity = monkey.activity + monkey.items.size
    )
  )

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines()

var monkeys = IndexedSeq.empty[Monkey]

while content.hasNext do
  if content.next().startsWith("Monkey") then
    monkeys = monkeys :+ parseMoney(content)

var result = monkeys

def count(monkeys: Seq[Monkey]) =
  monkeys.map(_.activity).sortWith(_ > _).take(2).reduce(_ * _)

val part1 = (item: Long) => Math.floor(item / 3).toLong
for _ <- 1 to 20 do
  (0 until monkeys.size).foreach(i => result = play(i, part1, result))

println(s"Part1: ${count(result)}")

val lcm = monkeys.map(_.test).reduce(_ * _)
val part2 = (item: Long) => item % lcm

for _ <- 1 to 10000 do
  (0 until monkeys.size).foreach(i => result = play(i, part2, result))

println(s"Part2: ${count(result)}")
