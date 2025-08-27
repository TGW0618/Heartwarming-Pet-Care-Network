import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        // vueDevTools(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        },
    },
    server: {
        port: 8989,
        host: '0.0.0.0',// 允许外部访问
        // 添加代理配置
        proxy: {
            '/files': {
                target: 'http://localhost:8083', // 后端服务地址
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/files/, '/files')
            }
        }
    }
})
