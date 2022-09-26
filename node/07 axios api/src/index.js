import axios from "axios";

const API_TOKEN = "L7wUyoXF7ypDWm0WEfv3bfXgeSUkuMxw"
const API = "https://api.co2signal.com/v1/latest"

// axios.options(API,{
//     params: {
//         countryCode: 'FR'
//     },
//     headers: {
//         'auth-token': API_TOKEN
//     }
// }).then(response => {
//     console.log(response.headers)
// })



axios.get("http://localhost:9000/test",{
    params: {
        countryCode: 'FR'
    },
    headers: {
        'auth-token': API_TOKEN
    }
}).then(response => {
    console.log(response)
})
