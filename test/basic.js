"use strict";

let expect = require("chai").expect;
let codecheck = require("codecheck");
let testcase = require("./basic_testcase.json");
let app = codecheck.consoleApp(process.env.APP_COMMAND);

describe("Calling Hash API", () => {
  testcase.forEach((t) => {
    let input = t.input;
    let title = t.it;
    let expectedOutput = t.output;
    it(title, () => {
      return app.codecheck(input).then( result => {
        expect(result.code).to.equal(0, "expect codecheck CLI to exit with status code 0");
        let actualOutput = result.stdout.join("");
        if (typeof expectedOutput === "number") {
          actualOutput = parseInt(actualOutput);
          expect(actualOutput).not.to.be.NaN;
        }
        expect(actualOutput).to.be.equal(expectedOutput);
      });
    });
  });
});
