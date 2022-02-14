<template>
    <div>
        <el-card shadow="hover" class="app-card" :body-style="{ padding: '0px' }">
            <!--图标 和 标题-->
            <el-row type="flex" class="app-info">
                <div class="app-img">
                    <el-avatar shape="square" :size="30" :src="imgSrc"></el-avatar>
                </div>
                <div class="app-title"><h4>Alipay</h4></div>
            </el-row>
            
            <!--数据展示-->
            <el-row class="app-data" >
                <div class="data-term first">
                    <p class="term-name">活跃用户</p>
                    <p>17万</p>        
                </div>
                <div class="data-term next">
                    <p class="term-name">新增用户</p>
                    <p>1260</p>        
                </div>
            </el-row>
            
            <!--操作-->
            <el-divider></el-divider>
            <el-row type="flex" justify="space-around" align="middle" class="app-tool">
                <el-tooltip class="item" effect="dark" content="下载" placement="top">
                    <el-button type="text" icon="el-icon-download" class="first-button"></el-button>
                </el-tooltip>
                <el-divider direction="vertical"></el-divider>
                <el-tooltip class="item" effect="dark" content="编辑" placement="top">
                    <el-button type="text" icon="el-icon-edit-outline"></el-button>
                </el-tooltip>
                <el-divider direction="vertical"></el-divider>
                <el-tooltip class="item" effect="dark" content="分享" placement="top">
                    <el-button type="text" icon="el-icon-share"></el-button>
                </el-tooltip>
                <el-divider direction="vertical"></el-divider>
                <el-tooltip class="item" effect="dark" content="删除" placement="top">
                    <el-button type="text" icon="el-icon-delete" class="last-button"></el-button>
                </el-tooltip>
            </el-row>
        </el-card>
      
    </div>
</template>

<script>
export default {
    name: 'DatasouceCard',
    props: {
        imgSrc: {
            type: String,
            default: 'https://tse1-mm.cn.bing.net/th/id/R-C.73f7046014d8de16e6b5e953e8e7e2f4?rik=C%2f%2b1XwfA4nWz8w&riu=http%3a%2f%2fwww.icosky.com%2ficon%2fpng%2fApplication%2fisabi%2fCocoaMySQL.png&ehk=tIPdOsCvKjQK8Wxx1KuCcqWiy3OKuKpbN%2fk3SGcLQFI%3d&risl=&pid=ImgRaw&r=0'
        }
    }
}
</script>

<style lang="scss" scoped>
.app-card {
    // width: 280px; 
    // height: 180px; 
    width: 23%; 
    height: 180px; 
    min-width: 220px;
    float: left; 
    margin: 10px; 

    .app-info {
        height: 50px;

        .app-img {
            padding-left: 30px;
            margin-top: 20px;
        }

        .app-title {
            margin-top: 10px;
            padding-left: 20px;
            overflow: hidden;
            color: rgba(0,0,0,.85);
            font-weight: 500;
            font-size: 16px;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
    }

    .app-data {
        height: 80px;

        .data-term {
            float: left; 

            &.first {
                padding-left: 80px;
            }
            
            &.next {
                padding-left: 20px;
            }

            .term-name {
                font-size: 12px; 
                color: gray;
            }
        }
    }


    .app-tool {
        margin-top: -5px;

        .el-button {
            font-size:20px;
        }

        .first-button {
            margin-left: 15px;
        }

        .last-button {
            margin-right: 15px;
        }
    }

    .el-divider--horizontal {
        margin: 4px 0;
        border: 10px;
    }
}



</style>