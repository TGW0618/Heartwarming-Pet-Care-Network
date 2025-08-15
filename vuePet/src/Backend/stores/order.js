import {defineStore} from 'pinia'
import request from "@/Backend/utils/request.js"
import {ElMessage, ElMessageBox} from "element-plus"


export const useOrderStore = defineStore('order', {
    state: () => ({
        allOrders: [],
        medicalOrders: [],
        fosterOrders: [],
        loading: false
    }),

    getters: {
        // 计算属性
        getOrderByNo: (state) => (orderNo) => {
            return state.allOrders.find(order => order.orderNo === orderNo)
        }
    },

    actions: {
        // 获取所有订单
        async fetchAllOrders() {
            this.loading = true
            try {
                const res = await request.get("/sysOrder/getAllSysOrder")
                if (res.code === 200) {
                    this.allOrders = res.data
                    this.medicalOrders = this.allOrders.filter(item => item.serviceType === "medical")
                    this.fosterOrders = this.allOrders.filter(item => item.serviceType === "foster")
                } else {
                    ElMessage.error('获取订单数据失败: ' + res.message)
                }
            } catch (error) {
                ElMessage.error('网络错误，请稍后重试')
                console.error(error)
            } finally {
                this.loading = false
            }
        },

        // 删除订单
        async deleteOrder(id) {
            console.log("删除订单:" + id)
            await ElMessageBox.confirm(
                '确定要删除此订单吗？此操作不可恢复！',
                '删除确认',
                {
                    confirmButtonText: '确定删除',
                    cancelButtonText: '取消',
                    type: 'warning',
                    confirmButtonClass: 'el-button--danger'
                }
            ).then(async () => {
                await request.delete("/sysOrder/deleteSysOrder/" + id).then(res => {
                    if (res.code === 200) {
                        ElMessage.success('删除成功')
                        this.fetchAllOrders()
                    } else {
                        ElMessage.error('删除失败: ' + res.message)
                    }
                })
            }).catch(() => {
                ElMessage.info('已取消删除')
            })
        }
    }
})
