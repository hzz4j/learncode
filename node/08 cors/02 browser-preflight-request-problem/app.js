import express from "express";

const app = express()
app.get("/test",(req,res)=>{
    console.log("get req");
    res.json({name:'Q10Viking'})
})

// 添加一个options方法，来观察
app.options("/test",(req,res)=>{
    console.log("Preflight Appear");
    res.send("finished:)")
})

app.listen(9000,()=>{
    console.log("server start at 9000");
})