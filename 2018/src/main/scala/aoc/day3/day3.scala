package aoc.day3

import aoc.Common._
import scala.collection.mutable.Map

object Main {

    def main(args: Array[String]): Unit = {

        val board = Map.empty[Int, Map[Int, Int]]
        lines("day3/day3.input")
        .take(4)
        .map(parseLine)
        .foreach { claim =>
            println(claim)
        }

    }

    private def parseLine(line: String): Claim = {
        val parts = line.split(" ")
        val coords = parts(2).split(",")
        val dim = parts(3).split("x")

        val left = coords(0).toInt
        val top = coords(1).split(":")(0).toInt

        Claim(left, top, dim(0).toInt, dim(1).toInt)
    }

    private def overlap(c1: Claim, c2: Claim): Boolean = {

        val (l1, r1) = c1.toPoint
        val (l2, r2) = c2.toPoint
        // If one rectangle is on left side of other
        if (l1.x > r2.x || l2.x > r1.x) {
            false
        } else if () { // If one rectangle is above other
            false
        } else {
            true
        }
    }

    private case class Claim(left: Int, top: Int, width: Int, height: Int) {
        def toPoint: (Point, Point) = Point(left, top) -> Point(left + width, top + height)
    }
    private case class Point(x: Int, y: Int)
}