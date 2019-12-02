package lt.dzebleckis.aoc_2019.day3
import org.scalatest.flatspec.AnyFlatSpec

class SolutionSpec extends AnyFlatSpec {

    "Manhatan distance" should "calulate distance 1" in {
        val firstPath = "R8,U5,L5,D3"
        val secondPath = "U7,R6,D4,L4"
        assert(3 === Solution.closestDistance(firstPath, secondPath))
    }

    it should "calculate distance 2" in {
        assert(1 === 3)
    }
}