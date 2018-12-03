package aoc.day2

import scala.collection.mutable.Map

object Main {

    import aoc.Common._

    def main(args: Array[String]): Unit = {
        part1()
    }

    private def part1(): Unit = {
        val (l, r) = lines("day2/day2.input")
            .map(count)
            .reduceLeft[(Int, Int)] { case (l, r) => ((l._1 + r._1) -> (l._2 + r._2)) }

        println(l * r)
    }

    private def count(line: String): (Int, Int) = {
        val freq = Map.empty[Char, Int]
        line.foreach { c =>
            freq.update(c, freq.getOrElse(c, 0) + 1)
        }
        val two  = freq.exists { case (_, count) => count == 2 }
        val three  = freq.exists { case (_, count) => count == 3 }

        ((if(two) 1 else 0) -> (if(three) 1 else 0))
    }
}

