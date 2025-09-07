import {defineStore} from 'pinia'
import request from "@/Backend/utils/request.js"
import {ElMessage} from "element-plus"


export const useOrderStore = defineStore('order', {
    state: () => ({
        orderData: null,
    }),

    getters: {
        getOrderData() {
            return this.orderData
        },
    },

    actions: {
        /*
        * 获取订单数据
        * */
        async getOrderAll(currentPag = 1, pageSize = 5) {
            // 传递分页参数给后端
            await request.get(`/sysOrder/getOrderAll?pageNum=${currentPag}&pageSize=${pageSize}`).then(res => {
                if (res.code === 200) {
                    this.orderData = res.data
                    console.log(res.data)
                } else {
                    ElMessage.error(res.message)
                }
            }).catch(error => {
                console.error(error)
            })
        },

        /*
        * 修改订单
        * */
        async updateSysOrder(order) {
            request.put(`/sysOrder/updateSysOrder/${order.id}`, order).then(res => {
                if (res.code === 200) {
                    ElMessage.success(res.message)
                    window.location.reload()
                } else {
                    ElMessage.error(res.message)
                }
            }).catch(err => {
                ElMessage.error(err.message)
            })
        },

        // 删除订单
        async deleteOrder(id) {
            console.log("删除订单:" + id)
            await request.delete("/sysOrder/deleteSysOrder/" + id).then(res => {
                if (res.code === 200) {
                    ElMessage.success('删除成功')
                    window.location.reload()
                } else {
                    ElMessage.error('删除失败: ' + res.message)
                }
            })
        }
    }
})
