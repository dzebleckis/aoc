export function parseGroups(lines: string[], separator: string = ""): string[] {
  let buffer = "";
  let data: string[] = [];

  for (const line of lines) {
    if (line === "") {
      data.push(buffer.trim());
      buffer = "";
    } else {
      buffer += separator + line;
    }
  }
  return data;
}
