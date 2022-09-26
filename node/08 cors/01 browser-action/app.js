import express from "express";

const app = express()
app.get("/test",(req,res)=>{
    console.log("get req");
    res.json({name:'Q10Viking'})
})

app.listen(9000,()=>{
    console.log("server start at 9000");
})