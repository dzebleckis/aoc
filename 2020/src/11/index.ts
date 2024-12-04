const Occupied = "#";
const Empty = "L";

export function isEmpty(x: number, y: number, lines: string[][]): boolean {
  const width = lines[0].length;
//   console.log("isEmpty", x, y, width, lines.length);
  if (x < 0 || y < 0 || x >= width || y >= lines.length) {
    return true;
  }
  // console.log(x, y, lines[x][y])
  return lines[x] !== undefined && lines[x][y] !== Occupied;
}

export function canOccupie(x: number, y: number, lines: string[][]): boolean {
  let empty = 0;
//   console.log(lines[x][y], x, y, lines[x]);
  if (lines[x][y] !== Empty) {
    return false;
  }
  for (let i = -1; i < 2; i++) {
    for (let j = -1; j < 2; j++) {
      if (i === 0 && j === 0) {
        continue;
      }
      if (isEmpty(x - i, y - j, lines)) {
        empty += 1;
      }
    }
  }
//   console.log('empty', empty)
  return empty === 8;
}

export function shouldMakeEmpty(
  x: number,
  y: number,
  lines: string[][],
): boolean {
  let occupied = 0;
  if (lines[x][y] !== Occupied) {
    return false;
  }

  for (let i = -1; i < 2; i++) {
    for (let j = -1; j < 2; j++) {
      if (i === 0 && j === 0) {
        continue;
      }
      if (!isEmpty(x - i, y - j, lines)) {
        occupied += 1;
      }
    }
  }
  return occupied >= 4;
}

export function createArray(y: number): string[][] {
  let arr: string[][] = [];
  for (let j = 0; j < y; j++) {
    arr[j] = [];
  }
  return arr;
}

export function run(lines: string[][]): string[][] {
  const clone: string[][] = createArray(lines.length);
  const widht = lines[0].length;
  for (let y = 0; y < lines.length; y++) {
    for (let x = 0; x < widht; x++) {
      if (canOccupie(x, y, lines)) {
        clone[x][y] = Occupied;
      } else if (shouldMakeEmpty(x, y, lines)) {
        clone[x][y] = Empty;
      } else {
        clone[x][y] = lines[x][y];
      }
    }
  }
  return clone;
}

export function countOccupied(seats: string[][]): number  {
    return seats.reduce(
        (a, b) => a + b.filter(e => e === Occupied).length,
        0
    );
}

function print(lines: string[][]) {
    for (let i = 0; i < lines.length; i++) {
        console.log(lines[i].toString().replaceAll(",", ""));
    }
    console.log();
}

const text: string = await Deno.readTextFile("./11/input");
const lines: string[][] = text.split("\n").map((c) => Array.from(c));
lines.pop();

let some = lines;
let prev = 0;
for (let i = 0; i < 84; i++) {
    some = run(some);
    const occupied = countOccupied(some);
    console.log('occupied', occupied)
    if(prev === occupied) {
        console.log('s', occupied);
        // console.log(i)
        break;
    } else {
        prev = occupied;
    }
}
print(some)
// console.log(some1.map(a => a.toString()));
