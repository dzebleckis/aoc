
void findMax(int[] numbers, int pos, int index, int[] accumulator, int batteriesCount) {
    if (index == batteriesCount) {
        return;
    }

    var found = -1;
    var offset = batteriesCount - 1 - index;
    for (int i = pos; i < numbers.length - offset; i++) {
        var num = numbers[i];
        if (num > accumulator[index]) {
            accumulator[index] = num;
            found = i;
        }
    }

    if (found == -1) {
        throw new IllegalStateException("Not found");
    }

    findMax(numbers, found + 1, index + 1, accumulator, batteriesCount);
}

int[] findMax(int[] numbers, int batteriesCount) {
    int[] accumulator = new int[batteriesCount];
    Arrays.fill(accumulator, 0);
    findMax(numbers, 0, 0, accumulator, batteriesCount);
    return accumulator;
}

int[] parseNumber(String number) {
    return number.codePoints().map(i -> i - 48).toArray();
}

long makeNumber(int[] numbers) {
    long result = 0;
    for (var num : numbers) {
        result = result * 10 + num;
    }

    return result;
}


void main() {
    var lines = Utils.getLines("day3.input")
            .stream()
            .map(this::parseNumber)
            .toList();

    var part1 = lines.stream()
            .map(numbers -> findMax(numbers, 2))
            .map(this::makeNumber)
            .reduce(Long::sum);

    IO.println("Part 1: " + part1);

    var part2 = lines.stream()
            .map(numbers -> findMax(numbers, 12))
            .map(this::makeNumber)
            .reduce(Long::sum);

    IO.println("Part 2: " + part2);
}
