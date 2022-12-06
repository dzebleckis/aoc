package dzebleckis.aoc22

import scala.reflect.ClassTag

class BoundedSeq[T: ClassTag](size: Int):
  private var idx = 0
  private val underlying = Array.ofDim[T](size)

  def add(value: T): Unit =
    underlying(idx % size) = value
    idx += 1

  def toSet: Set[T] = Set.from(underlying)
  def toSeq: Seq[T] = Seq.from(underlying)

object BoundedSeq:
  def empty[T: ClassTag](size: Int) = BoundedSeq[T](size)

@main
def main() =
  val bc = BoundedSeq.empty[Char](3)
  bc.add('A')
  bc.add('B')
  bc.add('C')
  println(bc.toSeq.mkString(" "))
  bc.add('D')
  println(bc.toSeq.mkString(" "))
  bc.add('E')
  println(bc.toSeq.mkString(" "))
  bc.add('F')
  println(bc.toSeq.mkString(" "))
  bc.add('G')
  println(bc.toSeq.mkString(" "))
