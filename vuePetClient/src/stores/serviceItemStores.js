// stores/serviceItemStores.js
import {defineStore} from "pinia"
import request from "@/utils/request.js"

export const serviceItemStores = defineStore('serviceItem', {
    state: () => ({
        serviceDetailData: null
    }),

    getters: {

        getServiceDetail: (state) => {
            return state.serviceDetailData
        }
    },

    actions: {
        async fetchServiceDetailData(serviceId) {
            try {
                const response = await request.get('/serviceItems/getServiceItemById', {
                    params: {
                        id: serviceId
                    }
                })

                if (response.code === 200) {
                    console.log(response.data)
                    this.serviceDetailData = response.data
                    return response.data
                }
            } catch (error) {
                console.error(error)
                throw error
            }
        },

        // 清除数据
        clearServiceDetail() {
            this.serviceDetailData = null
        }
    }
})

export default serviceItemStores
