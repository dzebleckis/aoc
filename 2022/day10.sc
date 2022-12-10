import java.nio.file.Paths
import scala.io.Source

enum Instruction:
  case Noop
  case AddX(count: Int)

val instructions = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines()
  .map(line =>
    if line.startsWith("noop") then Instruction.Noop
    else
      val Array(_, count) = line.split(" ")
      Instruction.AddX(count.toInt)
  )

def getSymbolChar(cycle: Int, register: Int): String =
  val position = (cycle - 1) % 40
  if register >= position - 1 && register <= position + 1 then "#" else "."

var cycle = 0
var running = true
var noop = 0
var cnt = 0
var register = 1
var sum = 0
var points = Set(20, 60, 100, 140, 180, 220)

while running && cycle < 500 do
  cycle += 1
  if noop == 0 then
    register += cnt
    if instructions.hasNext then
      instructions.next() match
        case Instruction.Noop =>
          cnt = 0
          noop += 1
        case Instruction.AddX(count) =>
          cnt = count
          noop += 2
    else running = false
  noop -= 1

  print(getSymbolChar(cycle, register))

  if cycle % 40 == 0 then println()
  if points.contains(cycle) then sum += cycle * register

println(s"Part1 $sum")
