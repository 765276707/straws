<template>
    <el-select v-model="selectValue" 
                :filterable="filterable" 
                :clearable="clearable" 
                :placeholder="placeholder"
                :multiple="multiple">
        <!--选项属性-->        
        <el-option
            v-for="item in options"
            :key="item.name"
            :label="item.name"
            :value="item.name">
            
            <span style="float: left">{{ item.name }}</span>

        </el-option>
    </el-select>
</template>

<script>
export default {
    name: 'ScriptSelect',
    props: {
        value: {
            type: String
        },
        placeholder: {
            type: String,
            default: '请选择脚本'
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
            let response = await this.$API.scripts.getList('');
            this.options = response.data
        }
    }
}
</script>

<style>

</style>