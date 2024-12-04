export {};

const text = await Deno.readTextFile("./3/input");
const input = text
  .split("\n")
  .filter((n) => n != "");

const result: number[] = [];

for (const line of input) {
  let i = 0;
  for (const digit of line) {
    result[i] = (result[i] ?? 0) + parseInt(digit);
    i++;
  }
}

const gammaRate = result.map((n) => n > 500 ? 1 : 0);
const epsilonRate = gammaRate.map((n) => n ? 0 : 1);

console.log("gammaRate", parseInt(gammaRate.join(""), 2));
console.log("epsilonRate", parseInt(epsilonRate.join(""), 2));
console.log(
  "epsilonRate",
  parseInt(gammaRate.join(""), 2) * parseInt(epsilonRate.join(""), 2),
);


function reduce(lines: string[], predicate: (a: number, b: number) => boolean, position = 0): string {
  const zeros: string[] = [];
  const ones: string[] = [];

  if (lines.length === 1) {
    return lines[0];
  }

  for (const line of lines) {
    if (line[position] === "0") {
      zeros.push(line);
    } else {
      ones.push(line);
    }
  }

  if (predicate(zeros.length, ones.length)) {
    return reduce(zeros, predicate, position + 1);
  }
  return reduce(ones, predicate, position + 1);
}

const oxygenRating =  parseInt(reduce(input, (a, b) => a > b), 2);
const co2Rating = parseInt(reduce(input, (a, b) => a <= b), 2)

console.log("oxygenRating", oxygenRating);
console.log("co2Rating", co2Rating);
console.log("result2", oxygenRating * co2Rating);
