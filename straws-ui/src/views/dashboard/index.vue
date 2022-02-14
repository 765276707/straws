<template>
  <div class="dashboard-container">
    <el-row type="flex" justify="space-between">
      <el-col :span="5" v-for="item in staticsList" :key="item.title">
        <statics-box :icon="item.icon" :title="item.title" :value="item.value" :prefix="item.prefix" :suffix="item.suffix" :description="item.desc"></statics-box>
      </el-col>
    </el-row>
    
    <el-row  style="padding-top: 10px;">
      <el-col :span="11" >
        <el-card style="min-height: 350px">
          <statics-oplog :oplogs="oplogs"></statics-oplog>
        </el-card>
      </el-col>
      <el-col :span="12" :offset="1">
        <el-card>
          <statics-server :globalEnv="globalEnv"></statics-server>
        </el-card>
      </el-col>
    </el-row>

        
    <el-row style="padding-top: 10px;">
      <el-card>
        <statics-task-log :list="taskLogs"></statics-task-log>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import StaticsBox from './modules/statics-box.vue'
import StaticsTaskLog from './modules/statics-task-log.vue'
import StaticsServer from './modules/statics-server.vue'
import StaticsOplog from './modules/statics-oplog.vue'

export default {
  name: 'Dashboard',
  components: {
    StaticsBox,
    StaticsTaskLog,
    StaticsServer,
    StaticsOplog
  },
  data() {
    return {
      timer: null,
      staticsList: [],
      taskLogs: [],
      globalEnv: {},
      oplogs: []
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  created() {
    this.getStaticsList()
    this.getOplogList()
    this.getLatestTaskLogs()
    this.getGlobalEnv()

    this.timer = setInterval(() => {
      this.getStaticsList()
      this.getOplogList()
      this.getLatestTaskLogs()
      this.getGlobalEnv()
    }, 10000)
  },
  destroyed() {
    // 离开页面时，销毁定时器
    clearInterval(this.timer)
  },
  methods: {
    async getLatestTaskLogs() {
      let response = await this.$API.statics.getLatestTaskLogs()   
      this.taskLogs = response.data
    },

    async getGlobalEnv() {
      let response = await this.$API.statics.getGlobalEnv()   
      this.globalEnv = response.data
    },

    async getStaticsList() {
      let response = await this.$API.statics.getStaticsList()   
      this.staticsList = response.data
    },

    async getOplogList() {
      let response = await this.$API.oplog.getList()   
      this.oplogs = response.data
    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }

}
</style>
