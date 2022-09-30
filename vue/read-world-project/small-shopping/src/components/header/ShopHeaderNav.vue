<template>
  <ul class="titles">
    <li class="title">首页</li>
    <li
      v-for="(categoryHead, index) in categoryHeads"
      class="title"
      :key="categoryHead.id"
      :class="{ active: active === index }"
      @click="updateActive(index)"
    >
      <span class="name">{{ categoryHead.name }}</span>
      <ul class="foods-wrapper container" :data-id="index">
        <li class="food" v-for="good in categoryHead.children" :key="good.id">
          <img :src="good.picture" alt="" />
          <p class="food-name">{{ good.name }}</p>
        </li>
      </ul>
    </li>
  </ul>
</template>

<script lang="ts" setup>
import { ref, onMounted, type Ref } from "vue"
import { getCategoryHead, type CategoryHead } from "@/api/category"
import DefaultCategoryHead from "@/api/defaultCategoryHead"

const categoryHeads: Ref<CategoryHead[]> = ref(DefaultCategoryHead)
onMounted(async () => {
  const categoryHead = await getCategoryHead()
  categoryHeads.value = categoryHead
})

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
      justify-content: flex-start;
      align-items: center;
      height: 0;
      opacity: 0;
      overflow: hidden;
      //   延迟消失，保证仍然hover
      transition: all 0.3s 0.1s;
      .food {
        margin: 0 2rem;
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

        &:hover {
          .food-name {
            color: $theme-primary-color;
          }
        }
      }
    }
  }
}
</style>
