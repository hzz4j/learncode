import express from "express";
// import cors from 'cors'

const app = express()

// app.use(cors())
app.get("/test",(req,res)=>{
    //res.setHeader('access-control-allow-origin','*')
    res.json({name:'Q10Viking'})
})


app.options("/test",(req,res)=>{
    console.log("options request");
    // res.setHeader('access-control-allow-origin','*')
    // res.setHeader('access-control-allow-headers','auth-token')
    res.status(201)
    res.send('None shall pass');
    // res.end()

})


app.listen(9000,()=>{
    console.log("server start");
})