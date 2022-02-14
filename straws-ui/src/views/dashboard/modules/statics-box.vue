<template>
    <el-card>
        <div style="display: inline-block;">
            <i :class="icon" style="font-size: 80px;"></i>
        </div>
        <div class="statistic" style="display: inline-block;">
            <div class="statistic-title">
                {{ title }}
                <el-tooltip v-if="tips" effect="light">
                    <template #content>
                        <div style="width: 200px;line-height: 2;">
                            {{ tips }}
                        </div>
                    </template>
                    <el-icon class="statistic-tips"><el-icon-question-filled/></el-icon>
                </el-tooltip>
            </div>
            <div class="statistic-content">
                <span v-if="prefix" class="statistic-content-prefix">{{ prefix }}</span>
                <span class="statistic-content-value">{{ value }}</span>
                <span v-if="suffix" class="statistic-content-suffix">{{ suffix }}</span>
            </div>
            <div v-if="description || $slots.default" class="statistic-description">
                <slot>
                {{ description }}
                </slot>
            </div>
        </div>
    </el-card>
</template>

<script>
	export default {
		props: {
            icon: { type: String, required: true, default: "" },
			title: { type: String, required: true, default: "这是标题" },
			value: { type: String, required: true, default: "asmaosii" },
			prefix: { type: String, default: "" },
			suffix: { type: String, default: "" },
			description: { type: String, default: "" },
			tips: { type: String, default: "" }
		},
		data() {
			return {

			}
		}
	}
</script>

<style scoped>

.statistic-title {font-size: 12px;color: #999;margin-bottom: 10px;display: flex;align-items: center;}
.statistic-tips {margin-left: 5px;}
.statistic-content {font-size: 20px;color: #333;}
.statistic-content-value {font-weight: bold;}
.statistic-content-prefix {margin-right: 5px;}
.statistic-content-suffix {margin-left: 5px;font-size: 12px;}
.statistic-description {margin-top: 10px;color: #999;}

</style>