package lt.dzebleckis.aoc_2019.day3

import org.scalatest.flatspec.AnyFlatSpec

class LineSpec extends AnyFlatSpec {

  "Line" should "return no intersect point" in {
    val verticalLine1 = Line.verticalLine(Point(1, 1), 5)
    val verticalLine2 = Line.verticalLine(Point(2, 1), 5)

    val intersection = verticalLine1.intersection(verticalLine2)

    assert(None === intersection)
  }

  it should "return intersect point lines1" in {
    val horizontalLine = Line.horizontalLine(Point(0, 2), 5)
    val verticalLine = Line.verticalLine(Point(1, 1), 5)

    val result = Some(Point(1, 2))
    assert(result === verticalLine.intersection(horizontalLine))
    assert(result === horizontalLine.intersection(verticalLine))
  }

  it should "return intersect point lines2" in {
    val horizontalLine = Line.horizontalLine(Point(1, 1), 5)
    val verticalLine = Line.verticalLine(Point(1, 1), 5)

    val result = Some(Point(1, 1))
    assert(result === verticalLine.intersection(horizontalLine))
    assert(result === horizontalLine.intersection(verticalLine))
  }

  it should "return intersect point lines3" in {
    val horizontalLine = Line.horizontalLine(Point(1, 1), 5)
    val verticalLine = Line.verticalLine(Point(6, 0), 5)

    val result = Some(Point(6, 1))
    assert(result === verticalLine.intersection(horizontalLine))
    assert(result === horizontalLine.intersection(verticalLine))
  }

  it should "return not intersect point1" in {
    val horizontalLine = Line.horizontalLine(Point(0, 1), 5)
    val verticalLine = Line.verticalLine(Point(2, 2), 5)

    val result = None
    assert(result === verticalLine.intersection(horizontalLine))
    assert(result === horizontalLine.intersection(verticalLine))
  }

  it should "return intersection" in {
    val horizontalLine = HorizontalLine(Point(3, 5), Point(8, 5))
    val verticalLine = VerticalLine(Point(6, 3), Point(6, 7))
    val result = Some(Point(6, 5))

    assert(result === verticalLine.intersection(horizontalLine))
    assert(result === horizontalLine.intersection(verticalLine))
  }

  it should "construst horizontal line" in {
    assert(HorizontalLine(Point(0, 0), Point(5, 0)) === Line.horizontalLine(Point(0, 0), 5))
    assert(HorizontalLine(Point(-5, 0), Point(0, 0)) === Line.horizontalLine(Point(0, 0), -5))
  }

  it should "construst vertical line" in {
    assert(VerticalLine(Point(1, 1), Point(1, 6)) === Line.verticalLine(Point(1, 1), 5))
    assert(VerticalLine(Point(5, 4), Point(5, 7)) === Line.verticalLine(Point(5, 7), -3))
  }
}
