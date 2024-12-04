import { assertEquals } from "https://deno.land/std@0.79.0/testing/asserts.ts";

import { countAnyoneYes, countEveryoneYes } from "./index.ts";

Deno.test("counts anyone answer yes in a group", () => {
  const input = "abcxabcyabcz";

  const count = countAnyoneYes(input);
  assertEquals(count, 6);
});

Deno.test("counts everyone answer yes in a group", () => {
  const input = "abcx abcy abcz";

  const count = countEveryoneYes(input);
  assertEquals(count, 3);
});
