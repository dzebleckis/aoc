import scala.collection.mutable.Stack
import java.nio.file.Paths
import scala.io.Source
import scala.collection.mutable.Map

val cd = raw"^\$$ cd (.+)$$".r
val file = raw"^(\d+) .+".r

class Directory(
    var parent: Option[Directory],
    val name: String,
    var size: Int,
    var children: Seq[Directory]
):
  override def toString(): String =
    s"Directory(name=$name,size=$size,parent=${parent.map(_.name)})"

val stack = Stack.empty[Directory]

def filter(directory: Directory, predicate: (size: Int) => Boolean): Seq[Int] =
  val result = directory.children.flatMap(filter(_, predicate))
  if predicate(directory.size) then result :+ directory.size
  else result

def print(directory: Directory, depth: Int = 0): Unit = 
    println(s"""${"-".repeat(depth)} ${directory.name} (dir)""")
    directory.children.foreach(print(_, depth + 1))

val content = Source
  .fromFile(Paths.get(".", args(0)).toUri())
  .getLines()
  .foreach(line =>
    line match
      case cd(name) if name == ".." =>
        val child = stack.pop()
        val parent = stack.top
        parent.size += child.size

      case cd(name) if name == "/" =>
        stack.push(Directory(None, name, 0, Seq()))

      case cd(name) =>
        val dir = Directory(Some(stack.top), name, 0, Seq())
        stack.top.children = stack.top.children :+ dir
        stack.push(dir)

      case file(size) =>
        stack.top.size += size.toInt

      case _ => 1 // just ignoring
  )

// we need to correctly adjust parent sizes as we are not getting back to root directory
stack.foreach(d => if (d.parent.isDefined) then d.parent.get.size += d.size)

val root = stack.last
val unused = 70000000 - root.size
val diff = 30000000 - unused

println(s"Part1: ${filter(root, _ < 100_000).sum}")
println(s"Part2: ${filter(root, _ >= diff).min}")
