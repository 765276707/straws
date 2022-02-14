package com.gitee.pristine.core.chunk.conf;

import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.split.SplitPkType;

/**
 * Chunk 内所需要的配置
 * @author Pristine Xu
 * @date 2022/1/19 0:01
 * @description:
 */
public class ChunkConfig {

    private String splitPkName;

    private Integer splitPkType;

    private String columns;

    private ChunkConfig() {}

    public ChunkConfig(String splitPkName, Integer splitPkType, String columns) {
        this.splitPkName = splitPkName;
        this.splitPkType = splitPkType;
        this.columns = columns;
    }

    /**
     * 获取主键在查询列的下标
     * @return
     */
    public int getSplitPkIdxInColumns() {
        int idx = -1;
        String[] cols = this.columns.split(",");
        for (int i = 0; i < cols.length; i++) {
            if (this.splitPkName!=null
                    && this.splitPkName.trim().equals(cols[i].trim())) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * 获取分割主键的类型
     * @return
     */
    public SplitPkType getSplitPkType() {
        if (this.splitPkType == null) {
            throw new IllegalArgumentException("SplitPkType value in ChunkConfig can not be null.");
        }
        if (this.splitPkType.equals(SplitPkType.INT.getType())) {
            return SplitPkType.INT;
        }
        else if (this.splitPkType.equals(SplitPkType.LONG.getType())) {
            return SplitPkType.LONG;
        }
        else {
            throw new IllegalArgumentException("SplitPkType value in ChunkConfig is invalid.");
        }
    }

}
