export {};

const text = await Deno.readTextFile("./7/input");

const input = text
  .split("\n")[0]
  .split(",")
  .map((_) => parseInt(_));

const sum = input.reduce((a, b) => a + b, 0);
console.log("sum", sum);

const sorted = input.sort((a, b) => a - b);

const median = sorted[sorted.length / 2];

console.log("median", median);

let fuel1 = 0;
for (let i = 0; i < sorted.length; i++) {
  fuel1 = fuel1 + Math.abs(median - sorted[i]);
}

console.log("part1", fuel1);

const mean = Math.round(sum / sorted.length);
console.log("mean", mean);

const fuel = [];
for (let i = mean - 1, idx = 0; i <= mean + 1; i++) {
  fuel[idx] = 0;
  console.log("i", i);
  for (let j = 0; j < input.length; j++) {
    const positionDiff = Math.abs(input[j] - i);
    fuel[idx] += positionDiff * ((positionDiff + 1) / 2);
  }
  idx += 1;
}

console.log("fuel", fuel);
console.log("part2", Math.min(...fuel));
