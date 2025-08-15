export default {
    plugins: {
        'postcss-pxtorem': {
            rootValue: 37.5, // 设计稿宽度/10，如375设计稿就是37.5
            propList: ['*'],
            selectorBlackList: ['.norem'] // 过滤掉.norem-开头的class，不进行rem转换
        }
    }
}