import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    routes: [

        {
            path: '/',
            // 通过redirect重定向设置默认路由跳转
            redirect: '/admin/login',
            component: () => import('../../Backend/views/AdminLogin.vue'),
        },
        // 后台管理系统路由
        {
            path: '/admin/login',
            name: 'adminLogin',
            meta: {
                title: '登录',
            },
            component: () => import('../../Backend/views/AdminLogin.vue'),
        },
        {
            path: '/admin',
            component: () => import('../../Backend/AdminApp.vue'),
            redirect: '/admin/login',
            children: [
                {
                    path: 'home',
                    name: 'adminHome',
                    meta: {
                        title: '首页',
                    },
                    component: () => import('../../Backend/views/Home.vue'),
                },
                {
                    path: 'UserCenter',
                    name: 'UserCenter',
                    meta: {
                        title: '个人中心',
                    },
                    component: () => import('../../Backend/views/UserCenter.vue'),
                },
                {
                    path: 'users',
                    name: 'Users',
                    meta: {
                        title: '用户管理',
                    },
                    component: () => import('../../Backend/views/Users.vue'),
                }

            ]
        },
        // 客户端路由
        {
            path: '/Client',
            name: 'Client',
            redirect: '/Client/home',
            component: () => import('../../Client/ClientApp.vue'),
            children: [
                {
                    path: 'home',
                    name: 'ClientHome',
                    meta: {
                        title: '首页',
                    },
                    component: () => import('../../Client/views/Home.vue'),
                },

            ]
        },
        //     自定义404错误页
        {
            path: '/404',
            name: 'NotFound',
            component: () => import('@/Common/views/NotFound.vue'),
        },
        //     404错误页
        {
            path: '/:pathMatch(.*)*',
            redirect: '/404',
        }
    ],
})

router.beforeEach((to, from, next) => {
    document.title = to.meta.title || '宠物管家系统'
    next()
})

export default router
