import { assertEquals } from "https://deno.land/std@0.79.0/testing/asserts.ts";

import { decode } from "./index.ts";

Deno.test("decodes input", () => {
  const data = [{
    encoded: "FBFBBFFRLR",
    result: {
      row: 44,
      column: 5,
      seatId: 357,
    },
  }, {
    encoded: "BFFFBBFRRR",
    result: {
      row: 70,
      column: 7,
      seatId: 567,
    },
  }, {
    encoded: "FFFBBBFRRR",
    result: {
      row: 14,
      column: 7,
      seatId: 119,
    },
  }, {
    encoded: "BBFFBBFRLL",
    result: {
      row: 102,
      column: 4,
      seatId: 820,
    },
  }];

  for (const input of data) {
    const result = decode(input.encoded);
    assertEquals(result, input.result);
  }
});
