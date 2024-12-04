type InstructionName = "nop" | "acc" | "jmp";
type Instruction = {
  name: InstructionName;
  value: number;
};

export function parse(line: string): Instruction {
  const [name, rest] = line.split(" ");
  const [sign, rest1] = rest;
  const value = parseInt(rest.substr(1));
  return {
    name,
    sign: rest[0],
    value: sign === "+" ? value : -value,
  } as Instruction;
}

function interpreter(
  instruction: Instruction,
): { result: number; offset: number } {
  switch (instruction.name) {
    case "nop":
      return { result: 0, offset: 1 };
    case "jmp":
      return { result: 0, offset: instruction.value };
    case "acc":
      return { result: instruction.value, offset: 1 };
  }
}

function execute(
  instrunction: Instruction[],
): { acc: number; terminated: boolean } {
  const memoization = new Set();
  let acc = 0;
  let next = 0;

  while (!memoization.has(next)) {
    if (next >= instructions.length) {
      return { acc, terminated: true };
    }
    memoization.add(next);
    const { result, offset } = interpreter(instructions[next]);
    acc += result;
    next = next + offset;
  }
  return { acc, terminated: false };
}

function swapInstructions(
  i: number,
  instructions: Instruction[],
  from: InstructionName,
  to: InstructionName,
): number | undefined {
  instructions[i].name = to;
  const { acc, terminated } = execute(instructions);
  if (terminated) {
    return acc;
  } else {
    instructions[i].name = from;
  }
}

const text: string = await Deno.readTextFile("./8/input");
const lines = text.split("\n");
lines.pop();

const instructions = lines.map(parse);

const { acc } = execute(instructions);

console.log("first acc", acc);

for (let i = 0; i < instructions.length; i++) {
  if (instructions[i].name === "jmp") {
    const acc = swapInstructions(i, instructions, "jmp", "nop");
    if (acc) {
      console.log("terminated with", acc);
      break;
    }
  } else if (instructions[i].name === "nop") {
    const acc = swapInstructions(i, instructions, "nop", "jmp");
    if (acc) {
      console.log("terminated with", acc);
      break;
    }
  }
}
