import { parseGroups } from "../parser.ts";

export function countAnyoneYes(input: string): number {
  const set = new Set();
  for (const c of input) {
    set.add(c);
  }
  return set.size;
}

export function countEveryoneYes(
  input: string,
  separator: string = " ",
): number {
  let count = 1;
  const answers = new Map<string, number>();
  for (const c of input) {
    if (c === separator) {
      count += 1;
    } else {
      answers.set(c, (answers.get(c) || 0) + 1);
    }
  }

  return Array.from(answers.values()).filter((c) => c === count).length;
}

const text: string = await Deno.readTextFile("./6/input");
const lines = text.split("\n");

const groups1 = parseGroups(lines);
const result = groups1.map(countAnyoneYes).reduceRight((acc, s) => acc + s, 0);
console.log("Anyone answer count", result);

const groups2 = parseGroups(lines, " ");
const result2 = groups2.map((g) => countEveryoneYes(g)).reduceRight(
  (acc, s) => acc + s,
  0,
);
console.log("Everyone answer count", result2);
