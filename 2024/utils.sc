import java.nio.file.Paths
import scala.io.Source

def readInput(args: Array[String]): List[String] = readInput(args.toSeq.head)

def readInput(path: String): List[String] =
  val source = Source.fromFile(Paths.get(".", path).toUri())
  val lines = source.getLines().toList
  source.close()
  lines
