String printBeams(Map<Integer, Integer> beams) {
    StringBuilder sb = new StringBuilder();


    int prev = 0;
    for (var entry : beams.entrySet()) {
        sb.append(".".repeat(entry.getKey() - prev));
        sb.append(entry.getValue());
        prev = entry.getKey() + 1;
    }

    return sb.toString();
}

void main() {
    var input = Utils.getLines("day7.input");

    int part1 = 0;
    var header = input.getFirst();
    var cleaned = input
            .stream()
            .filter(l -> l.contains("^"))
            .toList();
    int position = header.indexOf('S');

    Map<Integer, Long> beams = new HashMap<>();
    beams.put(position, 1L);

    for (var line : cleaned) {
        var beamKeys = beams.keySet().stream().toList();
        for (var beam : beamKeys) {

            if (line.charAt(beam) == '^') {
                part1++;
                int left = beam - 1;
                int right = beam + 1;

                long addLeft = beams.getOrDefault(left, 0L);
                long addRight = beams.getOrDefault(right, 0L);

                beams.put(left, beams.get(beam) + addLeft);
                beams.put(right, beams.get(beam) + addRight);
                beams.remove(beam);
            }
        }
    }

    IO.println("Part1: " + part1);
    IO.println("Part2: " + beams.values().stream().reduce(0L, Long::sum));
}
