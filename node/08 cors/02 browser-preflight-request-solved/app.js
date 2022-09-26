import express from "express";

const app = express()
app.get("/test",(req,res)=>{
    console.log("get req");
    res.json({name:'Q10Viking'})
})

// 添加一个options方法，来观察
app.options("/test",(req,res)=>{
    console.log("Preflight Appear");
    // 添加允许的跨域的策略
    res.setHeader("Access-Control-Allow-Origin","*") // 允许所有来源
    // 由于自定义了头部，需要指定允许跨域指定的头部
    res.setHeader("Access-Control-Allow-Headers","auth-token")
    res.send("finished:)")
})

app.listen(9000,()=>{
    console.log("server start at 9000");
})