export {};

const text = await Deno.readTextFile("./5/input");
const input = text.split("\n").filter((n) => n != "");

type Point = {
  x: number;
  y: number;
};

type Line = {
  start: Point;
  end: Point;
};

type Board = number[][];

function isHorizontalOrVertical({ start, end }: Line): boolean {
  return start.x == end.x || start.y == end.y;
}

function parsePoint(line: string): Point {
  const [x, y] = line.trim().split(",");

  return { x: parseInt(x), y: parseInt(y) };
}

function parseLine(line: string): Line {
  const [a, b] = line.split("->");

  return { start: parsePoint(a), end: parsePoint(b) };
}

function createBoard(lines: Line[]): Board {
  const board: Board = [];
  for (const { start, end } of lines) {
    const deltaX = Math.abs(start.x - end.x);
    const deltaY = Math.abs(start.y - end.y);
    const delta = Math.max(deltaX, deltaY);

    for (let i = 0; i <= delta; i++) {
      const x1 = start.x > end.x ? start.x - i : start.x + i;
      const y1 = start.y > end.y ? start.y - i : start.y + i;

      // just make sure we don't exceed max point (x or y),
      // it happens when filling horizontal or vertical line
      const x = Math.min(x1, Math.max(start.x, end.x));
      const y = Math.min(y1, Math.max(start.y, end.y));

      board[x] ??= [];
      board[x][y] = (board[x][y] ?? 0) + 1;
    }
  }
  return board;
}

function countBoard(board: Board): number {
  let total = 0;
  board.forEach((row) => {
    row.forEach((cell) => {
      if (cell > 1) {
        total += 1;
      }
    });
  });
  return total;
}

// createBoard([{ start: { x: 0, y: 9 }, end: { x: 5, y: 9 } }]);
// createBoard([{ start: { x: 0, y: 1 }, end: { x: 0, y: 5 } }]);
// createBoard([{ start: { x: 0, y: 8 }, end: { x: 8, y: 0 } }]);
// createBoard([{ start: { x: 5, y: 5 }, end: { x: 8, y: 2 } }]);
const lines = input.map(parseLine);

const board = createBoard(lines.filter(isHorizontalOrVertical));
const board2 = createBoard(lines);

console.log("total", countBoard(board));
// console.table(board2);
console.log("total diagonal", countBoard(board2));
