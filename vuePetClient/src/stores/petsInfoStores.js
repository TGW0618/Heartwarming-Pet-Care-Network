import {defineStore} from "pinia";
import request from "@/utils/request.js";
import {showToast} from "vant";

const petsInfoStores = defineStore('petsInfo', {
    state: () => {
        return {
            petsInfoData: [],
            petsInfoByIdData: null,
        }
    },
    getters: {
        getPetsInfo: (state) => {
            return state.petsInfoData
        },



    },
    actions: {
        //  根据用户id查询该用户的所有宠物信息
        async getPetsInfoData() {
            request.get('/petsInfo/getAllPetsInfo').then(res => {
                console.log(res)
                if (res.code === 200) {
                    this.petsInfoData = res.data
                    this.addOnButton = true
                }
            })
        },
        // 根据宠物ID获取特定宠物信息
        async getPetsInfoById(id) {
            await request.get("/petsInfo/getPetsInfoById", {
                params: {
                    id: id
                }
            }).then(res => {
                console.log(res)
                if (res.code === 200) {
                    this.petsInfoByIdData = res.data
                }else if (res.code === 404) {
                    showToast('暂无该宠物信息');
                }
            })
        },
        //     根据宠物id删除宠物信息
        async deletePetsInfoById(id) {
            await request.delete("/petsInfo/deletePetsInfoById", {
                params: {
                    id: id
                }
            }).then(res => {
                if (res.code === 200) {
                    showToast('已删除');
                }
            })
        },
        //     根据宠物id修改宠物信息
        async updatePetsInfoById(values) {
            console.log(values)
            await request.put("/petsInfo/updatePetsInfoById",values).then(res => {
                if (res.code === 200) {
                    showToast('已修改');
                }
            })
        },
        //     添加宠物信息
        async addPetsInfo(values) {
            await request.post("/petsInfo/addPetsInfo",values).then(res => {
                if (res.code === 200) {
                    showToast('已添加');
                }
            })
        },

    }
})

export default petsInfoStores;
