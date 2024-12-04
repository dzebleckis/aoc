package lt.dzebleckis.aoc_2019.day2
import scala.io.Source

object Solution {
  sealed trait Opcode
  final object Terminate extends Opcode
  final object Add extends Opcode
  final object Multiply extends Opcode

  def main(args: Array[String]): Unit = {
    val code = Source
      .fromResource("2019_day_2.input")
      .getLines()
      .mkString("")
      .split(",")

    var registry = code.toArray
    registry(1) = "12"
    registry(2) = "2"

    println(processCode(registry).head)

    val output = 19690720
    var searching = true
    var noun = 0

    while (searching && noun <= 99) {
      var verb = 0
      while (searching && verb <= 99) {
        registry = code.toArray
        registry(1) = noun.toString
        registry(2) = verb.toString

        if (processCode(registry).head == output) {
          println(100 * noun + verb)
          searching = false
        }
        verb += 1
      }
      noun += 1
    }

  }

  def processCode(code: Array[String]): Array[Int] = {
    var running = true
    var instructionPointer = 0
    val opcodes = code.map(_.toInt)

    while (running) {
      int2Opcode(opcodes(instructionPointer)) match {
        case Terminate => running = false
        case Add => execute(instructionPointer, opcodes, _ + _)
        case Multiply => execute(instructionPointer, opcodes, _ * _)
      }
      instructionPointer += 4
    }
    opcodes
  }

  private def execute(
      instructionPointer: Int,
      opcodes: Array[Int],
      op: (Int, Int) => Int
  ): Unit = {
    val leftAddress = opcodes(instructionPointer + 1)
    val rightAddress = opcodes(instructionPointer + 2)
    val resultAddress = opcodes(instructionPointer + 3)

    opcodes(resultAddress) = op(opcodes(leftAddress), opcodes(rightAddress))
  }

  private def int2Opcode(i: Int): Opcode = i match {
    case 99 => Terminate
    case 1 => Add
    case 2 => Multiply
    case o =>
      throw new IllegalArgumentException(s"Operation not recognized $o")
  }
}
