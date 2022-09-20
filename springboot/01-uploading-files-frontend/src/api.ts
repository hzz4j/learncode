

function getAll<T>(){
    return new Promise<T>(resolve => {
        fetch("http://127.0.0.1:8080/api/all").then(response => response.json())
        .then(result => {
            console.log(result.data)
            resolve(result.data)
        }).catch(e => {
            console.log(e)
        })
    })
}


function upload<T>(formdata: FormData){
    return new Promise<T>(resolve => {
        fetch("http://127.0.0.1:8080/api/upload",{
            method: "POST",
            body: formdata
        })
        .then(response => response.json())
        .then(result => {
            console.log(result)
            resolve(result.data)
        }).catch(e => {
            console.log(e)
        })
    })
}

export {
    getAll,
    upload
}