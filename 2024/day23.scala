package _2024

import scala.io.Source
import java.nio.file.Paths
import scala.compiletime.ops.double
import scala.annotation.tailrec

def readInput(path: String): List[String] =
  val source = Source.fromFile(Paths.get(".", path).toUri())
  val lines = source.getLines().toList
  source.close()
  lines

class Graph(connections: Iterable[(String, String)]):
  private val adj = collection.mutable.Map
    .empty[String, Set[String]]
    .withDefaultValue(Set.empty[String])

  connections.foreach((v, w) => addEdge(v, w))

  def addEdge(v: String, w: String): Unit =
    adj(v) = adj(v) + w
    adj(w) = adj(w) + v

  def connectionsFrom(v: String): Set[String] = adj(v)

  def isConnected(v: String, w: String) = adj(v).contains(w)

  def vertices = adj.keySet

  // def isClique =
  // false

  override def toString(): String =
    adj
      .map { (key, value) =>
        s"""$key: ${value.mkString(",")}"""
      }
      .mkString("\n")

@main
def day23(file: String) =
  val content = readInput(file)
    .map(l => l.split("-"))
    .map(s => (s.head, s.last))

  val g = Graph(content)

  println(g)

  // println(vertices)

  val part1 = for
    v <- g.vertices if v.startsWith("t")
    adj <- g.connectionsFrom(v) if adj != v
    adj1 <- g.connectionsFrom(adj) if (g.isConnected(v, adj1))
  yield Set(v, adj, adj1)

  println(s"Part1: ${part1.size}")

  val l = collection.mutable.Map
    .empty[String, List[String]]
    .withDefaultValue(List.empty)

  def isClique(vertices: Seq[Set[String]]): Boolean =
    val size = vertices.head.size
    val sameSize = vertices.forall(_.size == size)
    sameSize && vertices.reduce(_ & _).size == size

  def findClique(vertice: String): Set[String] =
    println("A")

    val c = g.connectionsFrom(vertice)
    val a =
      for v <- g.connectionsFrom(vertice)
      // j <- 0 until c.size
      yield
        val abc = c.filter(j => g.isConnected(v, j))
        println(s"abc [$vertice] $v, $abc")
        abc + v

    // println(s"max $a")

    // val max = a.sortBy(_.size).head
    println(s"findClique: $vertice, $a")

    Set.empty
    // val connected = g.isConnected(c(i), c(j))
    // println(s"checking $vertice, ${c(i)}, ${c(j)}, $connected")
    // for adj <- g.connectionsFrom(v)

  val found = findClique("vc")
  println(s"found: $found")

  // @tailrec def go(
  //     routes: List[(String, Set[String])],
  //     clusters: Set[Set[String]]
  // ): Set[Set[String]] = {
  //   val next = clusters.flatMap(cluster =>
  //     routes.collect { case (v, dest) if cluster.subsetOf(dest) => cluster + v }
  //   )
  //   if (next.isEmpty) clusters else go(routes, next)
  // }

  // go(routes.toList.map(p => (p._1, p._2.toSet)), routes.keys.map(Set(_)).toSet)

  def bors_kerbosch_v1(r: Set[String], p: Set[String], x: Set[String], acc: Set[Set[String]]): Set[Set[String]] =
    if p.isEmpty && x.isEmpty then
      acc + r
    else  
      for v <- p do
        bors_kerbosch_v1(r.union(Set(v), P.intersection(G[v]), X.intersection(G[v]), G, C)
      

  // val ssss = for
  //   v <- g.vertices
  // yield findClique(v)

  // println(ssss)

  for
    v <- g.vertices // .filter(_ == "co")
    adj = g.connectionsFrom(v).toVector
    i <- 0 until adj.size
    k <- i + 1 until adj.size if g.isConnected(adj(i), adj(k))
  // connected =  if connected
  // yield connected
  do
    // println(s"checking $v, $i, ${adj(i)} $k, ${adj(k)}")
    // val t =  (adj(i), adj(k))
    l(v) = l(v) :+ adj(i) :+ adj(k)
    // println(s"checking $v, $i, ${adj(i)} $k, ${adj(k)}, $connected")

  // println(l.mkString("\n"))

  for (key, value) <- l
  do
    val grouped = value.groupBy(identity)
    val size = grouped.head.last.size
    val b = grouped.forall((_, v) => v.size > 10 && v.size == size)
    if b then
      println(s"$key, $size, $b, $value")
      println((value :+ key).toSet.toList.sorted.mkString(","))
