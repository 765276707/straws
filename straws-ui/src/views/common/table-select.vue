<template>
    <el-select v-model="selectValue" 
                :filterable="filterable" 
                :clearable="clearable" 
                :placeholder="placeholder"
                :multiple="multiple">
        <!--选项属性-->        
        <el-option
            v-for="item in options"
            :key="item.tableName"
            :label="item.tableName"
            :value="item.tableName">
            
        </el-option>
    </el-select>
</template>

<script>
export default {
    name: 'TableSelect',
    props: {
        value: {
            type: String
        },
        migrateId: {
            type: Number,
            required: true
        },
        placeholder: {
            type: String,
            default: ''
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
         * @title 根据数据源编号加载所有表名
         */
        async getList() {
            let response = await this.$API.common.getTables(this.migrateId);
            this.options = response.data
        }
    }
}
</script>

<style>

</style>