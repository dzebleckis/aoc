import scala.collection.immutable.SortedSet
import java.nio.file.Paths
import java.nio.file.Files
import scala.collection.mutable.ListBuffer

val content = Files.lines(Paths.get(".", args.toSeq.head))

var maxCalories = SortedSet.empty[Int]

var counter = 0

content.forEach(line =>
  if line == "" then
    maxCalories = (maxCalories + counter).takeRight(3)
    counter = 0
  else counter += line.toInt
)

maxCalories = (maxCalories + counter).takeRight(3)

println(s"max: \t${maxCalories.last}")
println(s"top 3: \t${maxCalories.sum}")
