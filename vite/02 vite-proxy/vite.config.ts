import { defineConfig, loadEnv } from "vite";
console.log("Running?");

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), "");
  console.log(env.VITE_API_BASEURL);

  return {
    // vite config
    base: "./",
    server: {
      port: 5200,
      proxy: {
        "/api": {
          target: "http://localhost:9000",
          changeOrigin: true,
          rewrite: (path) => {
            console.log(path, "------------------");
            return path.replace(/^\/api/, "");
          },
        },
      },
    },
  };
});
