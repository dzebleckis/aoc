export {};

const text = await Deno.readTextFile("./6/input");

const input = text.split("\n")[0];
const population = input?.split(",").map((_) => parseInt(_)) ?? [];

console.log("input", population);

const table: number[] = [];
let populationCount = population.length;

for (let i = 0; i < 9; i++) {
  table[i] = population.filter((p) => p === i).length;
}

console.log(`day 0: `, table.join(","));
for (let i = 0; i < 256; i++) {
  const newItems = table[0];
  table[0] = table[1];
  table[1] = table[2];
  table[2] = table[3];
  table[3] = table[4];
  table[4] = table[5];
  table[5] = table[6];
  table[6] = newItems + table[7];
  table[7] = table[8];
  table[8] = newItems;
  populationCount += newItems;
}

console.log("populationCount", populationCount);
