package lt.dzebleckis.aoc_2019.day1

import scala.io.Source

object Solution {
  def main(args: Array[String]): Unit = {
    val operation = (n: Int) => n / 3 - 2

    val masses = Source
      .fromResource("2019_day_1.input")
      .getLines()
      .map(_.toInt)
      .map(operation)
      .toSeq

    println(masses.sum)

    val part2 = masses.map { n =>
      new OperationalIterator(n, operation, (o: Int) => o > 0)
        .drop(0) // we don't need module mass
        .sum
    }.sum

    println(part2)
  }
}

class OperationalIterator[T](
    initial: T,
    operation: T => T,
    hasNext: T => Boolean
) extends Iterator[T] {
  private var _next: T = initial

  override def hasNext: Boolean = hasNext(_next)

  override def next(): T = {
    val current = _next
    _next = operation(_next)
    current
  }
}
