type Good = {
  id: string
  name: string
  picture: string
}

// 头部分类信息
export type CategoryHead = {
  id: string
  name: string
  picture: string
  children: Good[]
}
