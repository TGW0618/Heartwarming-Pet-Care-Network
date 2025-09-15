import {defineStore} from "pinia";
import {ref} from "vue";

export const useStatisticsStore = defineStore('statistics', () => {
    const statistics = ref([])
    return {statistics}
})

export default useStatisticsStore