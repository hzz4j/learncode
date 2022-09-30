import { onMounted, onUnmounted } from "vue"

type Target = Window | HTMLElement
type Callback = () => void

export default function useEventListener(
  target: Target,
  event: string,
  callback: Callback
) {
  onMounted(() => {
    target.addEventListener(event, callback)
  })

  onUnmounted(() => {
    target.removeEventListener(event, callback)
  })
}
