import {defineStore} from "pinia";
import request from "@/utils/request.js";

const bookingStore = defineStore('booking', {
    state: () => {
        return {
            VaccineData: null,//疫苗数据
            VaccineDataInfo: null,//疫苗详细数据
            serviceDataItemInfo: null,//服务详细数据


            // 用于创建订单
            currentEmployee: null,//当前员工
            currentPet: null,//当前宠物
            currentServiceItem: null,//当前服务

            orderData: {//预约订单数据
                employeeId: null,//员工id
                serviceItemId: null,//服务id
                petId: null,//宠物id
            }


        }
    },
    getters: {
        getVaccineData: (state) => {
            return state.VaccineData;
        },
        getVaccineDataInfo: (state) => {
            return state.VaccineDataInfo;
        },
        getServiceDataItemInfo: (state) => {
            return state.serviceDataItemInfo;
        },
        getOrderData: (state) => {
            sessionStorage.setItem('orderData', JSON.stringify(state.orderData));
            return state.orderData;
        },

    },
    actions: {
        // 获取疫苗服务数据
        async setVaccineData() {
            await request.get('/serviceItems/getAllServiceItem', {
                params: {
                    medicalType: 'vacation'
                }
            }).then(res => {
                if (res.code === 200) {
                    console.log(res.data[0])
                    this.VaccineData = res.data
                }
            }).catch(error => {
                console.error(error);
            });
        },

        //     根据服务id获取对应的服务详细数据
        async getServiceItemById(id) {
            await request.get('/serviceItems/getServiceItemById', {
                params: {
                    id: id
                }
            }).then(res => {
                if (res.code === 200) {
                    console.log(res.data)
                    this.VaccineDataInfo = res.data
                    this.serviceDataItemInfo = res.data
                }
            }).catch(error => {
                console.error(error);
            });
        },

        setCurrentEmployee(employee) {
            console.log("当前员工:" + employee.username)
            // 只保存基本信息到 sessionStorage
            this.orderData.employeeId = employee.id;
            sessionStorage.setItem('orderData', JSON.stringify(this.orderData));
        },

        setCurrentServiceItem(serviceItem) {
            console.log("当前服务:" + serviceItem.serviceName)
            // 只保存基本信息到 sessionStorage
            this.orderData.serviceItemId = serviceItem.id;
            sessionStorage.setItem('orderData', JSON.stringify(this.orderData));
        },

        setCurrentPet(pet) {
            // 只保存基本信息到 sessionStorage
            this.orderData.petId = pet.id;
            sessionStorage.setItem('orderData', JSON.stringify(this.orderData));
        },
    },
})

export default bookingStore;