package lt.dzebleckis.aoc_2019.day3

object Solution {

  sealed trait Direction


  def main(args: Array[String]): Unit = {
    println("hi from day3")
  }

  def closestDistance(firstPath: String, secondPath: String): Int = {
    42
  }
}

object Parser {


}

final case class Point(x: Int, y: Int)


object Line {

  def verticalLine(start: Point, distance: Int): Line = {
    VerticalLine(Point(start.x, start.y), Point(start.x, start.y + distance))
  }

  def horizontalLine(start: Point, distance: Int): Line = {
    HorizontalLine(Point(start.x, start.y), Point(start.x, start.x + distance))
  }

  def intersection(first: Line, second: Line): Option[Point] = {
    first match {
      case h: HorizontalLine => second match {
        case _:HorizontalLine => None
        case v: VerticalLine =>
          if (v.start.x <= h.start.x && v.end.x >= h.start.x) {
            Some(Point(1, 1))
          }
          None
      }
      case VerticalLine(start, end) => second match {
        case _:VerticalLine => None
        case HorizontalLine(start, end) => Some(Point(1, 1))
      }
    }
  }
}

sealed trait Line
final case class VerticalLine(start: Point, end: Point) extends Line
final case class HorizontalLine(start: Point, end: Point) extends Line
