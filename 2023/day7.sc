//> using file utils.sc

import utils.readInput

enum Kind:
  case FiveOfKind, FourOfKind, FullHouse, ThreeOfKind, TwoPair, OnePair,
    HighCard

case class Hand(cards: String, kind: Kind)

type Grouping = (cards: String) => Map[Char, String]

object Hand:

  def apply(cards: String, grouping: Grouping) =
    val kind = grouping(cards) match
      case g if g.size == 5 => Kind.HighCard
      case g if g.size == 4 => Kind.OnePair
      case g if g.size == 3 =>
        val maxGgroup = g.maxBy((_, k) => k.size)
        maxGgroup(1).size match
          case 2 => Kind.TwoPair
          case 3 => Kind.ThreeOfKind
      case g if g.size == 2 =>
        val maxGgroup = g.maxBy((_, k) => k.size)
        maxGgroup(1).size match
          case 3 => Kind.FullHouse
          case 4 => Kind.FourOfKind
      case g if g.size == 1 => Kind.FiveOfKind

    new Hand(cards, kind)

class HandOrdering(cardOrdering: Ordering[Char]) extends Ordering[Hand]:

  def compare(x: Hand, y: Hand): Int =

    val kindOrdering = Ordering.by[Kind, Int](_.ordinal).reverse
    val sign = kindOrdering.compare(x.kind, y.kind)

    if (sign == 0) then
      x.cards
        .zip(y.cards)
        .find(cardOrdering.compare(_, _) != 0)
        .fold(0)(cardOrdering.compare(_, _))
    else sign

object HandOrdering:

  def apply(cardOrdering: Ordering[Char]) =
    new HandOrdering(cardOrdering)

def CardOrdering(strength: Map[Char, Int]) =
  Ordering.by[Char, Int](strength(_)).reverse

def parseLine(line: String, grouping: Grouping) =
  val Array(cards, bid) = line.split(" ")
  (Hand(cards.trim, grouping), bid.trim.toInt)

def answer(grouping: Grouping, strength: Map[Char, Int]) =
  content
    .map(l => parseLine(l, grouping))
    .sortBy((hand, _) => hand)(HandOrdering(CardOrdering(strength)))
    .map((_, bid) => bid)
    .zipWithIndex
    .map((bid, rank) => bid * (rank + 1))
    .sum

val content = readInput(args)

val part1Grouping = (cards: String) => cards.groupBy(identity)

val strength1 = Seq('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3',
  '2').zipWithIndex.toMap

println(s"Part 1: ${answer(part1Grouping, strength1)}")

def part2Grouping(card: String): Map[Char, String] =
  val groupedByCard = card.groupBy(identity)
  if groupedByCard.contains('J') && groupedByCard.size > 1 then
    val withoutJoker = groupedByCard.filterNot(_._1 == 'J')
    val max = withoutJoker.maxBy((c, s) => s.size)
    val suplement = max.copy(_2 = max._2 + groupedByCard('J'))
    withoutJoker.filterNot(_._1 == max._1) + suplement
  else groupedByCard

val strength2 = Seq('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2',
  'J').zipWithIndex.toMap

println(s"Part 2: ${answer(part2Grouping, strength2)}")
