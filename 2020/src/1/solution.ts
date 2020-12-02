const text = await Deno.readTextFile("./1/input");
const lines = text.split("\n").map((n) => Number(n));
lines.pop();

let expenses = new Set<number>();
for (const l of lines) {
  expenses.add(l);
}

function findPair(sum: number, lines: number[]) {
  for (let i = 0; i < lines.length; i++) {
    const o = sum - lines[i];
    if (expenses.has(o)) {
      //console.log("Pair", lines[i], o, lines[i] + o, lines[i] * o);
      return [lines[i], o];
    }
  }
}

function findTriple(sum: number, lines: number[]) {
  for (let i = 0; i < lines.length; i++) {
    const o = 2020 - lines[i];
    const pair = findPair(o, lines.slice(i + 1, lines.length));
    if (pair) {
      return [lines[i], pair[0], pair[1]];
    }
  }
}

const pair = findPair(2020, lines)!;
const triple = findTriple(2020, lines)!;

console.log("Pair", pair, pair[0]! * pair[1]);
console.log("Triple", triple, triple[0] * triple[1] * triple[2]);
