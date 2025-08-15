import {defineStore} from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        refreshFlag: false,
        userRole: null,
        userAvatar: null,
    }),

    getters: {},
    actions: {
        triggerRefresh() {
            this.refreshFlag = !this.refreshFlag
        },
        getUserRole() {
            this.userRole = JSON.parse(localStorage.getItem('petSysUser')).role
            return this.userRole
        },
        getUserAvatar(userAvatar) {
            this.userAvatar = userAvatar
            return this.userAvatar
        },

    }
})