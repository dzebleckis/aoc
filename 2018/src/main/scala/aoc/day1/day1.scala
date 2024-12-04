package aoc.day1

import aoc.Common._
import scala.collection.mutable.Map

object Main {
  def main(args: Array[String]): Unit = {
    part1()
    part2()
  }

  def part1(): Unit = {
    val result = lines("day1/day1.input")
      .foldLeft(0) { (m, n) =>
        sum(m, n)
      }

    println(result)
  }

  def part2(): Unit = {
    val ring = new Ring(lines("day1/day1.input").toVector)
    val seen = Map.empty[Int, Boolean]
    var done = false
    var memo = 0

    while (!done) {
      val s = sum(memo, ring.next)
      if (seen.contains(s)) {
        println(s)
        done = true
      }
      seen += (s -> true)
      memo = s
    }
  }
}

class Ring[T](data: Vector[T]) {
  var idx = 0

  def next: T = {
    val r = data(idx % data.length)
    idx = idx + 1
    r
  }
}
