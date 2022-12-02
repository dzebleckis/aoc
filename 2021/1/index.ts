export {}

const text = await Deno.readTextFile("./1/input");
const lines = text.split("\n").filter((n) => n != "").map((n) => Number(n));

let total = 0;

for (let i = 1; i < lines.length; i++) {
  if (lines[i] > lines[i - 1]) {
    total = total + 1;
  }
}

console.log("total", total);

total = 0;
let prevSum = Number.MAX_VALUE;

for (let i = 3; i < lines.length; i++) {
  const sum = lines[i - 2] + lines[i - 1] + lines[i];
  if (sum > prevSum) {
    total = total + 1;
  }
  prevSum = sum;
}

console.log("total window", total);
