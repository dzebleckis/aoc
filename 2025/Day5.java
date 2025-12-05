record Range(Long from, Long to) {
    public Range(Long id) {
        this(id, id);
    }

    public boolean intersects(Range other) {
        return other.from() >= from() && other.from() <= to ||
                other.to() >= from() && other.to() <= to ||
                other.from() <= from() && other.to() >= to;
    }

    public Range merge(Range other) {
        long newFrom = Math.min(from, other.from());
        long newTo = Math.max(to, other.to());

        return new Range(newFrom, newTo);
    }

    public long cardinality() {
        return to - from + 1;
    }
}

static class RangeComparator implements Comparator<Range> {

    @Override
    public int compare(Range o1, Range o2) {
        if (o1.from() < o2.from()) {
            return -1;
        }

        if (o1.to() > o2.to()) {
            return 1;
        }

        return 0;
    }
}

List<Range> merge(List<Range> ranges) {
    Stack<Range> stack = new Stack<>();
    stack.addAll(ranges);

    var list = new ArrayList<Range>();
    list.addFirst(stack.pop());

    boolean merged = false;

    while (!stack.isEmpty()) {
        var head = stack.pop();
        if (list.getFirst().intersects(head)) {
            list.addFirst(list.removeFirst().merge(head));
            merged = true;
        } else {
            list.addFirst(head);
        }
    }

    if (!merged) {
        return list;
    }

    return merge(list);
}


void main() {
    Comparator<Range> rangeComparator = new RangeComparator();
    Set<Range> set = new TreeSet<>(rangeComparator);

    var input = Utils.getLines("day5.input");

    var ranges = input
            .stream()
            .takeWhile((i) -> i.contains("-"))
            .map(s -> s.split("-"))
            .map(a -> new Range(Long.valueOf(a[0]), Long.valueOf(a[1])))
            .sorted(rangeComparator)
            .toList();

    var normalized = merge(ranges);
    set.addAll(normalized);

    var part1 = input.stream()
            .filter(Predicate.not((i) -> i.contains("-")))
            .map(Long::valueOf)
            .filter(id -> set.contains(new Range(id)))
            .count();

    IO.println("Part1: " + part1);

    long part2 = normalized.stream()
            .map(Range::cardinality)
            .reduce(0L, Long::sum);

    IO.println("Part2: " + part2);
}
