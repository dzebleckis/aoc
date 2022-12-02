export {};

const text = await Deno.readTextFile("./4/input");
const input = text.split("\n").filter((n) => n != "");

const numbersLine = input[0];
input.shift();
const boardSize = 5;

type Digit = {
  number: number;
  matched: boolean;
};

type B2 = Digit[][];

type Board = Digit[][];

const boards: Board[] = [];

let rows = [];
for (const line of input) {
  const digits = line
    .split(" ")
    .filter((d) => d != "")
    .map((d) => ({
      number: parseInt(d.trim()),
      matched: false,
    }));

  rows.push(digits);

  if (rows.length === boardSize) {
    boards.push([...rows]);
    rows = [];
  }
}

console.log("numbers", numbersLine);

function boardFinished(board: Board): boolean {
  let rows = true;
  let cols = true;
  for (let i = 0; i < boardSize; i++) {
    for (let j = 0; j < boardSize; j++) {
      rows = rows && board[i][j].matched;
      cols = cols && board[j][i].matched;
    }
    if (rows || cols) {
      return true;
    } else {
      rows = true;
      cols = true;
    }
  }
  return false;
}

function sortWinners(
  boards: Board[],
  numbers: number[],
  acc: { number: number; board: Board }[] = [],
): { number: number; board: Board }[] {
  for (let i = 0; i < numbers.length; i++) {
    for (let j = 0; j < boards.length; j++) {
      for (let row = 0; row < boardSize; row++) {
        for (let col = 0; col < boardSize; col++) {
          const board = boards[j];
          if (board[row][col].number === numbers[i]) {
            board[row][col].matched = true;
            if (boardFinished(board)) {
              acc.push({ number: numbers[i], board });
              const n2 = numbers.slice(i);
              const copy = [...boards];
              copy.splice(j, 1);

              return sortWinners(copy, n2, acc);
            }
          }
        }
      }
    }
  }
  return acc;
}

function sumBoard(board: Board): number {
  let result = 0;
  for (const row of board) {
    for (const digit of row) {
      if (!digit.matched) {
        result += digit.number;
      }
    }
  }
  return result;
}

const numbers = numbersLine.split(",").map((n) => parseInt(n));

const results = sortWinners(boards, numbers)!;
const winner = results[0];

const sum1 = sumBoard(winner.board);
console.log(winner.number, sum1, winner.number * sum1);


const loser = results[results.length - 1];
const sum2 = sumBoard(loser.board);
console.log(loser.number, sum2, loser.number * sum2);
