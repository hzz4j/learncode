import { defineStore } from "pinia"
import { ref, type Ref } from "vue"

import DefaultCategoryHead from "@/api/defaultCategoryHead"
import type { CategoryHead } from "@/types/category/CategoryHead"
import { getCategoryHead as categoryApi } from "@/api/category"

/**
 * 分类store
 */
export const useCategoryStore = defineStore("category", () => {
  const categoryHeads: Ref<CategoryHead[]> = ref(DefaultCategoryHead)

  async function initCategoryHead() {
    categoryHeads.value = await categoryApi()
  }

  return { categoryHeads, initCategoryHead }
})
