//> using file utils.sc
import scala.collection.immutable.Map
import scala.collection.immutable.Map.WithDefault

import utils.readInput

val content = readInput(args)

// println(content)

var rules = Map.empty[Int, Seq[Int]].withDefaultValue(Seq.empty[Int])
val ruleRegex = """(\d+)\|(\d+)""".r
var secondPart = collection.mutable.Seq.empty[Seq[Int]]

var parsingRules = true
content
  .foreach { line =>
    if parsingRules then
      line match {
        case ruleRegex(prefix, last) =>
          val key = prefix.toInt
          rules += (key, rules(key) :+ last.toInt)
        case _ =>
          parsingRules = false
      }
    else secondPart = secondPart :+ line.split(",").map(_.toInt)
  }

object PageNumbersOrderind extends Ordering[Int] {
  def compare(a: Int, b: Int) =
    if rules.contains(a) && rules(a).contains(b) then -1
    else if rules.contains(b) && rules(b).contains(a) then 1
    else 0
}

var part1 = 0
var part2 = 0

for pageNumbers <- secondPart
do
  val sorted = pageNumbers.sorted(PageNumbersOrderind)
  if pageNumbers.mkString("") == sorted.mkString("") then part1 += sorted(sorted.length / 2)
  else part2 += sorted(sorted.length / 2)

println(s"part1: $part1")
println(s"part2: $part2")
