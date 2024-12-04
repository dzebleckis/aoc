type Row = {
  range: { min: number; max: number };
  letter: string;
  password: string;
};

function split(line: string): Row {
  const [range, letter, password] = line.split(" ");
  const [min, max] = range.split("-");
  return {
    range: {
      min: Number(min),
      max: Number(max),
    },
    letter: letter[0],
    password,
  };
}

function valid1(row: Row): boolean {
  let count = 0;
  for (const l of row.password) {
    if (l === row.letter) {
      count = count + 1;
    }
  }
  return count >= row.range.min && count <= row.range.max;
}

function valid2(row: Row): boolean {
  const first = row.password[row.range.min - 1] == row.letter;
  const second = row.password[row.range.max - 1] == row.letter;
  return (first || second) && !(first && second);
}

const text = await Deno.readTextFile("./2/input");
const lines = text.split("\n");
lines.pop(); // new line

const rows = lines.map((l: string) => split(l));

let count = 0;
for (const row of rows) {
  if (valid1(row)) {
    count = count + 1;
  }
}

console.log("Policy1", count);

count = 0;

for (const row of rows) {
  if (valid2(row)) {
    count = count + 1;
  }
}

console.log("Policy2", count);
