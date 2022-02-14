package com.gitee.pristine.spi.spliter;

import com.gitee.pristine.datasource.info.TableInfo;
import com.gitee.pristine.datasource.split.SplitPk;
import com.gitee.pristine.datasource.split.SplitPkRange;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务切割器
 * @author Pristine Xu
 * @date 2022/1/13 5:29
 * @description: 将任务切分成多个 Chunk 所需要的数据抽取范围
 */
public class PkSpliter {

    // 目标表详情
    private TableInfo tableInfo;

    // 需要切分的块
    private Integer chunkCount;

    private PkSpliter() {}

    private PkSpliter(TableInfo tableInfo, Integer chunkCount) {
        this.tableInfo = tableInfo;
        this.chunkCount = chunkCount;
    }

    public static PkSpliter config(TableInfo tableInfo, Integer chunkCount) {
        // 默认 chunkCount 为 1
        chunkCount = chunkCount==null?1:chunkCount;
        return new PkSpliter(tableInfo, chunkCount);
    }

    public static PkSpliter config(TableInfo tableInfo) {
        return new PkSpliter(tableInfo, 1);
    }


    /**
     * 切分SplitPk，左开右闭
     * @return 1 <= id < 99
     */
    public List<SplitPkRange> doSplit() {
        List<SplitPkRange> ranges = new ArrayList<>(chunkCount);
        SplitPk startPk = tableInfo.getStart();
        SplitPk finishPk = tableInfo.getFinish();

        // 容量小于指定数据容量
        if (chunkCount == 1) {
            ranges.add(SplitPkRange.builder()
                    .startWith(startPk)
                    .finishWith(finishPk.plus(1)));
        }
        else
        {
            int chunkCapacity = tableInfo.getTotal() / chunkCount;
            // 取商
            for (int i = 0; i < chunkCount; i++) {
                if (i == chunkCount-1) {
                    ranges.add(
                            SplitPkRange.builder()
                                .startWith(SplitPk.copy(startPk).plus(i*chunkCapacity))
                                .finishWith(finishPk.plus(1))
                    );
                } else {
                    ranges.add(
                            SplitPkRange.builder()
                                .startWith(SplitPk.copy(startPk).plus(i*chunkCapacity))
                                .finishWith(SplitPk.copy(startPk).plus((i+1)*chunkCapacity))
                    );
                }
            }
        }
        return ranges;
    }
}
