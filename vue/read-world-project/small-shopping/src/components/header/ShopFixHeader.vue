<template>
  <!-- 父容器负责固定 -->
  <div class="header" :class="{ sticky: fixNavBar }">
    <!-- 子容器负责显示 -->
    <div class="container" v-show="fixNavBar">
      <img :src="logo" alt="" class="logo" />
      <!-- titles -->
      <ShopHeaderNav></ShopHeaderNav>
      <ul class="fix-right-wrapper">
        <li>品牌</li>
        <li>专题</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts" setup>
import logo from "@/assets/images/logo.png"
import ShopHeaderNav from "./ShopHeaderNav.vue"
import { computed } from "vue"
import useWindowScroll from "@/composables/useWindowScroll"

const { y } = useWindowScroll()
const fixNavBar = computed(() => y.value > 76)
</script>

<style lang="scss" scoped>
.header {
  width: 100%;
  transform: translateY(-100%);
  background-color: #fff;
  transition: transform 0.3s linear;
  &.sticky {
    position: fixed;
    top: 0;
    left: 0;
    transform: none;
    z-index: 100;
    .logo {
      height: 80px;
    }
  }
}
.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;

  .logo {
    width: 20rem;
    height: 132px;
    object-fit: contain;
  }

  .fix-right-wrapper {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    font-size: 1.6rem;
    width: 28.2rem;
    box-sizing: border-box;
    border-left: 2px solid $theme-primary-color;

    li:hover {
      color: $theme-primary-color;
    }
  }
}
</style>
