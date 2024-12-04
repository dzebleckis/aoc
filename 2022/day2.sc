import scala.io.Source

enum Move:
  case Rock, Scissors, Paper

type OponentMove = Move
type MyMove = Move

enum Outcome:
  case Win, Lose, Drawn

type Game = (MyMove, OponentMove)

val content =
  Source
    .fromFile(s"./${args.toSeq.head}")
    .getLines()
    .map(_.split(" "))
    .toSeq

def victory(game: Game) =
  game match
    case (Move.Rock, Move.Scissors)  => true
    case (Move.Scissors, Move.Paper) => true
    case (Move.Paper, Move.Rock)     => true
    case _                           => false

def calculate(game: Game) =
  val additional = game.head match
    case Move.Rock     => 1
    case Move.Paper    => 2
    case Move.Scissors => 3

  val count = game match
    case g if victory(g)  => 6
    case (a, b) if a == b => 3
    case _                => 0

  count + additional

def decodeOutcome(letter: String): Outcome =
  letter match
    case "X" => Outcome.Lose
    case "Y" => Outcome.Drawn
    case "Z" => Outcome.Win

def decodeMove(letter: String): Move =
  letter match
    case "A" => Move.Rock
    case "B" => Move.Paper
    case "C" => Move.Scissors

// Rock defeats Scissors
// Scissors defeats Paper
// Paper defeats Rock
def chooseMove(p: (Move, Outcome)): Move =
  p match
    case (m, Outcome.Drawn) => m
    case (m, Outcome.Win) =>
      m match
        case Move.Rock     => Move.Paper
        case Move.Paper    => Move.Scissors
        case Move.Scissors => Move.Rock
    case (m, Outcome.Lose) =>
      m match
        case Move.Rock     => Move.Scissors
        case Move.Paper    => Move.Rock
        case Move.Scissors => Move.Paper

val part1 = content
  .map { case Array(a, b) =>
    val oponentMove = decodeMove(a)
    val myMove = b match
      case "X" => Move.Rock
      case "Y" => Move.Paper
      case "Z" => Move.Scissors

    (myMove, oponentMove)
  }
  .map(calculate)
  .sum

println(s"Part1: ${part1}")

val part2 = content
  .map { case Array(a, b) =>
    val oponentMove = decodeMove(a)
    val outcome = decodeOutcome(b)

    (oponentMove, outcome)
  }
  .map(s => (chooseMove(s), s._1))
  .map(calculate)
  .sum

println(s"Part2 ${part2}")
