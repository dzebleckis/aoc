function decode0(
  input: string,
  letter: string,
  max: number,
): number {
  let row = [0, max];
  for (let i = 0; i < input.length; i++) {
    const half = (row[1] - row[0]) / 2;
    if (input[i] == letter) {
      row[1] = row[0] + Math.floor(half);
    } else {
      row[0] = row[0] + Math.ceil(half);
    }
  }
  return row[0];
}

export function decode(
  input: string,
): { row: number; column: number; seatId: number } {
  const row = decode0(input.slice(0, 8), "F", 127);
  const col = decode0(input.slice(7, 10), "L", 8);
  return { row, column: col, seatId: row * 8 + col };
}

const text: string = await Deno.readTextFile("./5/input");
const lines = text.split("\n");
lines.pop();

const sorted = lines.map(decode).sort((a, b) => a.seatId - b.seatId);

console.log("Max seat id", sorted[sorted.length - 1].seatId);

for (let i = 1; i < sorted.length - 1; i++) {
  if (sorted[i + 1].seatId - sorted[i - 1].seatId != 2) {
    console.log("Found seat:", sorted[i + 1].seatId - 1);
    break;
  }
}
