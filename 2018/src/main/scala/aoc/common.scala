package aoc

import scala.collection.Iterator
import scala.io.Source

object Common {
  private val number = raw"^([+,-])(\d+)".r

  def lines(input: String): Iterator[String] = {
    Source.fromFile(s"./src/main/scala/aoc/$input", "UTF-8").getLines()
  }

  def sum(memo: Int, signed: String): Int = {
    signed match {
      case number("+", n) => memo + n.toInt
      case number("-", n) => memo - n.toInt
    }
  }
}
