// 前端脚本
import axios from "axios";
const nameEl = document.getElementById("name")
const api = "http://localhost:9000/test"

axios.get(api).then(response => {
    console.log(response.data)
    nameEl.textContent = response.data.name
    nameEl.className = "success"
}).catch(e => {
    console.log("cors appear")
    nameEl.className = "errors"
    nameEl.textContent = "Cors Appear"
})