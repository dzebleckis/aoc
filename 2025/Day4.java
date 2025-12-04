void printGrid(char[][] grid) {
    for (char[] chars : grid) {
        for (int j = 0; j < grid[0].length; j++) {
            System.out.print(chars[j]);
        }
        IO.println();
    }
}

int countNeighbours(char[][] grid, int line, int column) {
    int counter = 0;
    for (int i = -1; i < 2; i++) {
        for (int j = -1; j < 2; j++) {
            int x = line + i;
            int y = column + j;
            if ((x == line && y == column)
                    || x < 0 || y < 0
                    || x >= grid.length || y >= grid[0].length) {
                continue;
            }

            if (grid[x][y] == '@') {
                counter++;
            }
        }
    }
    return counter;
}

char[][] makeCopy(char[][] grid) {
    char[][] copy = new char[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++) {
        System.arraycopy(grid[i], 0, copy[i], 0, grid[0].length);
    }
    return copy;
}

int traverse(char[][] grid, BiConsumer<Integer, Integer> consumer) {
    int result = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '@') {
                if (countNeighbours(grid, i, j) < 4) {
                    consumer.accept(i, j);
                    result++;
                }
            }
        }
    }
    return result;
}

void main() {

    var lines = Utils
            .getLines("day4.input");

    int width = lines.getFirst().length();
    int height = lines.size();
    char[][] grid = new char[height][width];

    for (int i = 0; i < lines.size(); i++) {
        grid[i] = lines.get(i).toCharArray();
    }

    char[][] operationalGrid = makeCopy(grid);

    int part1 = traverse(grid, (i, j) -> {});

    IO.println("Part 1: " + part1);

    int part2 = 0;
    int result;

    do {
        result = traverse(grid, (i, j) -> {
            operationalGrid[i][j] = 'x';
        });
        part2 += result;
        grid = makeCopy(operationalGrid);
    } while (result > 0);

    IO.println("Part2: " + part2);
}
