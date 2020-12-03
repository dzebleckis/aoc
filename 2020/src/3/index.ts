const text = await Deno.readTextFile("./3/input");
const lines = text.split("\n"); //.slice(0, 25);
lines.pop();

let landscape: string[][];

// function copy(line: string, times: number): string {
//   let result = "";
//   for (let i = 0; i < times; i++) {
//     result = result + line;
//   }
//   return result;
// }

const maxLength = lines[0].length;

function numberOfTrees(
  lines: string[],
  stepsDown: number,
  stepsRight: number,
): number {
  const times = lines.length / lines[0].length * stepsRight;
  let counter = 0;
  let right = 0;
  //   let right2 = 0;
  for (let down = stepsDown; down < lines.length; down = down + stepsDown) {
    right = right + stepsRight;
    // right2 = right2 + stepsRight;
    if (right >= maxLength) {
      right = right % maxLength;
    }

    // let symbol = "O";
    if (lines[down][right] === "#") {
      counter = counter + 1;
      //   symbol = "X";
    }
    // let line2 = copy(lines[down], times);

    // const original = line2[right2];
    // if (
    //   original === "." && symbol != "O" || original === "#" && symbol != "X"
    // ) {
    //   console.log("Error on line", original, symbol, right2, right, down);
    //   console.log(lines[down]);
    //   console.log(line2);
    //   console.log(
    //     line2.slice(0, right2) + symbol + line2.slice(right2 + 1, -1),
    //   );
    // }
  }
  return counter;
}

const result1 = numberOfTrees(lines, 1, 1);
console.log("Count1:", result1);
const result2 = numberOfTrees(lines, 1, 3);
console.log("Count2:", result2);
const result3 = numberOfTrees(lines, 1, 5);
console.log("Count3:", result3);
const result4 = numberOfTrees(lines, 1, 7);
console.log("Count4:", result4);
const result5 = numberOfTrees(lines, 2, 1);
console.log("Count5:", result5);

console.log("Final", result1 * result2 * result3 * result4 * result5);
