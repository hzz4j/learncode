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

export async function getCategoryHead() {
  const data = await request<CategoryHead[]>("/home/category/head", "get")
  return data
}
