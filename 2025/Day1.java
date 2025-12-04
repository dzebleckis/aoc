record Rotation(String direction, Integer distance) {
}

Rotation parseRotation(String line) {
    String direction = line.substring(0, 1);
    Integer distance = Integer.valueOf(line.substring(1));

    return new Rotation(direction, distance);
}

void main() {

    var lines = Utils.getLines("day1.example")
            .stream()
            .map(this::parseRotation)
            .toList();

    int currentPosition = 50;
    int part1 = 0;

    for (var line : lines) {
        currentPosition += line.direction().equals("L") ? -line.distance : line.distance();

        if (currentPosition % 100 == 0) {
            part1++;
        }
    }

    IO.println("Part1: " + part1);

    currentPosition = 50;
    int part2 = 0;

    IO.println("Current position: " + currentPosition);
    for (var line : lines) {
        currentPosition += line.direction().equals("L") ? -line.distance : line.distance();

        IO.println("Current position: " + currentPosition);
//        if (currentPosition % 100 == 0) {
//            part2++;
//        }
    }

    IO.println("Part1: " + part2);
}

