import {createRouter, createWebHistory} from 'vue-router'
import {ElMessage} from "element-plus";

const router = createRouter({
    history: createWebHistory(),
    routes: [

        {
            path: '/',
            // 通过redirect重定向设置默认路由跳转
            redirect: '/admin/home',
            component: () => import('../views/Home.vue'),
        },
        // 后台管理系统路由
        {
            path: '/admin/login',
            name: 'adminLogin',
            meta: {
                title: '登录',
            },
            component: () => import('../views/AdminLogin.vue'),
        },
        {
            path: '/admin',
            component: () => import('../AdminApp.vue'),
            redirect: '/admin/home',
            children: [
                // 首页
                {
                    path: 'home',
                    name: 'adminHome',
                    meta: {
                        title: '首页',
                    },
                    component: () => import('../views/Home.vue'),
                },
                // 个人中心
                {
                    path: 'userCenter',
                    name: 'UserCenter',
                    meta: {
                        title: '个人中心',
                    },
                    component: () => import('../views/UserCenter.vue'),
                },
                // 预约处理
                {
                    path: 'bookingsHome',
                    name: 'bookingsHome',
                    meta: {
                        title: '预约处理',
                    },
                    component: () => import('../views/BookingsHome.vue'),
                },
                // 订单管理
                {
                    path: 'orderHome',
                    name: 'orderHome',
                    meta: {
                        title: '订单管理',
                    }
                    ,
                    component: () => import('../views/OrderHome.vue'),
                },
                // 服务管理
                {
                    path: 'serviceHome',
                    name: 'serviceHome',
                    meta: {
                        title: '服务管理',
                    },
                    component: () => import('../views/ServiceHome.vue'),
                },
                // 宠舍管理
                {
                    path: 'roomsHome',
                    name: 'roomsHome',
                    meta: {
                        title: '宠舍管理',
                        roles: ['admin', 'foster_staff'],
                    },
                    component: () => import('../views/RoomsHome.vue'),
                },
                // 用户管理
                {
                    path: 'users',
                    name: 'Users',
                    meta: {
                        title: '用户管理',
                        roles: ['admin'],
                    },
                    component: () => import('../views/Users.vue'),
                },
                // 数据统计
                {
                    path: 'statisticsHome',
                    name: 'statisticsHome',
                    meta: {
                        title: '数据统计',
                        roles: ['admin'],
                    },
                    component: () => import('../views/StatisticsHome.vue'),
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

    const token = localStorage.getItem('token')

    // 如果访问的是登录页面，直接放行
    if (to.path === '/admin/login') {
        next()
        return
    }

    // 检查是否已登录
    if (!token) {
        ElMessage.error("请先登录")
        next('/admin/login')
    } else {
        // 正确获取用户信息
        const userStr = localStorage.getItem('petSysUser')
        if (userStr) {
            try {
                const user = JSON.parse(userStr)

                // 权限校验
                const requiredRoles = to.meta.roles
                if (requiredRoles && !requiredRoles.includes(user.role)) {
                    ElMessage.error("无权限访问该页面")
                    // 重定向到首页
                    next('/admin/home')
                    return
                }
                next()
            } catch (e) {
                ElMessage.error("用户信息解析失败，请重新登录")
                next('/admin/login')
            }
        } else {
            ElMessage.error("用户信息不存在，请重新登录")
            next('/admin/login')
        }
    }
})


export default router
