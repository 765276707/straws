<template>
    <div>
        <el-input v-model="jobCron" placeholder="请输入定时规则">
            <el-button slot="append" icon="el-icon-timer"  @click="openDialog"></el-button>
        </el-input>

        <el-dialog
            title="CRON生成器"
            :visible.sync="visible"
            v-if="visible"
            :append-to-body="true"
            :show-close="false"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
        >   
            <cron-picker @change="changeCron" @close="visible=false"></cron-picker>
        </el-dialog>
    </div>
</template>

<script>
import CronPicker from '@/components/CronPicker'

export default {
    name: 'ElCronPicker',
    components: {
        CronPicker
    },
    props: {
        value: {
            type: String
        }
    },
    data() {
        return {
            visible: false,
        }
    },
    computed: {
        jobCron: {
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
            this.visible = true
        },

        /**
         * cron表达式选取器
         */
        changeCron(val) {
            this.jobCron = val
        },

        /**
         * 触发表单的jobCron字段
         */
        blurCron() {
            if (this.jobCron != null) {
                // 移除该字段的校验结果
                this.$refs['jobDrawer.data'].clearValidate(['jobCron'])
            }
        }
    }
}
</script>

<style>

</style>