record Point(long row, long col) implements Comparable<Point> {

    public long area(Point other) {
        return (Math.abs(other.row - row) + 1) * (Math.abs(other.col - col) + 1);
    }

    public Point scale(long factor) {
        return new Point(row / factor, col / factor);
    }


    //1,1 -> 1,2
    //1,1 -> 1,1
    //1,1 -> 2,1
    //2,1 -> 1,3
    @Override
    public int compareTo(Point other) {
        if (row == other.row()) {
            return Long.compare(col, other.col());
        }
        if (col == other.col()) {
            return Long.compare(row, other.row());
        }

        return row > other.row() ? 1 : -1;
    }
}

record Rectangle(Point x, Point y) {

    public boolean intersects(Line line) {

        return false;
    }

    public long area() {
        return x.area(y);
    }
}

record Line(Point x, Point y) {

    public Line(Point x, Point y) {
        if (x.row() == y.row()) {
            if (x.col() > y.col()) {
                this.x = y;
                this.y = x;
            } else {
                this.x = x;
                this.y = y;
            }
        } else {
            if (x.row() > y.row()) {
                this.x = y;
                this.y = x;
            } else {
                this.x = x;
                this.y = y;
            }
        }
    }
}

void print(List<Point> points) {
    var scaled = points.stream().map(p -> p.scale(1)).toList();

    long maxCol = scaled.stream().mapToLong(Point::row).max().getAsLong();
    long maxRow = scaled.stream().mapToLong(Point::col).max().getAsLong();

    for (int i = 0; i <= maxRow; i++) {
        for (int j = 0; j <= maxCol; j++) {
            if (scaled.contains(new Point(j, i))) {
                IO.print("#");
            } else {
                IO.print(".");
            }
        }
        IO.println();
    }

    IO.println(maxCol + " " + maxRow);
}

void main() {
    var points = Utils.getLines("day9.example")
            .stream()
            .map(line -> line.split(","))
            .map(arr -> new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[0])))
            .toList();

    long max = 0;

    IO.println(points);

    List<Rectangle> rectangles = new ArrayList<>();

    for (int i = 1; i < points.size(); i++) {
        for (int j = i + 1; j < points.size(); j++) {
            var rectangle = new Rectangle(points.get(i), points.get(j));
            rectangles.add(rectangle);
            if (rectangle.area() > max) {
                max = rectangle.area();
            }
        }
    }

    List<Point> points2 = new ArrayList<>(points);
    List<Line> lines = new ArrayList<>(points.size());

    points2.sort(Comparator.comparingLong(Point::col));
    for (int i = 0; i < points2.size(); i = i + 2) {
        lines.add(new Line(points2.get(i), points2.get(i + 1)));
    }

    points2.sort(Comparator.comparingLong(Point::row));
    for (int i = 0; i < points2.size(); i = i + 2) {
        lines.add(new Line(points2.get(i), points2.get(i + 1)));
    }

//    lines.forEach(IO::println);

    rectangles.sort(Comparator.comparingLong(Rectangle::area).reversed());

//    IO.println("Area: " + new Point(2, 5).area(new Point(11, 1)));
//    IO.println("Area: " + new Point(11, 1).area(new Point(2, 5)));

//    print(rectangles);
//    rectangles.forEach(r -> IO.println("R " + r.area()));
    IO.println("Part1: " + max);

    long max2 = 0;

    Predicate<Rectangle> intersectsLine = rectangle -> {
        return lines.stream().anyMatch(rectangle::intersects);
    };

    var filtered = rectangles.stream()
            .filter(Predicate.not(intersectsLine)).toList();

    IO.println("Part2 " + filtered);

//    var p1 = new Point(1, 1);
//    var p2 = new Point(2, 1);
//    var p3 = new Point(1, 2);
//    var p4 = new Point(1, 3);
//    IO.println(".....");
//    IO.println(p1.compareTo(p1));
//    IO.println(p1.compareTo(p2));
//    IO.println(p1.compareTo(p3));
//    IO.println(".....");
//    IO.println(p2.compareTo(p1));
//    IO.println(p2.compareTo(p2));
//    IO.println(p2.compareTo(p3));
//    IO.println(".....");
//    IO.println(p3.compareTo(p3));
//    IO.println(p3.compareTo(p2));
//    IO.println(p3.compareTo(p1));
//    IO.println(".....");
//    IO.println(p2.compareTo(p4));
//    IO.println(p4.compareTo(p2));
//    IO.println(".....");
//    for (var rectangle: rectangles) {
//       for(var line: lines) {
//           if (rectangle.intersects(line)) {
//               break;
//           }
//       }
//    }
}
