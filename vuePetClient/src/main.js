import {createApp} from 'vue'
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' //引入持久化插件
import App from './App.vue'
import router from './router'
import '@/assets/global.css'
import Vant from 'vant'
import 'vant/lib/index.css'
import 'amfe-flexible'

const app = createApp(App)

// 创建 pinia 实例并应用插件
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate) //将插件添加到 pinia 实例上

app.use(pinia) // 使用配置好的 pinia 实例
app.use(router)
app.use(Vant)

// 设置根字体大小
function setRem() {
    const docEl = document.documentElement
    const width = docEl.clientWidth
    const rem = width / 10
    docEl.style.fontSize = rem + 'px'
}

setRem()
window.addEventListener('resize', setRem)

app.mount('#app')
