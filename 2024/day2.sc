//> using file utils.sc

import utils.readInput

val content = readInput(args)

def allIncreasing(seq: Seq[Int]): Boolean =
  seq.sliding(2).forall { case Seq(a, b) => a < b }

def allDecreasig(seq: Seq[Int]): Boolean =
  seq.sliding(2).forall { case Seq(a, b) => a > b }
// false

def safeDistance(seq: Seq[Int]): Boolean =
  !seq.sliding(2).exists { case Seq(a, b) => Math.abs(a - b) > 3 }

def parseLine(line: String): Seq[Int] = line.split(" ").map(_.toInt)

val safe = for
  seq <- content.map(parseLine)
  if (allDecreasig(seq) && safeDistance(seq)) || (allIncreasing(seq) && safeDistance(seq))
yield seq

println(s"part1: ${safe.length}")


def allDecreasig2(seq: Seq[Int]): Boolean =
    println(seq)
    val a = for i <- 0 until seq.length - 1
        if seq(i) - seq(i + 1) < 3
    do
        println(i)
    false

def allIncreasing2(seq: Seq[Int]): Boolean = 
    false


def somethingSomething(): Boolean
    
    false    

val safe2 = for
  seq <- content.map(parseLine)
  if allDecreasig2(seq) || allIncreasing2(seq)
yield seq


println(s"part2: ${safe2.length}")

// def safe2(line: String): Int =
//     val seq = parseLine(line)
//     val a = for i <- 0 to seq.length
//         if seq(i) - seq(i + 1) < 3
//     yield 1

    // 0
//   val a = seq(i)
//   val b = seq(j)
//   if j == seq.length then true
//   else if cmp(a, b) && Math.abs(a - b) <= 3 then safe2(i + 1, j + 1, seq, cmp)
//   else false

// val a = List(7, 6, 4, 2, 1)
// val a = List(1, 2, 7, 8, 9)
// println(a)
// println(allIncreasing(a))
// println(allDecreasig(a))
// println(safeDistance(a))
