<template>
  <div class="container">
    <img :src="logo" alt="" class="logo" />
    <ul class="titles">
      <!-- <li class="title" :class="{ active: active === 1 }">首页</li>
      <li class="title">美食</li>
      <li class="title">服饰</li>
      <li class="title">母婴</li>
      <li class="title">个护</li>
      <li class="title">严选</li>
      <li class="title">数码</li>
      <li class="title">运动</li>
      <li class="title">杂质</li> -->

      <li
        v-for="(title, index) in titles"
        class="title"
        :key="index"
        :class="{ active: active === index }"
        @click="updateActive(index)"
      >
        {{ title }}
      </li>
    </ul>
    <div class="right-wrapper">
      <div class="search">
        <i class="fa-solid fa-magnifying-glass search-icon"></i>
        <input type="text" placeholder="search" />
      </div>
      <div class="cart">
        <i class="fa-solid fa-cart-shopping"></i>
        <span class="number">0</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import logo from "@/assets/images/logo.png"
const titles = [
  "首页",
  "美食",
  "服饰",
  "母婴",
  "个护",
  "严选",
  "数码",
  "运动",
  "杂质",
]
const active = ref(-1)

function updateActive(val: number) {
  active.value = val
}
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  .logo {
    width: 30rem;
    height: 132px;
    object-fit: cover;
    transform: scale(0.6);
  }

  .titles {
    display: flex;
    justify-content: space-evenly;
    width: 100%;
    font-size: 1.6rem;
    .title {
      display: flex;
      align-items: center;
      height: 3.2rem;

      &:hover,
      &.active {
        color: $theme-primary-color;
        border-bottom: 1px solid $theme-primary-color;
      }
    }
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
        font-size: 1.8rem;
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
        font-size: 2rem;
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
