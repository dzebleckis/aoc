export {};

const text = await Deno.readTextFile("./9/input");

const map: number[][] = [];
text
  .split("\n")
  .filter((_) => _ != "")
  //   .slice(0,1)
  .forEach((l) => {
    // console.log("lone", l);
    map.push(l.split("").map((_) => parseInt(_)));
  });

// console.log("input", map);


type Node = {
    value: number;
    neighbors: Node[]
}

const length = map[0].length;

let riskLevel = 0;

for (let i = 0; i < map.length; i++) {
  //   console.log(map[i]);
  for (let j = 0; j < map[i].length; j++) {
    const left = j - 1 < 0 ? Number.MAX_VALUE : map[i][j - 1];
    const right = j + 1 >= map[i].length ? Number.MAX_VALUE : map[i][j + 1];
    const top = i - 1 < 0 ? Number.MAX_VALUE : map[i - 1][j];
    const bottom = i + 1 >= map.length ? Number.MAX_VALUE : map[i + 1][j];
    // console.log(map[i][j], { left, right, top, bottom });
    // console.log('i', i, j, map[i][j]);
    if (
      map[i][j] < left && map[i][j] < right && map[i][j] < top &&
      map[i][j] < bottom
    ) {
    //   console.log("found", map[i][j]);
      riskLevel += map[i][j] + 1;
    }
  }
}

console.log('riskLevel', riskLevel)
