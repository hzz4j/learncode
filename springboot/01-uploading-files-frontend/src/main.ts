import './style.scss'
import { getAll,upload } from './api'
const inputFile = document.getElementById("uploadFile")! as HTMLInputElement
const showContainer = document.getElementById("showContainer")! as HTMLElement

showImages()
inputFile.addEventListener("change",(e) => {
  const inputFile = e.target as HTMLInputElement
  if(inputFile.files){
    const formdata = new FormData()
    formdata.append("file",inputFile.files[0])
    upload<string>(formdata).then(data => createImage(data))
  }
})

async function showImages(){
  const allImages = await getAll<string[]>()
  console.log(allImages);
  if(allImages){
    allImages.forEach(url => createImage(url))
  }
  
}

function createImage(url: string){
  console.log(url);
  
  const div = document.createElement("div")
  div.classList.add("image")

  div.innerHTML = `
    <img src="${url}" alt="">
  `
  showContainer.appendChild(div)
}