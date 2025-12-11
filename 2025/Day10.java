record Machine(String diagram, Set<Set<Integer>> buttons) {
}

Integer parseLights(String input) {
    int d = 0;
    input = input.toLowerCase();
    for (int i = 0; i < input.length(); i++) {
//        IO.println(input.charAt(i));
        if (input.charAt(i) == '#') {
            d += Math.powExact(2, input.length() - 1 - i);
//            IO.println("D1 " + i + " " + Integer.toBinaryString(d));
        }
//        d = d << 0;
//        IO.println("D2 " + d + " " + Integer.toBinaryString(d));
    }

    IO.println("D " + d + " " + Integer.toBinaryString(d));
    return d;
}

Machine parse(String line) {
    var a = line.indexOf(']');
    int b = line.indexOf('{');
    String diagram = line.substring(1, a);
    String buttons = line.substring(a + 1, b);

    Pattern p = Pattern.compile("([\\d,]+)");
    Matcher m = p.matcher(buttons);

    Set<Set<Integer>> bb = new HashSet<>();

    while (m.find()) {
        bb.add(
                Arrays.stream(m.group().split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toSet())
        );
    }

    return new Machine(diagram, bb);
}

String click(String current, Set<Set<Integer>> buttons) {

    StringBuilder sb = new StringBuilder(current);
    for (var button : buttons) {
        for (var l : button) {
            var c = ".";
            if (sb.charAt(l) == '.') {
                c = "#";
            }
            sb.replace(l, l + 1, c);
        }
    }
    return sb.toString();
}

int click2(int length, int current, Set<Set<Integer>> buttons) {
    int result = current;
    IO.println("Result: " + Integer.toBinaryString(result));
    for (var button : buttons) {
        for (var l : button) {
            int a = Math.powExact(2, length - l);

            result = result ^ a;

            IO.println(Integer.toBinaryString(a));
            IO.println("l " + l + " a " + a + " r " + Integer.toBinaryString(result));
        }
    }
    return result;
}

boolean found(String input) {
    return input.indexOf('#') == -1;
}

class Node {
    private final Set<Set<Integer>> buttons = new HashSet<>();

    public Node(Set<Integer> button) {
        this.buttons.add(button);
    }

    private Node() {

    }

    public void addAll(Set<Set<Integer>> buttons) {
        this.buttons.addAll(buttons);
    }

    public Set<Set<Integer>> buttons() {
        return this.buttons;
    }

    @Override
    public String toString() {
        return "Node" + this.buttons;
    }
}

int find1(Machine machine) {

    Queue<Node> q = new LinkedList<>();
    for (var b : machine.buttons()) {
        q.add(new Node(b));
    }

    while (!q.isEmpty()) {
        var node = q.poll();

        for (var b : machine.buttons()) {
            if (!node.buttons().contains(b)) {
                var node2 = new Node();
                node2.addAll(node.buttons());
                node2.addAll(Set.of(b));
                q.add(node2);
//                IO.println("B2 " + node2);
            }
        }

        if (found(click(machine.diagram(), node.buttons()))) {
//            IO.println("FOUND " + node.buttons());
            return node.buttons().size();
        }

//        IO.println("B " + node);
    }

    throw new IllegalStateException("Could not find answer");
}

void main() {
    var lines = Utils.getLines("day10.input");


//    var part1 = lines.stream()
//            .map(this::parse)
//            .map(this::find1)
//            .mapToInt(i -> i)
//            .sum();

//    IO.println("Part1: " + part1);
    int parsed = parseLights(".##.");
    IO.println("Parsed " + Integer.toBinaryString(parsed));


    int i = click2(3, parsed, Set.of(Set.of(0, 1), Set.of(0, 2)));
    IO.println("C " + Integer.toBinaryString(i));


//    int d = 6;
//    int mask = (~((~0) << 1)) << 2;
//    IO.println("aa " + Integer.toBinaryString(mask));
//    int num = (~(d & mask) );
//    IO.println("num " + Integer.toBinaryString(d ^ mask));
//
//    d = d | 1;
//    d = d << 1;
//    d = Math.powExact(2, 2);
//
//    d = 0B0110;
//    IO.println("D " + d + " " + Integer.toBinaryString(d));

}
