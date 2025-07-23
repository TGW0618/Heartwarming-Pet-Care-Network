import {createRouter, createWebHistory} from 'vue-router'
import {ElMessage} from "element-plus";
import {reactive} from "vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [

        {
            path: '/',
            // 通过redirect重定向设置默认路由跳转
            redirect: '/admin/home',
            component: () => import('../../Backend/views/Home.vue'),
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
            redirect: '/admin/home',
            children: [
                // 主要
                {
                    path: 'home',
                    name: 'adminHome',
                    meta: {
                        title: '首页',
                    },
                    component: () => import('../../Backend/views/Home.vue'),
                },
                {
                    path: 'Appointment',
                    name: 'Appointment',
                    meta: {
                        title: '预约管理',
                    },
                    component: () => import('../../Backend/views/Appointment.vue'),
                },
                {
                    path: 'order',
                    name: 'adminOrder',
                    meta: {
                        title: '订单管理',
                    }
                    ,
                    component: () => import('../../Backend/views/Order.vue'),
                },
                {
                    path: 'userCenter',
                    name: 'UserCenter',
                    meta: {
                        title: '个人中心',
                    },
                    component: () => import('../../Backend/views/UserCenter.vue'),
                },
                // 业务
                {
                    path: 'foster',
                    name: 'Foster',
                    meta: {
                        title: '宠物寄养',
                        roles: ['admin', 'foster_staff'],
                    },
                    component: () => import('../../Backend/views/Foster.vue'),
                },
                {
                    path: 'vet',
                    name: 'Vet',
                    meta: {
                        title: '医疗服务',
                        roles: ['admin', 'veterinarian'],
                    },
                    component: () => import('../../Backend/views/Vet.vue'),
                },
                {
                    path: 'users',
                    name: 'Users',
                    meta: {
                        title: '用户管理',
                        roles: ['admin'],
                    },
                    component: () => import('../../Backend/views/Users.vue'),
                }
                //     系统

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

const data = reactive({
    user: null
})


router.beforeEach((to, from, next) => {
    document.title = to.meta.title || '宠物管家系统'

    const user = JSON.parse(localStorage.getItem("petSysUser"))

    // 如果访问的是登录页面，直接放行
    if (to.path === '/admin/login') {
        next()
        return
    }

    // 检查是否已登录
    if (!user) {
        ElMessage.error("请先登录")
        next('/admin/login')
    } else {
        data.user = user
        // 权限校验
        const requiredRoles = to.meta.roles
        if (requiredRoles && !requiredRoles.includes(user.role)) {
            ElMessage.error("无权限")
            next('/404')
            return
        }
        next()
    }
})


export default router
