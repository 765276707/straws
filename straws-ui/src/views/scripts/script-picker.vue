<template>
    <div>
        <el-input v-model="scripts" placeholder="请选择数据转换器">
            <el-button slot="append" icon="el-icon-set-up" @click="openDialog"></el-button>
        </el-input>

        <el-dialog
            title="数据转换器"
            :visible.sync="visible"
            v-if="visible"
            width="500px"
            :append-to-body="true"
            :show-close="false"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
        >   
            <el-form :model="form" ref="form" label-width="100px">
                <el-form-item
                    v-for="(domain, index) in form.domains"
                    :label="'转换器' + index"
                    :key="domain.key"
                    :prop="'domains.' + index + '.value'"
                >
                    <script-select v-model="domain.value" style="width: 280px;"></script-select>
                    <el-button type="danger" icon="el-icon-delete" circle @click.prevent="removeDomain(domain)"></el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" icon="el-icon-plus" @click="addDomain">转换器</el-button>
                    <el-button type="primary" @click="submit()">确定</el-button>
                    <el-button @click="resetForm('form')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import ScriptSelect from './script-select.vue'

export default {
    name: 'ScriptPicker',
    components: {
        ScriptSelect
    },
    props: {
        value: {
            type: String
        }
    },
    data() {
        return {
            visible: false,
            form: {
                domains: [],
            }
        }
    },
    computed: {
        scripts: {
            get: function() {
                return this.value
            },
            set: function(val) {
                // 通知父组件更新绑定值
                this.$emit('input', val)
            }
        }
    },
    methods: {
        /**
         * 打开对话框
         */
        openDialog() {
            if (this.value!=null && this.value != '') {
                this.form.domains = []
                let arr = this.value.split(',')
                arr.forEach(item => {
                    this.form.domains.push({
                        value: item,
                        key: Date.now() +  Math.round(Math.random()*6)
                    });
                })
            }
            
            this.visible = true
        },

        

        submit() {            
            let temp = ''
            let arr = this.form.domains
            arr.forEach(item => {
                temp = temp + item.value + ','
            })
            if (temp.endsWith(',')) {
                temp = temp.substr(0, temp.length-1)
            }
            this.$emit('input', temp)
            // this.scripts = temp
            this.visible = false
        },

        resetForm(formName) {
            this.$refs[formName].resetFields();
             this.visible = false
        },

        removeDomain(item) {
            var index = this.form.domains.indexOf(item)
            if (index !== -1) {
            this.form.domains.splice(index, 1)
            }
        },

        addDomain() {
            this.form.domains.push({
                value: '',
                key: Date.now()
            });
        }
    }
}
</script>

<style>

</style>