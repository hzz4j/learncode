<template>
  <ul class="titles">
    <li
      v-for="(title, index) in titles"
      class="title"
      :key="index"
      :class="{ active: active === index }"
      @click="updateActive(index)"
    >
      <span class="name">{{ title }}</span>
      <ul class="foods-wrapper container" :data-id="index">
        <li class="food" v-for="i in 10" :key="i">
          <img
            src="http://zhoushugang.gitee.io/erabbit-client-pc-static/uploads/img/category%20(4).png"
            alt=""
          />
          <p class="food-name">果干{{ index }}</p>
        </li>
      </ul>
    </li>
  </ul>
</template>

<script lang="ts" setup>
import { ref } from "vue"
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
.titles {
  display: flex;
  justify-content: space-evenly;
  width: 100%;
  font-size: 1.6rem;
  position: relative;

  .title {
    display: flex;
    align-items: center;
    height: 3.2rem;

    &:hover,
    &.active {
      background-color: antiquewhite;
      color: $theme-primary-color;
      border-bottom: 1px solid $theme-primary-color;
    }

    &:hover {
      .foods-wrapper {
        // height: auto;
        height: 132px;
        opacity: 1;
      }
    }

    .foods-wrapper {
      //   padding: 1rem 2rem;
      box-shadow: 0 0 0.5rem #ccc;
      position: absolute;
      top: 5.6rem;
      left: -20rem;
      display: flex;
      justify-content: space-evenly;
      align-items: center;
      height: 0;
      opacity: 0;
      overflow: hidden;
      //   延迟消失，保证仍然hover
      transition: all 0.3s 0.1s;
      .food {
        display: flex;
        flex-direction: column;
        align-items: center;

        img {
          width: 6rem;
          height: 6rem;
          object-fit: contain;
        }

        .food-name {
          margin-top: 1rem;
          font-size: 1rem;
          color: #000;
        }
      }
    }
  }
}
</style>
