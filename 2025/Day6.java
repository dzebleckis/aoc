
long part1(List<String> input) {
    var grid = input
            .stream()
            .map(l -> l.trim().split("\\s+"))
            .toArray(String[][]::new);

    long total = 0;
    for (int j = 0; j < grid[0].length; j++) {
        BiFunction<Long, Long, Long> op;
        long result = 0;

        if (grid[grid.length - 1][j].equals("*")) {
            op = Math::multiplyExact;
            result = 1;
        } else {
            op = Long::sum;
        }

        for (int i = grid.length - 2; i >= 0; i--) {
            result = op.apply(result, Long.parseLong(grid[i][j]));
        }
        total += result;
    }

    return total;
}

int[] positions(String operations) {
    var array = new ArrayList<Integer>();

    for (int i = 0; i < operations.length(); i++) {
        if (operations.charAt(i) != ' ') {
            array.add(i);
        }
    }

    return array.stream().mapToInt(i -> i).toArray();
}

long part2(List<String> input) {
    var positions = positions(input.getLast());

    int from;
    int to = input.getFirst().length();
    long total = 0;

    for (int p = positions.length - 1; p >= 0; p--) {
        BiFunction<Long, Long, Long> op;
        long result = 0;

        if (input.getLast().charAt(positions[p]) == '*') {
            op = Math::multiplyExact;
            result = 1;
        } else {
            op = Long::sum;
        }

        from = positions[p];

        for (int idx = from; idx < to; idx++) {
            StringBuilder dd = new StringBuilder();

            for (int line = 0; line < input.size() - 1; line++) {
                char maybeDigit = input.get(line).charAt(idx);
                if (maybeDigit != ' ') {
                    dd.append(maybeDigit);
                }
            }
            result = op.apply(result, Long.parseLong(dd.toString()));
        }

        total += result;
        to = from - 1;
    }

    return total;
}

void main() {
    var input = Utils
            .getLines("day6.input");

    IO.println("Part1: " + part1(input));
    IO.println("Part2: " + part2(input));
}
