package lt.dzebleckis.aoc_2019.day3

import org.scalatest.flatspec.AnyFlatSpec

class LineSpec extends AnyFlatSpec {

  "Line" should "return no intersect point" in {
    val line1 =  Line.verticalLine(Point(1, 1), 5)
    val line2 = Line.verticalLine(Point(2, 1), 5)

    val intersection = Line.intersection(line1, line2)

    assert(None === intersection)
  }

  it should "return intersect point1" in {
    val line1 =  Line.verticalLine(Point(1, 1), 5)
    val line2 = Line.horizontalLine(Point(0, 2), 5)

    val intersection = Line.intersection(line1, line2)

    assert(Some(Point(1, 2)) === intersection)
  }
}
