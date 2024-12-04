const text: string = await Deno.readTextFile("./10/input0");
const lines = text.split("\n");
lines.pop();

const adapters = lines.map((l) => parseInt(l)).sort((a, b) => a - b);

export function part1(adapters: number[]): number {
  let start = 0;
  const result = new Map<number, number>();

  for (const a of adapters) {
    const diff = a - start;
    result.set(diff, (result.get(diff) || 0) + 1);
    start = a;
  }

  return result.get(1)! * result.get(3)!;
}

adapters.push(adapters[adapters.length - 1] + 3);

console.log(part1(adapters));


// console.log(adapters)

const top = adapters[adapters.length - 1];
let [a, b, c] = [0, 0, 1];
const nums = new Set(adapters);

for (let i = 1; i < top + 1; i++) {
    if (nums.has(i)) {
        [a, b, c] = [b, c, a + b + c]
        console.log('has', [a, b, c]);
    } else {
        [a, b, c] = [b, c, 0]
        console.log('does not has',  [a, b, c]);
    }
}

// for (let i = 2; i < adapters.length; i++) {
//     const current = adapters[i];
//     if (current - adapters[i - 2] <= 3) {
//         count += 1;
//         // console.log('1 skipping', adapters[i - 1])
//         // if (i > 2 && current - adapters[i - 3] <= 3) {
//         //     console.log('1.1', current, adapters[i - 3])
//         //     count += 1;
//         //     console.log('2 skipping', adapters[i - 2])
//         // }
//     }
// }
console.log('count', c)
// console.log('Part2', Math.pow(2, 15));
