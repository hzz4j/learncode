<script setup lang="ts">
import useURLLoader from '../hooks/useURLLoader'

interface DogResult{
  message: string,
  status: string
}
const { result,loading } = useURLLoader<DogResult>('https://dog.ceo/api/breeds/image/random')

</script>

<template>
  <h2 v-if="loading">Loading</h2>
  <img :src="result?.message" width="500" height="500" v-else>
</template>

<style scoped>
</style>
