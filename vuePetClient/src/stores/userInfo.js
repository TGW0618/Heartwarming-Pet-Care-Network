// userInfo.js
import {defineStore} from "pinia";
import request from "@/utils/request.js";

const useUserInfoStore = defineStore('userInfo', {
    state: () => {
        return {
            token: localStorage.getItem('token') || null,
            userInfo: null,
        }
    },
    getters: {
        getToken: (state) => {
            return state.token || localStorage.getItem('token') || null;
        },
        getUserInfo: (state) => {
            return state.userInfo;
        }
    },
    actions: {
        setUserInfo(userInfo) {
            // 添加空值检查
            // if (!userInfo) {
            //     console.warn('传入的用户信息为空');
            //     return;
            // }

            this.userInfo = userInfo;
            // 只存储非敏感信息到本地存储
            const safeUserInfo = {
                id: userInfo.id || null,
                username: userInfo.username || '',
                realName: userInfo.realName || '',
                avatar: userInfo.avatar || '',
                phone: userInfo.phone || '',
                email: userInfo.email || '',
                sex: userInfo.sex || '',
                role: userInfo.role || ''
            };
            localStorage.setItem('user-info', JSON.stringify(safeUserInfo));
        },

        setToken(token) {
            this.token = token;
            localStorage.setItem('token', token);
        },
        // 清除信息
        clearUserInfo() {
            this.userInfo = null;
            this.token = null;
            localStorage.removeItem('user-info');
            localStorage.removeItem('token');
        },
        // 获取当前用户信息
        async fetchUserInfo() {
            try {
                const response = await request.get('/ClientUserInfo/getUserById');
                if (response.code === 200) {
                    this.setUserInfo(response.data);
                    return response.data;
                } else {
                    throw new Error(response.message || '获取用户信息失败');
                }
            } catch (error) {
                console.error('获取用户信息失败:', error);
                throw error;
            }
        },
        // 初始化时从localStorage读取数据
        initializeStore() {
            this.token = localStorage.getItem('token') || null;
            try {
                const userInfoStr = localStorage.getItem('user-info');
                this.userInfo = userInfoStr ? JSON.parse(userInfoStr) : null;
            } catch (error) {
                this.userInfo = null;
                // 清除损坏的数据
                localStorage.removeItem('user-info');
            }
        },
        //     更新用户数据
        updateUserInfo(dataFrom) {
            // 更新store和localStorage
            this.userInfo = dataFrom;
            localStorage.setItem('user-info', JSON.stringify(dataFrom));
            request.put('/sysUser/updateSysUsers', dataFrom).then(res => {
            }).catch(err => {
                console.log(err);

            })
        }
    }
})

export default useUserInfoStore;
