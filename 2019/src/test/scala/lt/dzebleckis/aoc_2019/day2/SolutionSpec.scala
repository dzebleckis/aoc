package lt.dzebleckis.aoc_2019.day2
import org.scalatest.flatspec.AnyFlatSpec

class SolutionSpec extends AnyFlatSpec {
  "Solution" should "process code1" in {
    val code = "1,0,0,0,99".split(",")
    val result = "2,0,0,0,99"
    assert(Solution.processCode(code).mkString(",") === result)
  }

  it should "process code 2" in {
    val code = "2,3,0,3,99".split(",")
    val result = "2,3,0,6,99"
    assert(Solution.processCode(code).mkString(",") === result)
  }

  it should "process code 3" in {
    val code = "2,4,4,5,99,0".split(",")
    val result = "2,4,4,5,99,9801"
    assert(Solution.processCode(code).mkString(",") === result)
  }

  it should "process code 4" in {
    val code = "1,1,1,4,99,5,6,0,99".split(",")
    val result = "30,1,1,4,2,5,6,0,99"
    assert(Solution.processCode(code).mkString(",") === result)
  }
}
