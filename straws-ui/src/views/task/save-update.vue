<template>
    <!--
        新增或编辑表单
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        v-if="visible"
        width="800px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>
        <el-form  ref="Form" :model="form" :rules="rule" label-width="120px">
            <!--隐藏id字段-->
            <el-row>
                <!--隐藏id字段-->
                <el-col :span="24" v-show="false">
                    <el-form-item prop="id" v-show="false">
                        <el-input v-model="form.id"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="任务名" prop="name">
                        <el-input v-model="form.name" placeholder="请输入任务名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="任务分组" prop="groupName">
                        <el-input v-model="form.groupName" placeholder="请输入任务分组" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="来源数据源" prop="originDsId">
                        <ds-select v-model="form.originDsId" :placeholder="'请选择来源数据源'" style="width: 100%"></ds-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="来源表" prop="originTableName">
                        <el-input v-model="form.originTableName" :placeholder="'请输入来源表'" style="width: 100%"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="目标数据源" prop="targetDsId">
                        <ds-select v-model="form.targetDsId" :placeholder="'请选择目标数据源'" style="width: 100%"></ds-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="目标表" prop="targetTableName">
                        <el-input v-model="form.targetTableName" :placeholder="'请输入目标表'" style="width: 100%"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="同步列名" prop="columns">
                        <el-input v-model="form.columns" placeholder="请输入同步列名，以逗号分隔" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="同步模式" prop="syncMode">
                        <el-radio-group v-model="form.syncMode">
                            <el-radio-button label="1">全量</el-radio-button>
                            <el-radio-button label="2">增量</el-radio-button>
                            <el-radio-button label="3">CDC</el-radio-button>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24" v-if="form.syncMode!=3">
                    <el-form-item label="数据转换器" prop="transformKeys">
                        <script-picker v-model="form.transformKeys"></script-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="CRON" prop="timeCron">
                        <el-cron-picker v-model="form.timeCron"></el-cron-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="运行状态" prop="runStatus">
                        <el-radio-group v-model="form.runStatus">
                            <el-radio :label="true">运行</el-radio>
                            <el-radio :label="false">就绪</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="切分列列名" prop="splitPk">
                        <el-input v-model="form.splitPk" placeholder="请输入切分列列名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="切分列类型" prop="splitPkType">
                        <el-radio-group v-model="form.splitPkType">
                            <el-radio-button label="1">INT</el-radio-button>
                            <el-radio-button label="2">LONG</el-radio-button>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Chunk数" prop="workersPerGroup">
                        <el-input-number v-model="form.workersPerGroup"  :min="1" :max="5" ></el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="限速阈值" prop="bytesPerSecond">
                        <el-input v-model="form.bytesPerSecond" placeholder="请输入限速阈值" style="width: 100%">
                            <template slot="append">KB</template>
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="FetchSize" prop="selectFetchSize">
                        <el-input v-model="form.selectFetchSize" placeholder="请输入FetchSize" style="width: 100%">
                            <template slot="append">条</template>
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="BatchSize" prop="insertBatchSize">
                        <el-input v-model="form.insertBatchSize" placeholder="请输入BatchSize" style="width: 100%">
                             <template slot="append">条</template>
                        </el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submit()">确 定</el-button>
            <el-button @click="close()">关 闭</el-button>
        </div>
    </el-dialog>
</template>

<script>
import DsSelect from '../datasource/select.vue'
import ElCronPicker from '@/views/common/el-cron-picker.vue'
import ScriptPicker from '../scripts/script-picker.vue'

export default {
    data() {
        return {
            title: '',
            visible: false,
            form: {},
            rule: {}
        }
    },
    components: {
        DsSelect, ElCronPicker, ScriptPicker
    },
    methods: {
        /**
         * @method 提交表单
         */
        async submit() {
            let response = null;
            if (!this.form.id) {
                // 新增
                response = await this.$API.task.insertEntity(this.form)
            } else {
                // 更新
                response = await this.$API.task.updateEntity(this.form)
            }
            this.$emit('success', this.form.id)
            this.close()
            this.$message.success(response.message)
        },

        /**
         * 打开表单
         */
        open(row) {
            this.visible = true
            if (row.id) {
                this.title = '编辑任务'
                
            } else {
                this.title = '新增任务'
            }
            // 赋值
            console.log(row)
            this.$nextTick(() => {
                // this.form = Object.assign({}, row, {'transformKeys': 'FemaleTransform,FemaleTransform2'}) 
                this.form = Object.assign({}, row) 
            })
        },

        /**
         * @method 关闭表单
         */
        close() {
            this.$refs.Form.resetFields()
            this.$refs.Form.clearValidate()
            this.visible = false
        }
    }
}
</script>

<style>

</style>