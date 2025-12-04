import utils.readInput


@main
def hello(file: String): Unit =
  val content =
    readInput(file)
      .map(l => l.split(","))
      .map(v => (v.head.toInt, v.last.toInt))
      .take(12)


  val full = for i <- 0 until 6
      j <- 0 until 6
  yield (i, j)

  println(content)
  val diff = full.diff(content)

  println(s"Hello, $diff!")
