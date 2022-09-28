import { defineConfig, loadEnv } from "vite";

export default defineConfig(({ command, mode }) => {
  console.log("mode///", mode);
  console.log("process.env///", process.env.NODE_NAME); // 拿不到

  // Load env file based on `mode` in the current working directory.
  // Set the third parameter to '' to load all env regardless of the `VITE_` prefix.
  // 会加载当前项目路径下的.env.[model]文件
  const env = loadEnv(mode, process.cwd(), "");
  console.log("env///", env.NODE_NAME);

  return {};
});
