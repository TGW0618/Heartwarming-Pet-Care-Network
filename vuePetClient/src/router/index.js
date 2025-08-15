import {createRouter, createWebHistory} from 'vue-router'


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'HomeView',
            redirect: '/home',
            component: () => import('../views/HomeView.vue'),
            children: [
                {
                    path: 'home',
                    name: 'Home',
                    meta: {
                        title: '首页',
                    },
                    component: () => import('../views/petViews/Home.vue'),
                },
                {
                    path: 'chat',
                    name: 'chat',
                    meta: {
                        title: 'chat',
                    },
                    component: () => import('../views/petViews/Chat.vue'),
                },
                {
                    path: 'order',
                    name: 'order',
                    meta: {
                        title: 'order',
                    },
                    component: () => import('../views/petViews/Order.vue'),
                },
                {
                    path: 'userinfo',
                    name: 'userinfo',
                    meta: {
                        title: 'userinfo',
                    },
                    component: () => import('../views/petViews/UserInfo.vue'),
                },
                {
                    path: '/login',
                    name: 'Login',
                    meta: {
                        title: 'login',
                    },
                    component: () => import('../views/login/LoginView.vue')
                },
                {
                    path: '/:pathMatch(.*)*',
                    name: 'NotFound',
                    meta: {
                        title: '404',
                    },
                    component: () => import('../views/NotFound.vue')
                },


            ],
        },
        {
            path: '/loginInfo',
            name: 'LoginInfo',
            meta: {
                title: '登录',
            },
            component: () => import('../views/login/LoginInfo.vue')
        },
        {
            path: '/phoneLogin',
            name: 'phoneLogin',
            meta: {
                title: '手机号登录',
            },
            component: () => import('../views/login/PhoneLogin.vue')
        },
        {
            path: '/petLogin',
            name: 'petLogin',
            meta: {
                title: 'pet账号登录',
            },
            component: () => import('../views/login/PetLogin.vue')
        },
        {
            path: '/register',
            name: 'Register',
            meta: {
                title: '注册',
            },
            component: () => import('../views/RegisteredView.vue')
        },
        {
            path: '/setting',
            name: 'Setting',
            meta: {
                title: '设置',

            },
            component: () => import('../views/set/SettingView.vue'),

        },

    ],
})


// 定义不需要登录验证的页面白名单
const whiteList = [
    '/login',
    '/loginInfo',
    '/phoneLogin',
    '/petLogin',
    '/register',
    '/home',
    '/', // 根路径
    '/404',
    '/:pathMatch(.*)*'
]

router.beforeEach((to, from, next) => {
    document.title = to.meta.title || '宠物管家系统'

    // 如果访问的页面在白名单中，直接放行
    if (whiteList.includes(to.path)) {
        next()
        return
    }


    // 获取当前token
    const token = localStorage.getItem('token')

    // 检查是否已登录
    if (!token) {
        // 记录用户原本想访问的页面
        next({
            path: '/login',
            query: {redirect: to.fullPath} // 将原路径作为查询参数传递
        })
    } else {
        next()
    }
})

export default router
