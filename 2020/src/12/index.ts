const text: string = await Deno.readTextFile("./12/input0");
const lines = text.split("\n");
lines.pop();

const istructions: Instruction[] = lines.map((l) =>
  ({
    direction: l[0],
    value: parseInt(l.slice(1)),
  }) as Instruction
);

// console.log(i)

type Instruction = {
  direction: "N" | "S" | "E" | "W" | "L" | "R" | "F";
  value: number;
};

const position = {
  northSouth: 0,
  eastWest: 0,
  direction: "E",
};

type Cell = {
  name: string;
  next: Cell;
  prev: Cell;
};

const n = {
  name: "N",
  next: {},
  prev: {},
} as Cell;

const s = {
  name: "S",
  next: {},
  prev: {},
} as Cell;

const e = {
  name: "E",
  next: {},
  prev: {},
} as Cell;

const w = {
  name: "W",
  next: {},
  prev: {},
} as Cell;

n.next = e;
n.prev = w;
s.next = w;
s.prev = e;
e.prev = n;
e.next = s;
w.prev = s;
e.next = n;

const c = [n, e, s, w];

for (const instruction of istructions) {
  console.log(position);

  if (instruction.direction in ["N", "E", "S", "W"]) {
    if (position.direction !== instruction.direction) {
    }
  } else if (instruction.direction === "F") {
    if (position.direction in ["N", "S"]) {
      position.northSouth += instruction.value;
    } else {
      position.eastWest += instruction.value;
    }
  } else {
    if (instruction.direction === "R") {
      let current = c.find((e) => e.name === position.direction)!;
      for (let i = 0; i < instruction.value / 90; i++) {
        current = current.next;
      }
      console.log("currebt", current);
      position.direction = current.name;
    } else {
      let current = c.find((e) => e.name === position.direction)!;
      for (let i = 0; i < instruction.value / 90; i++) {
        current = current.next;
      }
      console.log("currebt", current);
      position.direction = current.name;
    }
  }
}
