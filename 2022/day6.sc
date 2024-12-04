import java.nio.file.Paths
import scala.io.Source
import dzebleckis.aoc22.BoundedSeq

val part = args.toSeq.last.trim

if part != "1" && part != "2" then
  throw Exception(s"Part number not valid: $part")

val distinct = if part == "1" then 4 else 14
var lastSeen = BoundedSeq.empty[Char](distinct)

val content = Source
  .fromFile(Paths.get(".", args.toSeq.head).toUri())
  .zipWithIndex
  .find((c, idx) =>
    lastSeen.add(c)
    idx >= distinct && lastSeen.toSet.size == distinct
  )

content match
  case Some(_, idx) => println(s"${idx + 1}")
  case _            => println("no result")
