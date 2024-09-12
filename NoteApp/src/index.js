const express = require("express");
const mongoose = require("mongoose");
const userRouter = require("./routes/userRouter");
const noteRouter = require("./routes/noteRouter");
const dotenv = require('dotenv');
const app = express();


dotenv.config();

// converting request body to json  
app.use(express.json());

// Using router
app.use("/users", userRouter);
app.use("/notes", noteRouter);

app.get("/", (req, res) => {
  res.send("Hello World");
});



// connecting to mongoose database
mongoose
  .connect(process.env.MONGO_URL) 
  .then(() => {
    app.listen(5000, () => {
      console.log("Server started on port 5000");
    });
  })
  .catch((err) => {
    console.log(err);
  });
