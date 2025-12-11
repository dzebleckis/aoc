Set<String> parsePaths(String line) {
    return Arrays
            .stream(line.trim().split(" "))
            .map(String::trim)
            .collect(Collectors.toSet());
}

long findPath(Map<String, Set<String>> input, String from, String to) {
    Map<String, Long> cache = new HashMap<>();
    return findPathInner(input, from, to, cache, from);
}

long findPathInner(Map<String, Set<String>> input, String from, String to, Map<String, Long> cache, String path) {

//    IO.println("Visiting " + path);
    if (cache.containsKey(from)) {
//        IO.println("Found in cache " + from + " " + cache.get(from));
        return cache.get(from);
    }

    if (from.equals(to)) {
        return 1;
    }

    long counter = 0;

    for (var next : input.getOrDefault(from, Set.of())) {
        counter += findPathInner(input, next, to, cache, path + "->" + next);
    }

//    IO.println("Set cache for " + from + " " + counter);
    cache.put(from, counter);

    return counter;
}

void main() {
    var devices = Utils.getLines("day11.input")
            .stream()
            .map(l -> l.split(":"))
            .collect(Collectors.toMap(l -> l[0].trim(), l -> parsePaths(l[1])));

//    devices.entrySet().forEach(IO::println);
    IO.println("Part1: " + findPath(devices, "you", "out"));

    long srvToDac = findPath(devices, "svr", "dac");
    long dacToFft = findPath(devices, "dac", "fft");
    long fftToOut = findPath(devices, "fft", "out");

    long srvToFft = findPath(devices, "svr", "fft");
    long fftToDac = findPath(devices, "fft", "dac");
    long dacToOut = findPath(devices, "dac", "out");

    long part2 = (srvToDac * dacToFft * fftToOut) + (srvToFft * fftToDac * dacToOut);
    IO.println("Part2: " + part2);
}
