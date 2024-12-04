export {};

type Instruction = {
  command: "up" | "down" | "forward";
  value: number;
};

const text = await Deno.readTextFile("./2/input");
const instructions = text
  .split("\n")
  .filter((n) => n != "")
  .map<Instruction>((line) => {
    const [command, value] = line.split(" ");
    return {
      command: command as "up" | "down" | "forward",
      value: parseInt(value),
    };
  });

let depth = 0;
let horizontalPosition = 0;

for (const instruction of instructions) {
  switch (instruction.command) {
    case "up":
      depth = depth - instruction.value;
      break;
    case "down":
      depth = depth + instruction.value;
      break;
    case "forward":
      horizontalPosition = horizontalPosition + instruction.value;
      break;
    default:
      console.error("unknown instruction", instruction);
  }
}

console.log(
  "answer 1",
  { depth, horizontalPosition },
  depth * horizontalPosition,
);

let aim = 0;
depth = 0;
horizontalPosition = 0;

for (const instruction of instructions) {
  switch (instruction.command) {
    case "down":
      aim = aim + instruction.value;
      break;
    case "up":
      aim = aim - instruction.value;
      break;
    case "forward":
      horizontalPosition = horizontalPosition + instruction.value;
      depth = depth + aim * instruction.value;
      break;
    default:
      console.error("unknown instruction", instruction);
  }
}

console.log(
  "answer 2",
  { depth, horizontalPosition },
  depth * horizontalPosition,
);
