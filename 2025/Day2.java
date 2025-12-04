record Range(long from, long to) {
}

Range toRange(String line) {
    var l = line.split("-");
    return new Range(Long.parseLong(l[0]), Long.parseLong(l[1]));
}

Predicate<Long> isDuplicate(String regex) {
    Pattern pattern = Pattern.compile(regex);
    return (number) -> {
        Matcher m = pattern.matcher(number.toString());
        return m.matches();
    };
}

Stream<Long> supplyNumbers(Range range) {
    return Stream.iterate(range.from(), (current) -> current <= range.to(), (current) -> current + 1);
}

void main() {
    var lines = Arrays.stream(Utils.getLines("day2.example")
                    .getFirst()
                    .split(","))
            .map(this::toRange)
            .flatMap(this::supplyNumbers)
            .toList();

    var part1 = lines.stream()
            .filter(isDuplicate("^(.+)\\1$"))
            .reduce(Long::sum);

    var part2 = lines.stream()
            .filter(isDuplicate("^(.+)\\1+$"))
            .reduce(Long::sum);

    IO.println("Part1: " + part1);
    IO.println("Part2: " + part2);
}
