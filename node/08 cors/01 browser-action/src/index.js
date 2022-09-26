// 前端脚本
import axios from "axios";
const api = "http://localhost:9000/test"

axios.get(api).then(response => {
    console.log(response.data)
}).catch(e => console.log("cors appear"))