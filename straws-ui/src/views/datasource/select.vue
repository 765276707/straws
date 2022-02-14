<template>
    <el-select v-model="selectValue" 
                :filterable="filterable" 
                :clearable="clearable" 
                :placeholder="placeholder"
                :multiple="multiple">
        <!--选项属性-->        
        <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
    </el-select>
</template>

<script>
export default {
    name: 'DatasourceSelect',
    props: {
        value: {
            type: Number
        },
        placeholder: {
            type: String,
            default: '请选择数据源'
        },
        filterable: {
            type: Boolean,
            default: true
        },
        clearable: {
            type: Boolean,
            default: true 
        },
        multiple: {
            type: Boolean,
            default: false 
        }
    },
    data() {
        return {
            options: []
        }
    },
    computed: {
        selectValue: {
            get: function() {
                return this.value
            },
            set: function(val) {
                // 通知父组件更新绑定值
                this.$emit('input', val)
            }
        },

    },
    created() {
        this.getList();
    },
    methods: {
        /**
         * @title 加载数据
         */
        async getList() {
            let response = await this.$API.datasource.getList('');
            this.options = response.data
        }
    }
}
</script>

<style>

</style>