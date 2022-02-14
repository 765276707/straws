<template>
    <!--
        查看脚本
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

            <codemirror v-model="script" :options="editorOptions"/>
            <div slot="footer" class="dialog-footer">
                <el-button @click="close()">关 闭</el-button>
            </div>

    </el-dialog>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import "codemirror/theme/material.css";
require("codemirror/mode/groovy/groovy")

export default {
    data() {
        return {
            title: '查看脚本',
            visible: false,
            script: '',
            editorOptions: {
                tabSize: 4,
                mode: 'text/x-groovy',
                theme: 'material',
                lineNumbers: true,
                line: true
            }
        }
    },
    components: {
        codemirror
    },
    methods: {
        /**
         * 打开
         */
        open(script) {
            this.visible = true
            this.script = script
        },

        /**
         * 关闭
         */
        close() {
            this.visible = false
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
