import express from "express";

const app = express();

app.get("/user", (req, res) => {
  console.log("Received a request");
  res.json({ name: "q10viking" });
});

app.listen(9000, () => {
  console.log("Server start at 9000");
});
