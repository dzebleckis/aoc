//> using file utils.sc
import utils.readInput

val content = readInput(args)

enum Color:
  case Red, Green, Blue

import Color.*

case class Cubes(count: Int, color: Color)
type GameSet = Seq[Cubes]
case class Game(id: Int, gameSets: Seq[GameSet])

val lineRegex = """Game (\d+): (.+)""".r
val cubesRegex = """(\d+)\s+(\w+)""".r

val maxRed = 12
val maxGreen = 13
val maxBlue = 14

def parseCubes(input: String): Cubes =
  input.trim() match
    case cubesRegex(count, colorString) =>
      val color = colorString match
        case "red"   => Red
        case "green" => Green
        case "blue"  => Blue
      Cubes(count.toInt, color)

// println(parseCubes(" 6  red"))

def parseGameSet(input: String): GameSet =
  input
    .split(",")
    .map(parseCubes)
    .toIndexedSeq

// parseGameSet("6 red, 1 blue, 3 green")

def parseGame(input: String): Game =
  input match
    case lineRegex(id, rest) =>
      Game(id.toInt, rest.split(";").map(parseGameSet).toIndexedSeq)

val games = content.map(parseGame)

def aboveLimit(cubes: Cubes) =
  cubes.color match
    case Red   => cubes.count > maxRed
    case Green => cubes.count > maxGreen
    case Blue  => cubes.count > maxBlue

def notPossibleGameSet(gameSet: GameSet) =
  gameSet.exists(aboveLimit)

def minimumNumberOfCubes(gameSets: Seq[GameSet]): (Int, Int, Int) =
  val Seq(c1, c2, c3) = gameSets.flatten
    .groupBy(_.color)
    .map((_, group) => group.maxBy(_.count).count)
    .toSeq

  (c1, c2, c3)

val part1 = games
  .filterNot(game => game.gameSets.exists(notPossibleGameSet))
  .map(_.id)

println(s"Part1: ${part1.sum}")

val part2 = games
  .map(game => minimumNumberOfCubes(game.gameSets))
  .map((r, g, b) => r * g * b)

println(s"Part2: ${part2.sum}")
