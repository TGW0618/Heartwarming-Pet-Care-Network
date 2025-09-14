// utils/request.js
import axios from "axios";
import {showFailToast} from "vant";
import router from "@/router/index.js";

// 根据访问方式动态确定后端地址
const getBaseURL = () => {
    // 获取当前页面的协议和主机名
    const protocol = window.location.protocol;
    const hostname = window.location.hostname;

    // 如果是本地访问 (localhost 或 127.0.0.1)
    if (hostname === 'localhost' || hostname === '127.0.0.1') {
        return 'http://localhost:8083';
    } else {
        // 手机等外部设备访问，使用当前页面的主机名
        // 但端口仍指向后端端口 8083
        return `${protocol}//${hostname}:8083`;
    }
};

// 创建 axios 实例
const request = axios.create({
    baseURL: getBaseURL(),
    timeout: 15000 // 超时时间到15秒
});

// 请求拦截器
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';

        // 添加Token到请求头
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        console.log('发送请求到:', config.baseURL + config.url);
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// 响应拦截器
request.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        console.error('请求详细错误:', error);
        if (error.code === 'ERR_NETWORK') {
            showFailToast('网络连接失败，请确保后端服务正在运行');
        } else if (error.response) {
            // 对于401错误，只在特定情况下跳转登录页
            if (error.response.status === 401) {
                showFailToast('请先登录');
                // 清除本地token
                localStorage.removeItem('token');
                // 延迟跳转，避免干扰当前操作
                setTimeout(() => {
                    router.push('/login');
                }, 1000);
            } else {
                showFailToast(`服务器错误: ${error.response.status}`);
            }
        } else {
            showFailToast('请求失败: ' + (error.message || '未知错误'));
        }

        return Promise.reject(error);
    },
);

export default request;
