import axios from "axios";
import { ElMessage } from "element-plus";

// 创建一个 axios 实例，配置基础 URL 和请求超时时间
const request = axios.create({
    baseURL: 'http://localhost:8083',
    timeout: 3000
});

// 请求拦截器：在请求发送之前做些什么
request.interceptors.request.use(
    config => {
        // 设置请求头，指定内容类型
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        return config;
    },
    error => {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

// 响应拦截器：对响应数据做些什么
request.interceptors.response.use(
    response => {
        let res = response.data;

        // 处理 null 或 undefined 的情况
        if (res === null || res === undefined) {
            return res;
        }

        // 判断是否为 JSON 类型响应
        const contentType = response.headers['content-type'];
        if (contentType && contentType.includes('application/json')) {
            // 如果是字符串类型，尝试解析 JSON
            if (typeof res === 'string') {
                res = res.trim(); // 去除首尾空白字符，避免 JSON 解析失败
                if (res) {
                    try {
                        res = JSON.parse(res);
                    } catch (e) {
                        console.error("JSON解析失败", e);
                    }
                }
            }
        }

        return res;
    },
    error => {
        // ✅ 安全访问 error.response
        if (error.response) {
            // 请求已发出，服务器响应了非2xx状态码
            switch (error.response.status) {
                case 404:
                    ElMessage.error('404 未找到请求接口');
                    break;
                case 500:
                    ElMessage.error('500 服务器内部错误');
                    break;
                default:
                    ElMessage.error(`请求失败：${error.response.status}`);
            }
        } else if (error.request) {
            // 请求已发出但未收到响应（如后端服务未启动）
            ElMessage.error('网络异常，请检查后端服务是否启动');
        } else {
            // 其他错误（如设置请求时出错）
            console.error('请求错误:', error.message);
            ElMessage.error('请求异常');
        }

        return Promise.reject(error);
    }
);

// 导出配置好的 axios 实例
export default request;
