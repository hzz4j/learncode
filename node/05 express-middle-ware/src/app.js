import { isAuthorized } from "./helper.js";
import express from "express";
const app = express();
const port = 3000;


app.get("/", (req, res) => res.send("Hello World!"));

app.get("/users", isAuthorized,(req, res) => {
  res.json([
    {
      id: 1,
      name: "User Userson",
    },
  ]);
});

app.get("/products", (req, res) => {
  res.json([
    {
      id: 1,
      name: "The Bluest Eye",
    },
  ]);
});

app.listen(port, () => console.log(`Example app listening on port ${port}!`));