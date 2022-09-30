import { ref } from "vue"
import useEventListener from "./useEventListener"
// 参考：https://vueuse.org/core/useWindowScroll/#usewindowscroll
// https://vuejs.org/guide/reusability/composables.html

export default function useWindowScroll() {
  const x = ref(window.scrollX)
  const y = ref(window.scrollY)

  function scrollerListener() {
    x.value = window.scrollX
    y.value = window.scrollY
  }

  useEventListener(window, "scroll", scrollerListener)
  return { x, y }
}
