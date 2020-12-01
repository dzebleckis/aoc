const text = await Deno.readTextFile("./input");
const lines = text.split("\n");

let expenses = new Set<number>();
for (const l  of lines) {
    expenses.add(Number(l));
}

for (const e of expenses) {
    const o = 2020 - e;
    if (o > 0 && expenses.has(o)) {
        console.log(e, o, e * o);
        break;
    }
}
