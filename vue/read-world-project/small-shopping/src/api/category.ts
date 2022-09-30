import { request } from "@/utils/request"
import type { CategoryHead } from "@/types/category/CategoryHead"

/**
 * 获取头部分类数据
 * @returns CategoryHead
 */
export function getCategoryHead() {
  return request<CategoryHead[]>("/home/category/head", "get")
}
