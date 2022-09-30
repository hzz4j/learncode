import { request } from "@/utils/request"

type Good = {
  id: string
  name: string
  picture: string
}

export type CategoryHead = {
  id: string
  name: string
  picture: string
  children: Good[]
}

export function getCategoryHead() {
  return request<CategoryHead[]>("/home/category/head", "get")
}
