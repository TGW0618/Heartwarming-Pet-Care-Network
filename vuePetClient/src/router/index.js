import {createRouter, createWebHistory} from 'vue-router'


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'homeView',
            redirect: '/home',
            component: () => import('../views/HomeView.vue'),
            children: [
                {
                    path: 'home',
                    name: 'home',
                    meta: {
                        title: '首页',
                    },
                    component: () => import('../views/petViews/Home.vue'),
                },
                {
                    path: 'booking',
                    name: 'booking',
                    meta: {
                        title: '预约',
                    },
                    component: () => import('../views/petViews/booking.vue'),
                    children: [
                        {
                            path: 'bookingHome',
                            name: 'bookingHome',
                            meta: {
                                title: '预约',
                            },
                            component: () => import('../views/petViews/booking/bookingHome.vue'),
                        },
                        {
                            path: 'bookingsMedical',
                            name: 'bookingsMedical',
                            meta: {
                                title: '预约医生',
                            },
                            component: () => import('../views/petViews/booking/bookingsMedical.vue'),
                        },
                        // 寄养服务列表页
                        {
                            path: 'fosterServiceList',
                            name: 'fosterServiceList',
                            meta: {
                                title: '寄养服务列表',
                            },
                            component: () => import('../views/petViews/booking/fosterServiceList.vue'),
                        },
                        //     服务详细信息页
                        {
                            path: 'serviceDetail/:id',
                            name: 'serviceDetail',
                            meta: {
                                title: '服务详细信息',
                            },
                            component: () => import('../views/petViews/booking/serviceDetail.vue'),
                        },
                        //     服务预约
                         {
                             path: 'serviceBooking/:id',
                             name: 'serviceBooking',
                             meta: {
                                 title: '服务预约',
                             },
                              component: () => import('@/views/petViews/booking/serviceBooking.vue'),

                         },
                    ]
                },

                {
                    path: 'consultation',
                    name: 'consultation',
                    meta: {
                        title: '咨询',
                    },
                    component: () => import('../views/petViews/consultation.vue'),
                },
                {
                    path: 'orders',
                    name: 'orders',
                    meta: {
                        title: '订单',
                    },
                    component: () => import('../views/petViews/orders.vue'),
                    children: [
                        {
                            path: 'orderList',
                            name: 'orderList',
                            meta: {
                                title: '订单列表',
                            },
                            component: () => import('../views/petViews/orders/orderList.vue'),
                        },
                        {
                            path: 'orderDetail/:id',
                            name: 'orderDetail',
                            meta: {
                                title: '订单详情',
                            },
                            component: () => import('../views/petViews/orders/orderDetail.vue'),
                        },
                        {
                            path: 'orderSelect',
                            name: 'orderSelect',
                            meta: {
                                title: '订单搜索',
                            },
                            component: () => import('../views/petViews/orders/orderSelect.vue'),
                        }
                    ]
                },
                {
                    path: 'userinfo',
                    name: 'userinfo',
                    meta: {
                        title: '用户中心',
                    },
                    component: () => import('../views/petViews/UserInfo.vue'),
                },
                {
                    path: '/login',
                    name: 'login',
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
            name: 'loginInfo',
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
            path: '/petsProfile',
            name: 'petsProfile',
            meta: {
                title: '宠物信息',
            },
            component: () => import('../views/petsProfile/PetsProfileView.vue')
        },
        {
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
        //     主要业务路由视图(预约--->医疗服务/寄养服务;
        //     医疗服务--->疫苗接种/疾病诊疗
        //     疫苗接种--->医生--->疫苗服务--->疫苗详情--->立即预约--->选择宠物--->下单--->支付--->成功/失败
        //
        //
        //
        //     )
        {
            path: '/medical',
            name: 'medical',
            meta: {
                title: '医疗',
            },
            component: () => import('../views/petViews/bookings/medical/MedicalHomeView.vue'),
        },
        {
            path: '/veterinarian/:roleSpecialty',
            name: 'veterinarian',
            meta: {
                title: '预约医生',
            },
            component: () => import('../views/petViews/bookings/VeterinarianView.vue'),
        },
        {
            path: '/vaccine/:vetId',
            name: 'vaccine',
            meta: {
                title: '疫苗服务',
            },
            component: () => import('../views/petViews/bookings/medical/vaccineHome/VaccineHomeView.vue'),
        },
        {
            path: '/vaccineInfo/:vetId/:serviceId',
            name: 'vaccineInfo',
            meta: {
                title: '疫苗服务详情',
            },
            component: () => import('../views/petViews/bookings/medical/vaccineHome/vaccineInfoView.vue'),
        },
        {
            path: '/bookingNow/:vetId/:serviceId',
            name: 'bookingNow',
            meta: {
                title: '预约信息',
            },
            component: () => import('../views/petViews/bookings/bookingNowView.vue'),
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
    // 导航路由
    '/home',
    '/booking',
    '/consultation',
    '/orders',
    // 服务相关路由
    '/medical',
    '/veterinarian/:roleSpecialty',
    '/vaccine/:vetId',
    '/vaccineInfo/:vetId/:serviceId',
    '/', // 根路径
    '/404',
    '/:pathMatch(.*)*'
]

router.beforeEach((to, from, next) => {
    document.title = to.meta.title || '宠物管家系统'


    // 检查是否在白名单中（支持路由参数）
    const isWhitelisted = whiteList.some(path => {
        // 处理带参数的路由匹配
        if (path.includes('/:')) {
            const basePath = path.split('/:')[0];
            return to.path.startsWith(basePath);
        }
        return path === to.path || path === '/';
    });

    // 如果访问的页面在白名单中，直接放行
    if (isWhitelisted) {
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
