//> using file utils.sc

import utils.readInput

case class Data(destination: Long, source: Long, lenght: Long):
  def contains(n: Long) = source <= n && n < source + lenght

  def next(n: Long) =
    assert(contains(n), s"$this does not contains $n")
    destination + n - source

  override def toString(): String = s"$destination $source $lenght"

class Region(data: Seq[Data]):

  def next(n: Long): Long =
    data.find(_.contains(n)).fold(n)(_.next(n))

  override def toString(): String = data.mkString("\n")

object Region:

  def apply(lines: Seq[String]): Region =
    val r = """(\d+) (\d+) (\d+)""".r

    val data = lines.map(l =>
      l match
        case r(destination, source, range) =>
          Data(destination.toLong, source.toLong, range.toLong)
    )

    new Region(data)

// val data = Data(50, 98, 2)
// val data = Data(52, 50, 48)

// val region = Region(List("50 98 2", "52 50 48"))

// println(region)
// println(data.next(98))
// println(data.contains(99))

val content = readInput(args)

// println(content)

val seeds = """(\d+)""".r.findAllMatchIn(content.head).map(_.group(1).toLong)
val rest = content.tail.drop(2)

def parse(lines: Seq[String], iter: Seq[Region]): Seq[Region] =
  if lines.isEmpty then iter
  else
    val idx = lines.indexWhere(_.isEmpty)
    val (a, b) = lines.splitAt(idx)

    if a.nonEmpty then parse(b.drop(2), iter :+ Region(a))
    else iter :+ Region(b) // last region

println(s"seeds  ${seeds}")
val regions = parse(rest, Seq.empty[Region])

// println(regions.mkString("\n\n"))
// println(regions.size)

// val i = regions.foldLeft(79L)((n, region) => region.next(n))
val part1 = seeds
  .map(s => regions.foldLeft(s)((n, r) => r.next(n)))
  .min

println(s"Part 1: $part1")

// val seedPairs = """(\d+) (\d+)""".r
//   .findAllMatchIn(content.head)
//   .map(m => (m.group(1).toLong, m.group(2).toLong))
//   .toList

// val part2 = seedPairs
//     .flatMap((f, l) => f until f + l)
//     .map(s => regions.foldLeft(s)((n, r) => r.next(n)))
//     .min

// println(s"Part 2: $part2")  
