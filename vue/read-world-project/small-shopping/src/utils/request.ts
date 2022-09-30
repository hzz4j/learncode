import axios, { type AxiosInstance, type AxiosResponse } from "axios"
import type { AppResponseData } from "@/types/ResponseData"

// config axios
const apiClient: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  headers: {
    "Content-type": "application/json",
  },
})

/**
 *
 * @param url url地址
 * @param method 请求方法
 * @param data 数据
 * @returns
 */
export async function request<T>(
  url: string,
  method: string = "get",
  data: any = {}
) {
  const response: AxiosResponse<AppResponseData<T>> = await apiClient({
    url,
    method,
    [method.toLowerCase() === "get" ? "params" : "data"]: data,
  })
  return response.data.result
}
