<template>
  <GlobalNavHeader></GlobalNavHeader>
  <ShopHeader></ShopHeader>
  <main style="height: 20rem"></main>
  <GlobalFooter></GlobalFooter>
</template>

<script setup lang="ts">
import GlobalNavHeader from "@/components/header/GlobalNavHeader.vue"
import ShopHeader from "@/components/header/ShopHeader.vue"
import GlobalFooter from "@/components/footer/GlobalFooter.vue"

import { onMounted } from "vue"
console.log("App.vue setup")

onMounted(() => {
  console.log("App.vue onMounted")
})
</script>
