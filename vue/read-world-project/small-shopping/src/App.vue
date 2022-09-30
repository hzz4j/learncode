<template>
  <GlobalNavHeader></GlobalNavHeader>
  <ShopHeader></ShopHeader>
  <ShopFixHeader></ShopFixHeader>
  <main style="height: 50rem"></main>
  <GlobalFooter></GlobalFooter>
</template>

<script setup lang="ts">
import GlobalNavHeader from "@/components/header/GlobalNavHeader.vue"
import ShopHeader from "@/components/header/ShopHeader.vue"
import ShopFixHeader from "./components/header/ShopFixHeader.vue"
import GlobalFooter from "@/components/footer/GlobalFooter.vue"
</script>
