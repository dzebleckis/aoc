import {
  assertArrayIncludes,
  assertEquals,
} from "https://deno.land/std@0.79.0/testing/asserts.ts";

import { isByrValid, isHgtValid, isIyrValid, isPidValid } from "./index.ts";

Deno.test("valid byr", () => {
  const data = ["2002", "1920", "2000"];
  for (const input of data) {
    assertEquals(isByrValid(input), true, input);
  }
});

Deno.test("not valid byr", () => {
  const data = ["2003", "1919", "2010", "02002"];
  for (const input of data) {
    assertEquals(isByrValid(input), false, input);
  }
});

Deno.test("valid iyr", () => {
  const data = ["2010", "2020"];
  for (const input of data) {
    assertEquals(isIyrValid(input), true, input);
  }
});

Deno.test("valid height", () => {
  const data = ["60in", "190cm"];
  for (const input of data) {
    assertEquals(isHgtValid(input), true, input);
  }
});

Deno.test("not valid height", () => {
  const data = ["190in", "190"];
  for (const input of data) {
    assertEquals(isHgtValid(input), false, input);
  }
});

Deno.test("not valid pid", () => {
  const data = ["3966920279", "90919899"];
  for (const input of data) {
    assertEquals(isPidValid(input), false, input);
  }
});
