<template>
  <div class="container">
    <img :src="logo" alt="" class="logo" />
    <!-- titles -->
    <ShopHeaderNav></ShopHeaderNav>
    <div class="right-wrapper">
      <div class="search">
        <i class="iconfont icon-sousuo search-icon"></i>
        <input type="text" placeholder="search" />
      </div>
      <div class="cart">
        <i class="iconfont icon-cart"></i>
        <span class="number">0</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import logo from "@/assets/images/logo.png"
import ShopHeaderNav from "./ShopHeaderNav.vue"
</script>

<style lang="scss" scoped>
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

  .right-wrapper {
    display: flex;
    justify-content: flex-start;
    align-items: center;

    .search {
      position: relative;
      .search-icon {
        position: absolute;
        top: 1.6rem;
        transform: translateY(-50%);
        color: #000;
        font-size: 2rem;
      }
      input {
        width: 17rem;
        height: 3.2rem;
        font-size: 1.6rem;
        padding: 0.3rem 0.3rem 0.3rem 2rem;
        border: none;

        &:focus {
          outline: none;
        }
      }
    }

    .cart {
      position: relative;
      i {
        font-size: 2.5rem;
      }

      .number {
        padding: 0.1rem 0.6rem;
        border-radius: 10px;
        color: #fff;
        position: absolute;
        top: -1rem;
        left: 1.8rem;
        background-color: red;
      }
    }
  }
}
</style>
