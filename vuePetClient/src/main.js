import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import '@/assets/global.css'
import Vant from 'vant'
import 'vant/lib/index.css'
import 'amfe-flexible'

const app = createApp(App)

app.use(createPinia())
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
