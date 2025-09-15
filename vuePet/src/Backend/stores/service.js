import {defineStore} from 'pinia'
import request from "@/Backend/utils/request.js"
import {ElMessage} from "element-plus"


export const useServiceStore = defineStore('service', {
    state: () => ({
        serviceData: null,
    }),

    getters: {
        getServiceData() {
            return this.serviceData
        },
    },

    actions: {
        //     获取所有服务项
        async getServiceItemAll(currentPag = 1, pageSize = 5) {
            await request.get(`/serviceItems/getServiceItemAll?pageNum=${currentPag}&pageSize=${pageSize}`)
                .then(res => {
                    if (res.code === 200) {
                        this.serviceData = res.data
                        console.log(res.data)
                    } else {
                        ElMessage.error(res.message)
                    }
                })
                .catch(err => {
                    ElMessage.error(err.message)
                })
        },

        //     删除服务项
        async deleteServiceItem(serviceItemId) {
            await request.delete(`/serviceItems/deleteServiceItem/${serviceItemId}`)
                .then(res => {
                    if (res.code === 200) {
                        console.log(res)
                        ElMessage.success(res.data)
                        this.getServiceItemAll()
                    } else {
                        ElMessage.error(2)
                    }
                })
                .catch(err => {
                    ElMessage.error(err.message)
                })
        },

    //     更新服务项
        async updateServiceItem(serviceItem) {
            await request.put(`/serviceItems/updateServiceItem`, serviceItem)
                .then(res => {
                    if (res.code === 200) {
                        console.log(res)
                        ElMessage.success(res.data)
                        this.getServiceItemAll()
                    } else {
                        ElMessage.error(res)
                    }
                })
                .catch(err => {
                    ElMessage.error(err)
                })
        },

    //     新增服务
        async addServiceItem(serviceItem) {
            await request.post(`/serviceItems/addServiceItem`, serviceItem)
                .then(res => {
                    if (res.code === 200) {
                        console.log(res)
                        ElMessage.success(res.data)
                        this.getServiceItemAll()
                    } else {
                        ElMessage.error(res)
                    }
                })
                .catch(err => {
                    ElMessage.error(err)
                })
        }

    }
})

export default useServiceStore
