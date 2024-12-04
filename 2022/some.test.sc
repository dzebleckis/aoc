//> using lib "org.scalactic::scalactic::3.2.14"
//> using lib "org.scalatest::scalatest::3.2.14"
import org.scalatest.funsuite.AnyFunSuite

class MyTests extends AnyFunSuite:
  test("foo") {
    assert(2 + 2 === 5)
  }

  test("foo2") {
    assert(2 + 2 === 4)
  }
