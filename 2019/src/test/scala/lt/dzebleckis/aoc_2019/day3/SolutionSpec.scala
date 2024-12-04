package lt.dzebleckis.aoc_2019.day3

import org.scalatest.flatspec.AnyFlatSpec

class SolutionSpec extends AnyFlatSpec {

  "Manhatan distance" should "calulate distance 1" in {
    val firstPath = "R8,U5,L5,D3"
    val secondPath = "U7,R6,D4,L4"
    assert(6 === Solution.closestDistance(firstPath, secondPath))
  }

  it should "calculate distance 2" in {
    val firstPath = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
    val secondPath = "U62,R66,U55,R34,D71,R55,D58,R83"
    assert(159 === Solution.closestDistance(firstPath, secondPath))
  }

  it should "calculate distance 3" in {
    val firstPath = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
    val secondPath = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
    assert(135 === Solution.closestDistance(firstPath, secondPath))
  }

  it should "calculate steps 1" in {
    val firstPath = "R8,U5,L5,D3"
    val secondPath = "U7,R6,D4,L4"
    assert(30 === Solution.minSteps(firstPath, secondPath))
  }

  it should "calculate steps 2" ignore {
    val firstPath = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
    val secondPath = "U62,R66,U55,R34,D71,R55,D58,R83"
    assert(610 === Solution.minSteps(firstPath, secondPath))
  }

  it should "calculate steps 3" ignore {
    val firstPath = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
    val secondPath = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
    assert(410 === Solution.minSteps(firstPath, secondPath))
  }
}
