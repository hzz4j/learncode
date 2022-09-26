import express from "express";

const app = express()
app.get("/test",(req,res)=>{
    console.log("get req");
    // add Access-Control-Allow-Origin header
    res.setHeader("Access-Control-Allow-Origin","*")
    res.json({name:'Q10Viking'})
})

app.listen(9000,()=>{
    console.log("server start at 9000");
})