import express from "express";
import cors from 'cors'

const app = express()

// 使用cors中间件
app.use(cors())

app.get("/test",(req,res)=>{
    console.log("get req");
    res.json({name:'Q10Viking'})
})

app.listen(9000,()=>{
    console.log("server start at 9000");
})