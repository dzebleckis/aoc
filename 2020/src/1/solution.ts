const text = await Deno.readTextFile("./input");
const lines = text.split("\n");

let expenses = new Map();
for (const l  of lines) {
    expenses.set(Number(l), 1);
}

for (const e of expenses.keys()) {
    const o = 2020 - e;
    if (o > 0 && expenses.has(o)) {
        console.log(e, o, e * o);
        break;
    }
}
