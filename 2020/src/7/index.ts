export function extractBags(line: string): string[] {
  const matches = line.match(/([a-z]+ [a-z]+) bag/g)!;
  return matches.map((m) => m.slice(0, -4).trim());
}

// contert [parent, ..children] to [childen, parent] pairs
function normalizeBags(bags: string[]): [string, string][] {
  let result: [string, string][] = [];
  for (let i = 1; i < bags.length; i++) {
    result.push([bags[i], bags[0]]);
  }
  return result;
}

const text: string = await Deno.readTextFile("./7/input");
const lines = text.split("\n");
lines.pop();

const rules = lines.map(extractBags).flatMap(normalizeBags);

function findParents(data: string[][], bag: string): string[] {
  const result: string[] = [];
  for (const [child, parent] of data) {
    if (child === bag) {
      result.push(parent);
      result.push(...findParents(data, parent));
    }
  }
  return result;
}

const some = findParents(rules, "shiny gold");
console.log("Part 1", new Set(some).size);

const m = new Map<string, number>();
let i = 0;
