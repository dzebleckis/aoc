package aoc.day2

import scala.collection.mutable.Map

object Main {
  import aoc.Common._

  def main(args: Array[String]): Unit = {
    //part1()
    part2()
  }

  private def part2(): Unit = {
    val input = lines("day2/day2.input").toVector
    val lenght = input.size

    var i, j = 0
    var found = false
    var result: Option[(String, String)] = None

    while (!found && i < lenght) {
      j = i + 1
      val s1 = input(i)
      while (!found && j < lenght) {
        val s2 = input(j)
        if (similar(s1, s2, 1)) {
          result = Some(s1 -> s2)
          found = true
        }
        j = j + 1
      }
      i = i + 1
    }

    result.foreach {
      case (s1, s2) =>
        println(s1)
        println(s2)
        println(s1.toSeq.intersect(s2.toSeq))
    }
  }

  private def similar(s1: String, s2: String, threshold: Int): Boolean = {
    var diff, idx = 0
    val length = Math.min(s1.length, s2.length)
    while (diff <= threshold && idx < length) {
      if (s1(idx) != s2(idx)) {
        diff = diff + 1
      }
      idx = idx + 1
    }
    diff <= threshold
  }

  private def part1(): Unit = {
    val (l, r) = lines("day2/day2.input")
      .map(count)
      .reduceLeft[(Int, Int)] {
        case (l, r) => ((l._1 + r._1) -> (l._2 + r._2))
      }

    println(l * r)
  }

  private def count(line: String): (Int, Int) = {
    val freq = Map.empty[Char, Int]
    line.foreach { c =>
      freq.update(c, freq.getOrElse(c, 0) + 1)
    }
    val two = freq.exists { case (_, count)   => count == 2 }
    val three = freq.exists { case (_, count) => count == 3 }

    ((if (two) 1 else 0) -> (if (three) 1 else 0))
  }
}
