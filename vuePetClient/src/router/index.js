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

                // 预约
                {
                    path: '/bookingHome',
                    name: 'bookingHome',
                    component: () => import('../views/petViews/bookingHome.vue'),
                    children: [
                        // 服务分类(寄养和医疗)
                        {
                            path: '',
                            name: 'ServiceTypeItem',
                            meta: {
                                title: '预约',
                            },
                            component: () => import('../views/petViews/booking/bookingServiceTypeItem.vue'),
                        },
                        // 寄养服务列表页
                        {
                            path: '/fosterServiceList',
                            name: 'fosterServiceList',
                            meta: {
                                title: '寄养服务',
                            },
                            component: () => import('../views/petViews/booking/fosterServiceList.vue'),
                        },
                        //     服务详细信息页
                        {
                            path: '/serviceDetail/:id',
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
                            component: () => import('@/views/petViews/booking/fosterServiceBooking.vue'),

                        },
                        //      医疗服务分类
                        {
                            path: '/medicalServiceTypeItem',
                            name: 'medicalServiceTypeItem',
                            meta: {
                                title: '医疗服务',
                            },
                            component: () => import('../views/petViews/booking/medicalServiceTypeItem.vue'),
                        },
                        // 预约医生
                        {
                            path: '/vetList/:specialty',
                            name: 'vetList',
                            meta: {
                                title: '预约医生',
                            },
                            component: () => import('../views/petViews/booking/vetList.vue'),
                        },
                    ]
                },

                // 咨询
                {
                    path: 'consultationHome',
                    name: 'consultationHome',
                    meta: {
                        title: '咨询',
                    },
                    component: () => import('../views/petViews/consultationHome.vue'),
                },

                // 订单
                {
                    path: '/ordersHome',
                    name: 'ordersHome',
                    meta: {
                        title: '订单',
                    },
                    component: () => import('../views/petViews/ordersHome.vue'),
                    children: [
                        {
                            path: '',
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
                    path: 'userCenterHome',
                    name: 'userCenterHome',
                    meta: {
                        title: '用户中心',
                    },
                    component: () => import('../views/petViews/userCenterHome.vue'),
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

        //     预约医生下单页
        {
            path: '/bookingsVetOrder/:id',
            name: 'bookingsVetOrder',
            meta: {
                title: '预约信息',
            },
            component: () => import('../views/petViews/booking/vetServiceBooking.vue'),
        },


    ],
    // 滚动行为配置
    scrollBehavior(to, from, savedPosition) {
        // 如果有保存的位置（比如浏览器前进/后退），则返回保存的位置
        if (savedPosition) {
            return savedPosition
        }
        // 否则滚动到顶部
        return { top: 0 }
    }
})


// 定义需要登录验证的页面黑名单
const authRequiredList = [
    '/ordersHome',
    '/userCenterHome',
    '/ordersHome',
    '/userProfile',
]



router.beforeEach((to, from, next) => {
    document.title = to.meta.title || '宠物管家系统'

    // 检查是否在需要验证的路由中
    const isAuthRequired = authRequiredList.some(path => {
        // 处理带参数的路由匹配
        if (path.includes('/:')) {
            const basePath = path.split('/:')[0];
            return to.path.startsWith(basePath);
        }
        return path === to.path;
    });

    // 如果访问的页面需要验证
    if (isAuthRequired) {
        // 获取当前token
        const token = localStorage.getItem('token')

        // 检查是否已登录
        if (!token) {
            // 记录用户原本想访问的页面
            next({
                path: '/login',
                query: {redirect: to.fullPath} // 将原路径作为查询参数传递
            })
            return;
        }
    }

    // 不需要验证或已登录，直接放行
    next()
})



export default router
