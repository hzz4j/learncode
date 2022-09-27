import { createApp } from "vue"
import App from "./App.vue"

import "@/assets/styles/common.scss"
// fontawesome icon
import FontAwesomeIcon from "./fontawesone/icon"

const app = createApp(App)
app.component("font-awesome-icon", FontAwesomeIcon)
app.mount("#app")
