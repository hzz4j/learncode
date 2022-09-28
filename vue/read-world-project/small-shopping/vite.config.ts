import { fileURLToPath, URL } from "node:url"

import { defineConfig } from "vite"
import vue from "@vitejs/plugin-vue"
import vueJsx from "@vitejs/plugin-vue-jsx"

console.log("import.meta.url", import.meta.url)
console.log("别名", fileURLToPath(new URL("./src", import.meta.url)))

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: [
          `@import "./src/assets/styles/__variables";`,
          `@import "./src/assets/styles/__mixin";`,
        ].join(""),
      },
    },
  },
})
