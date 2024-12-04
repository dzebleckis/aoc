package lt.dzebleckis.aoc_2019.day1

import org.scalatest.flatspec.AnyFlatSpec

class OperationalIteratorSpec extends AnyFlatSpec {
  private val operation = (n: Int) => n / 3 - 2
  private val hasNext = (o: Int) => o > 0

  "OperationalIterator" should "require fuel 2 for module of mass 14" in {
    val it = new OperationalIterator[Int](14, operation, hasNext)
    it.drop(1)
    assert(2 === it.sum)
  }

  it should "require 966 fuel for module of mass 1969" in {
    val it = new OperationalIterator[Int](1969, operation, hasNext)
    it.drop(1)
    assert(966 === it.sum)
  }

  it should "require 50346 fuel for module of mass 100756" in {
    val it = new OperationalIterator[Int](100756, operation, hasNext)
    it.drop(1)
    assert(50346 === it.sum)
  }
}
