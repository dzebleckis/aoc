record Rotation(String direction, Integer distance) {
}

Rotation parseRotation(String line) {
    String direction = line.substring(0, 1);
    Integer distance = Integer.valueOf(line.substring(1));

    return new Rotation(direction, distance);
}

void main() {

    var lines = Utils.getLines("day1.input")
            .stream()
            .map(this::parseRotation)
            .toList();

    int currentPosition = 50;
    int part1 = 0;
    int part2 = 0;

    for (var line : lines) {
        for (int i = 1; i <= line.distance(); i++) {
            if (line.direction().equals("L")) {
                if (currentPosition == 0) {
                    currentPosition = 99;
                } else {
                    currentPosition--;
                }
            } else {
                if (currentPosition == 99) {
                    currentPosition = 0;
                } else {
                    currentPosition++;
                }
            }

            if (currentPosition == 0) {
                part2++;
            }
        }
        if (currentPosition == 0) {
            part1++;
        }
    }

    IO.println("Part1: " + part1);
    IO.println("Part2: " + part2);
}

