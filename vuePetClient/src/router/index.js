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
            component: () => import('../views/registered/RegisteredView.vue')
        },
        {
            path: '/setting',
            name: 'Setting',
            meta: {
                title: '设置',

            },
            component: () => import('../views/set/SettingView.vue'),

        },
        {
            path: '/userProfile',
            name: 'userProfile',
            meta: {
                title: '资料',
            },
            component: () => import('../views/userProfile/UserProfileView.vue')
        },
        {
            path: '/avatar',
            name: 'avatar',
            meta: {
                title: '编辑头像',
            },
            component: () => import('../views/userProfile/AvatarView.vue')
        },
        {
            path: '/realName',
            name: 'realName',
            meta: {
                title: '编辑名字',
            },
            component: () => import('../views/userProfile/RealNameView.vue')
        },
        {
            path: '/sex',
            name: 'sex',
            meta: {
                title: '编辑性别',
            },
            component: () => import('../views/userProfile/SexView.vue')
        },
        {
          path: '/phone',
          name: 'phone',
          meta: {
              title: '编辑手机号',
          },
          component: () => import('../views/userProfile/PhoneView.vue')
        },
        {
            path: '/email',
            name: 'email',
            meta: {
                title: '编辑邮箱',
            },
            component: () => import('../views/userProfile/EmailView.vue')
        },
        {
          path:'/petsProfile',
          name: 'petsProfile',
          meta: {
              title: '宠物信息',
          },
          component: () => import('../views/petsProfile/PetsProfileView.vue')
        },{
        path: '/petInfo/:id',
            name: 'petInfo',
            meta: {
                title: '宠物详情',
            },
            component: () => import('../views/petsProfile/PetInfoView.vue')
        },
        {
            path: '/petsProfileEdit/:id',
            name: 'petsProfileEdit',
            meta: {
                title: '编辑宠物信息',
            },
            component: () => import('../views/petsProfile/PetsProfileEditView.vue')
        },
        {
            path: '/petsProfileAdd',
            name: 'petsProfileAdd',
            meta: {
                title: '添加宠物信息',
            },
            component: () => import('../views/petsProfile/PetsProfileAddView.vue')
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
