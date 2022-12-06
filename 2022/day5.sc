import java.nio.file.Paths
import scala.io.Source
import scala.collection.mutable.{Seq, Map}

val table = Map.empty[Int, Seq[String]]
val instruction = raw"move (\d+) from (\d+) to (\d+)".r

val content =
  Source
    .fromFile(Paths.get(".", args.toSeq.head).toUri())
    .getLines

val part = args.toSeq.last.trim

if part != "1" && part != "2" then
  throw Exception(s"Part number not valid: $part")

println(s"Executing part $part")

content
  .takeWhile(_ != "")
  .foreach(l =>
    l
      .grouped(4)
      .map(_.trim)
      .zipWithIndex
      .foreach((g, idx) =>
        if (g.startsWith("[")) then
          val i = idx + 1
          if table.isDefinedAt(i) then table(i) = table(i) :+ g
          else table.put(i, Seq(g))
      )
  )

content.foreach(line =>
  line match
    case instruction(countS, fromS, toS) =>
      val count = countS.toInt
      val from = fromS.toInt
      val to = toS.toInt

      val (move, remaining) = table(from).splitAt(count)

      table(from) = remaining;
      table(to) =
        if part == "1" then move.reverse ++ table(to)
        else move ++ table(to)
    case _ => throw Exception(s"Did not recognized $line")
)

val answer = table
  .map((_, s) => s.head.substring(1, 2))
  .mkString("")

println(s"Answer: $answer")
