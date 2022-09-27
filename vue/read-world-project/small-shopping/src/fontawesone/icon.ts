import { library } from "@fortawesome/fontawesome-svg-core"

import {
  faTiktok,
  faWeibo,
  faBilibili,
} from "@fortawesome/free-brands-svg-icons"

import { faMobileAlt, faUser } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome"

library.add(faTiktok, faWeibo, faBilibili, faMobileAlt, faUser)

export default FontAwesomeIcon
