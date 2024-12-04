package lt.dzebleckis.aoc_2019.day4
import org.scalatest.flatspec.AnyFlatSpec
import lt.dzebleckis.aoc_2019.day4.Solution

class SolutionSpec extends AnyFlatSpec {

  "Day 4 solution" should "validate valid numbers for part 1" in {
    Seq(111111)
      .foreach(n => assert(Solution.valid(n) === true))
  }

  it should "validate invalid numbers for part 1" in {
    Seq(
      223450,
      123789,
      178821
    ).foreach(n => assert(Solution.valid(n) === false))
  }

  it should "validate valid numbers for part 2" in {
    Seq(112233, 111122)
      .foreach(n => assert(Solution.valid2(n) === true, n))
  }

  it should "validate invalid numbers for part 2" in {
    Seq(
      223450,
      123789,
      178821,
      111111,
      123444
    ).foreach(n => assert(Solution.valid2(n) === false))
  }
}
