import {onBeforeUnmount, onMounted, ref, watch} from "vue";
import * as echarts from 'echarts'

const useChart = (options) => {
    const chartRef = ref(null);
    let chartInstance = null;

    // 获取实际的图表配置选项
    const getOptions = () => {
        return typeof options === 'function' ? options() : options;
    };

    const chartInit = () => {
        if (chartRef.value) {
            if (chartInstance) {
                chartInstance.dispose()
            }
            chartInstance = echarts.init(chartRef.value)
            chartInstance.setOption(getOptions())
        }
    }

    const chartResize = () => {
        if (chartInstance) {
            chartInstance.resize()
        }
    }

    const updateChart = () => {
        if (chartInstance) {
            chartInstance.setOption(getOptions(),true)
        }
    }

    // 如果 options 是函数，则监听其返回值的变化
    if (typeof options === 'function') {
        watch(options, updateChart, { deep: true });
    }

    onMounted(() => {
        chartInit()
        window.addEventListener('resize', chartResize)
    })

    onBeforeUnmount(() => {
        if (chartInstance) {
            chartInstance.dispose()
        }
        window.removeEventListener('resize', chartResize)
    })

    return {
        chartRef,
        chartInstance,
        chartInit,
        chartResize,
        updateChart
    }

}
export default useChart
