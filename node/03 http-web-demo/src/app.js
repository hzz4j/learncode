const http = require("http")
const PORT = 9000

const app = http.createServer((req, res) => {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({
      data: 'Hello World!'
    }))
})

app.listen(PORT,()=>{
    console.log(`listening on port ${PORT}`);
})


  