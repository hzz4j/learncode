import "./style.css";
import axios from "axios";

console.log(import.meta.env.VITE_API_BASEURL);
const baseurl = import.meta.env.VITE_API_BASEURL;

// 如果是相对路径 axios.get("/api"),那么默认的base-url就是当前的服务器路径
axios.get(`${baseurl}/user`).then((res) => {
  console.log(res.data);
});
