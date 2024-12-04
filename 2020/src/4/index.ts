const text: string = await Deno.readTextFile("./4/input");
const lines = text.split("\n");

function parse(data: string): Map<string, string> {
  const a = data.split(" ").map<[string, string]>((d) => {
    const parts = d.split(":");
    return [parts[0].trim(), parts[1].trim()];
  });
  return new Map(a);
}

export function isValid(passport: Map<string, string>): boolean {
  return passport.size == 8 || (passport.size == 7 && !passport.has("cid"));
}

export function isByrValid(byr: string): boolean {
  const n = parseInt(byr);
  return byr.length === 4 && n >= 1920 && n <= 2002;
}

export function isIyrValid(iyr: string): boolean {
  const n = parseInt(iyr);
  return iyr.length === 4 && n >= 2010 && n <= 2020;
}

export function isEyrValid(eyr: string): boolean {
  const n = parseInt(eyr);
  return eyr.length === 4 && n >= 2020 && n <= 2030;
}

export function isHgtValid(hgt: string): boolean {
  const value = parseInt(hgt.slice(0, -2));
  const metric = hgt.slice(-2);

  switch (metric) {
    case "cm":
      return value >= 150 && value <= 193;
    case "in":
      return value >= 59 && value <= 76;
    default:
      return false;
  }
}

export function isHclValid(hcl: string): boolean {
  return /^#[0-9,a-f]{6}$/.test(hcl);
}

export function isEclValid(ecl: string): boolean {
  return ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"].includes(ecl);
}

export function isPidValid(pid: string): boolean {
  return /^\d{9}$/.test(pid);
}

function isValid2(passport: Map<string, string>): boolean {
  return isValid(passport) &&
    isByrValid(passport.get("byr")!) &&
    isIyrValid(passport.get("iyr")!) &&
    isEyrValid(passport.get("eyr")!) &&
    isHgtValid(passport.get("hgt")!) &&
    isHclValid(passport.get("hcl")!) &&
    isEclValid(passport.get("ecl")!) &&
    isPidValid(passport.get("pid")!);
}

let data = [];
let buffer = "";
for (const line of lines) {
  if (line === "") {
    data.push(parse(buffer.trim()));
    buffer = "";
  } else {
    buffer += " " + line;
  }
}

const count = data.map(isValid2).filter((v) => v === true).length;
console.log("Count", count);
