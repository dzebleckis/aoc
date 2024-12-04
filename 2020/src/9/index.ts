const text: string = await Deno.readTextFile("./9/input");
const lines = text.split("\n");
lines.pop();

export function part1(numbers: number[], start: number): number | undefined {
  for (let i = start; i < numbers.length; i++) {
    const preamble = new Set(numbers.slice(i - start, i));
    let hasPair = false;
    for (const p of preamble) {
      if (preamble.has(numbers[i] - p)) {
        hasPair = true;
        break;
      }
    }
    if (!hasPair) {
      return numbers[i];
      break;
    }
  }
}

const numbers = lines.map((l) => parseInt(l));

const start = 25;

console.log("Part1:", part1(numbers, start));

export function part2(number: number[], sum: number): number[] | undefined {
  for (let i = 0; i < numbers.length; i++) {
    let acc = numbers[i];
    for (let j = i + 1; j < numbers.length; j++) {
      acc += numbers[j];
      if (acc === sum) {
        return numbers.slice(i, j);
      }
      if (acc > sum) {
        break;
      }
    }
  }
}

const p2 = part2(numbers, 57195069)?.sort();
if (p2) {
  console.log("Part2: ", p2[0] + p2[p2.length - 1]);
}
