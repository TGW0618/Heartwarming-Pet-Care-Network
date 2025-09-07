import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './Backend/router'
import '@/assets/global.css'
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import {zhCn} from "element-plus/es/locale/index";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus, {locale: zhCn})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}


app.mount('#app')
