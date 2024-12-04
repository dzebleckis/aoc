export {};

const text = await Deno.readTextFile("./8/input");

const input = text
  .split("\n")
  .filter((_) => _ != "");

const input1 = input
  .map((l) => l.split("|")[1]?.trim())
  .flatMap((l) => l.split(" "));

const patterns = [2, 3, 4, 7];
const result1 = input1.filter((p) => {
  const includes = patterns.includes(p.length);
  return includes;
});

console.log("result1", result1.length);

const sortString = (a: string) => a.trim().split("").sort().join("");

function sortLine(a: string): string {
  return a.trim().split(" ").map(sortString).join(" ");
}

function intersection(a: string, b: string): string {
  const bigger = a.length > b.length ? a : b;
  const smaller = a === bigger ? b : a;
  return [...bigger].filter((v) => [...smaller].includes(v)).join("");
}

function diff(a: string, b: string): string {
  const bigger = a.length > b.length ? a : b;
  const smaller = a === bigger ? b : a;
  return [...bigger].filter((v) => ![...smaller].includes(v)).join("");
}

function union(a: string, b: string): string {
  return [...new Set(a + b)].join("");
}

function includes(a: string, b: string): boolean {
  return intersection(a, b).length === b.length;
}

function deduce(digits: string[]) {
  const one = digits.find((_) => _.length == 2)!;
  const four = digits.find((_) => _.length == 4)!;
  const seven = digits.find((_) => _.length == 3)!;
  const eight = digits.find((_) => _.length == 7)!;

  const three = digits.find((_) =>
    _.length == 5 && intersection(_, one).length == 2
  )!;

  const nine = sortString(union(three, four));
  const zero = digits.find((_) =>
    _.length === 6 && _ != nine && includes(_, one)
  )!;
  const six = digits.find((_) => _.length === 6 && _ !== nine && _ !== zero)!;

  const five = digits.find((_) =>
    _.length === 5 && _ !== three && diff(_, six).length === 1
  );

  const two = digits.find((_) => _.length === 5 && _ !== five && _ !== three);

  const result = [zero, one, two, three, four, five, six, seven, eight, nine];

  // console.log("one", {
  //   zero,
  //   one,
  //   two,
  //   three,
  //   four,
  //   five,
  //   six,
  //   seven,
  //   eight,
  //   nine,
  // });
  // console.log("result", result);
  return result;
}

const input2 = input //.slice(2, 3)
  .map((l) => {
    // console.log('l', l)
    const [a, b] = l.split("|");
    return [sortLine(a), sortLine(b)];
  });

let result2 = 0;
input2.forEach(([digits, result]) => {
  // console.log('digits', digits)
  const parsed = deduce(digits.split(" "));
  // console.log("parsed", parsed);
  // console.log("result", result);

  const number = result.split(" ").map((_) => parsed.indexOf(_)).join("");
  result2 += parseInt(number);
  // console.log(result, "\t", number);
});

console.log("result2", result2);
