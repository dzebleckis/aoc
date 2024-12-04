import java.nio.file.Paths
import scala.io.Source

def readInput(args: Array[String]) = Source
  .fromFile(Paths.get(".", args.toSeq.head).toUri())
  .getLines()
  .toList