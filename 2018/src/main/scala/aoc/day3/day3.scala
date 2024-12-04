package aoc.day3

import aoc.Common._
import scala.collection.mutable.Map

object Main {
  def main(args: Array[String]): Unit = {
    // part1()
    part2()
  }

  private def part2(): Unit = {
    val input = Seq("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")
    // .map(parseLine)
    // .foreach { c =>
    //     println(c)
    //     println(c.l -> c.r)
    // }

    val claims = input.map(parseLine)

    // println(claims(0).overlap(claims(1)))
    // println(claims(0).overlap(claims(2)))
    // println(claims(1).overlap(claims(2)))

    var found = false
    var i, j = 0
    val length = input.length
    var result: Option[Claim] = None
    while (!found && i < length) {
      println(claims(i))
      j = i + 1
      var searching = true

      while (searching && j < length) {
        if (claims(i).overlap(claims(j))) {
          searching = false
        }
        j = j + 1
      }
      if (searching) {
        found = true
        result = Some(claims(i))
      }
      i = i + 1
    }
    println(result)
  }

  private def part1(): Unit = {
    val board = Map.empty[Int, Map[Int, Int]]

    lines("day3/day3.input")
      .map(parseLine)
      .flatMap(_.points)
      .foreach { point =>
        val x = board.getOrElseUpdate(point.x, Map.empty[Int, Int])
        val value = x.getOrElseUpdate(point.y, 0)
        x.update(point.y, value + 1)
        board.update(point.x, x)
      }

    val result = for {
      a <- board.values
      b <- a.values if b > 1
    } yield b

    val ss = result
      .map(_ => 1)
      .reduce(_ + _)

    println(ss)
  }

  private def parseLine(line: String): Claim = {
    val parts = line.split(" ")
    val coords = parts(2).split(",")
    val dim = parts(3).split("x")
    val left = coords(0).toInt
    val top = coords(1).split(":")(0).toInt

    new Claim(parts(0), left, top, dim(0).toInt, dim(1).toInt)
  }

  private class Claim(
      id: String,
      left: Int,
      top: Int,
      width: Int,
      height: Int
  ) {
    val l = Point(left + 1, top + 1)
    val r = Point(left + width, top + height)

    def points: Seq[Point] = {
      for {
        x <- left + 1 to left + width
        y <- top + 1 to top + height
      } yield Point(x, y)
    }

    def toPoint: (Point, Point) = {
      Point(left + 1, top + 1) -> Point(left + width, top + height)
    }

    def overlap(other: Claim): Boolean = {
      // if one rectangle is on left side of other
      if (l.x > other.r.x || other.l.x > r.x) {
        // println(s" ${l.x > other.r.x}")
        // println(s"${other.l.x > r.x}")
        // println(s"left [$l,$r], [${other.l},${other.r}]")
        false
      } else if (l.y > other.r.y || other.l.y > r.y) { // if one rectangle is above other
        // println(l.y < other.r.y)
        // println(other.l.y < r.y)
        // println(s"above [$l,$r], [${other.l},${other.r}]")
        false
      } else {
        true
      }
    }

    override def toString(): String = {
      s"$id @ $left,$top: ${width}x$height"
    }
  }

  private case class Point(x: Int, y: Int)
}
