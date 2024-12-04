package lt.dzebleckis.aoc_2019.day4

object Solution {

  def main(args: Array[String]): Unit = {

    val result1 = (178416 to 676461)
      .filter(valid)
      // .take(100)
      // .foreach(println)
      .length

    println(result1)

    val result2 = (178416 to 676461)
      .filter(valid2)
      // .take(100)
      // .foreach(println)
      .length

    println(result2)
  }

  def valid(i: Int): Boolean = {
    adjacentDigits(i) && increasingOrder(i)
  }

  def valid2(i: Int): Boolean = {
    adjacentDigits2(i) && increasingOrder(i)
  }

  private def increasingOrder(i: Int): Boolean = {
    val asString = i.toString
    var previous = asString.head

    asString.tail.forall { c =>
      val r = c >= previous
      previous = c
      r
    }
  }

  private def adjacentDigits(i: Int): Boolean = {
    val asString = i.toString
    var previous = asString.head

    asString.tail.exists { c =>
      if (c == previous) {
        true
      } else {
        previous = c
        false
      }
    }
  }

  private def adjacentDigits2(i: Int): Boolean = {
    val asString = i.toString
    var previous = asString.head
    var length = 1
    var result = false

    asString.tail.foreach { c =>
      if (c == previous) {
        length = length + 1
      } else {
        previous = c
        if (length == 2) {
          result = true
        }
        length = 1
      }
    }

    result || length == 2
  }

}
