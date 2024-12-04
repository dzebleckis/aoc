import scala.collection.mutable.Stack
import java.nio.file.Paths
import scala.io.Source
import scala.math.Ordered.given
import Term.*

enum Term:
  case Digit(d: Int)
  case Array(s: Seq[Term])

given TermOrdering: Ordering[Term] with

  def nestedCompare(ls: Seq[Term], rs: Seq[Term]): Int = (ls, rs) match
    case (l :: ls1, r :: rs1) =>
      val res = compare(l, r)
      if res == 0 then nestedCompare(ls1, rs1) // equal, look at next element
      else res // less or greater

    case (_ :: _, Nil) => 1 // right ran out of elements first
    case (Nil, _ :: _) => -1 // left ran out of elements first
    case (Nil, Nil)    => 0 // equal size
  end nestedCompare

  def compare(left: Term, right: Term): Int = (left, right) match
    case (Digit(l), Digit(r))       => l compare r
    case (Array(l), Array(r))       => nestedCompare(l, r)
    case (num @ Digit(_), Array(r)) => nestedCompare(num :: Nil, r)
    case (Array(l), num @ Digit(_)) => nestedCompare(l, num :: Nil)
  end compare

end TermOrdering

def tokenize(input: String): Stack[String] =
  val seq = input
    .replace("[", " [ ")
    .replace("]", " ] ")
    .replace(",", " ")
    .split(" ")
    .filter(!_.isEmpty())

  Stack.from(seq)

def parse(input: Stack[String]): Term =
  val token = input.pop()
  if token == "[" then
    // println("begining")
    var acc = Seq.empty[Term]
    while input.head != "]" do acc = acc :+ parse(input)
    input.pop() // pop off ']'
    Term.Array(acc)
  else Term.Digit(token.toInt)

// val left = parse(tokenize("[[3,7],[5,2],[],[6,[[4,3,7]]]]"))
// val right = parse(tokenize("[[[[3,7,2,5],8],[[],[5,9,3,9]]]]"))

// println(left <= right)

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines()
  .filter(!_.isBlank())
  .map(l => parse(tokenize(l)))
  .toSeq

val sum = content  
  .grouped(2)
  .map(g => g.head < g.last)
  .zipWithIndex
  .collect { case (true, idx) => idx + 1 }
  .sum

println(s"Part1 $sum")

val firstPacket = parse(tokenize("[[2]]"))
val secondPacket = parse(tokenize("[[6]]"))

val content2 =
  (content :+ firstPacket :+ secondPacket).sorted

val idx1 = content2.indexOf(firstPacket) + 1
val idx2 = content2.indexOf(secondPacket) + 1

println(s"Part2: ${idx1 * idx2}")
