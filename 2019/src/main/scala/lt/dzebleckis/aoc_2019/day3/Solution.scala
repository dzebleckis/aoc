package lt.dzebleckis.aoc_2019.day3

import scala.io.Source

object Solution {
  sealed trait Direction

  def main(args: Array[String]): Unit = {
    val lines = Source
      .fromResource("2019_day_3.input")
      .getLines()
      .toSeq

    val firstLine = lines.head
    val secondLine = lines(1)

    println(closestDistance(firstLine, secondLine))
  }

  def closestDistance(firstPath: String, secondPath: String): Int = {
    intersections(firstPath, secondPath)
      .map(point => Math.abs(point.x) + Math.abs(point.y))
      .min
  }

  private def intersections(firstPath: String, secondPath: String): Set[Point] = {

    val firstWireLines = Parser.parse(firstPath)
    val secondWireLines = Parser.parse(secondPath)
    var result = Set.empty[Point]

    for (firstLine <- firstWireLines) {
      for (secondLine <- secondWireLines) {
        firstLine
          .intersection(secondLine)
          .foreach { point =>
            if (point != Point(0, 0)) {
              result = result + point
            }
          }
      }
    }
    result
  }

  def minSteps(firstPath: String, secondPath: String): Int = {

    val firstWireLines = Parser.parse(firstPath)
    val secondWireLines = Parser.parse(secondPath)

    var steps1 = collection.mutable.Map.empty[Point, Int].withDefaultValue(0)
    var steps2 = collection.mutable.Map.empty[Point, Int].withDefaultValue(0)

    for (point <- intersections(firstPath, secondPath)) {
      for (line <- firstWireLines) {
        steps1(point) = steps1(point) + line.distance
        if (line.conains(point)) {
          println(s" line $line contains $point")
          println(steps1)
          println(line.distance(point))

          steps1(point) = steps1(point) + line.distance(point)

        }
      }
      for (line <- secondWireLines) {
        steps2(point) = steps1(point) + line.distance
        if (line.conains(point)) {
          println(s" line $line contains $point")
          steps2(point) = steps1(point) + line.distance(point)
          println(steps2)
        }
      }
    }

    12
  }
}

object Parser {
  def parse(path: String): Array[Line] = {
    var current = Point(0, 0)
    path
      .split(",")
      .map { s =>
        val tmp = current
        val distance = s.tail.toInt
        s.head match {
          case 'R' =>
            current = Point(tmp.x + distance, tmp.y)
            Line.horizontalLine(tmp, distance)
          case 'L' =>
            current = Point(tmp.x - distance, tmp.y)
            Line.horizontalLine(tmp, -distance)
          case 'U' =>
            current = Point(tmp.x, tmp.y + distance)
            Line.verticalLine(tmp, distance)
          case 'D' =>
            current = Point(tmp.x, tmp.y - distance)
            Line.verticalLine(tmp, -distance)
        }
      }
  }
}

final case class Point(x: Int, y: Int)

object Line {
  def verticalLine(start: Point, distance: Int): Line = {
    val startY = Math.min(start.y, start.y + distance)
    val endY = Math.max(start.y, start.y + distance)
    VerticalLine(Point(start.x, startY), Point(start.x, endY))
  }

  def horizontalLine(start: Point, distance: Int): Line = {
    val startX = Math.min(start.x, start.x + distance)
    val endX = Math.max(start.x, start.x + distance)

    HorizontalLine(Point(startX, start.y), Point(endX, start.y))
  }

  protected def intersection(first: Line, second: Line): Option[Point] = {
    val (h, v) = first match {
      case h: HorizontalLine => (h, second)
      case v: VerticalLine => (second, v)
    }

    if (h.start.x <= v.start.x
        && h.end.x >= v.start.x
        && h.start.y >= v.start.y
        && h.start.y <= v.end.y) {
      Some(Point(v.start.x, h.start.y))
    } else {
      None
    }
  }
}

sealed trait Line {
  val start: Point
  val end: Point

  def intersection(other: Line): Option[Point] = Line.intersection(this, other)
  def conains(point: Point): Boolean
  def distance: Int
  def distance(point: Point): Int
}

final case class VerticalLine(start: Point, end: Point) extends Line {

  def conains(point: Point): Boolean = {
    start.x == point.x && start.y <= point.y && end.y >= point.y
  }

  def distance: Int = end.y - start.y

  def distance(point: Point): Int = point.y - start.y
}

final case class HorizontalLine(start: Point, end: Point) extends Line {

  def conains(point: Point): Boolean = {
    start.y == point.y && start.x <= point.x && end.x >= point.x
  }

  def distance: Int = end.x - start.x

  def distance(point: Point): Int = point.x - start.x
}
