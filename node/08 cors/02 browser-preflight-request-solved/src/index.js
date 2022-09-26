// 前端脚本
import axios from "axios";
const api = "http://localhost:9000/test"
const nameEl = document.getElementById("name")
// 自定义了一个header
const config = {
    headers: {
        'auth-token': 'q10viking-token'
    }
}

axios.get(api,config).then(response => {
    console.log(response.data)
    nameEl.textContent = response.data.name
    nameEl.className = "success"
}).catch(e => {
    console.log("cors appear")
    nameEl.className = "errors"
    nameEl.textContent = "Cors Appear"
})