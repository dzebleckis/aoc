//> using file utils.sc

import utils.readInput

val equations = readInput(args)
  .map(l => l.split(": ").toList)
  .map { line => (line.head.toLong, line(1).split(" ").map(_.toLong)) }

// println()  

def recurse(answer: Long, accumulator: Long, rest: Seq[Long]): Boolean =
    println(s"$answer, $accumulator, $rest")
    if answer == 0 then rest.isEmpty
    else if answer.toString().endsWith(accumulator.toString()) then
        
        val as  = answer.toString()
        // println(as)
        val a = as.take(as.length - accumulator.toString().length)
        if a.isBlank() then true
        else
            // println(s"a $a")
            recurse(a.toLong, rest.head, rest.tail)
    else if answer % accumulator == 0 then recurse(answer / accumulator, rest.head, rest.tail)            
    else recurse(answer - accumulator, rest.head, rest.tail)
    // false


val answer = 7290
val rest = Seq[Long](6, 8, 6, 15).reverse    

println(recurse(answer, rest.head, rest.tail))    