package com.gitee.pristine.core.chunk.impl;

import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.loader.Loader;
import com.gitee.pristine.spi.statis.Statistics;
import com.gitee.pristine.spi.transformer.Transformer;

import java.util.List;

/**
 * IncrChunk 的逻辑与 FullChunk 相同
 * @author Pristine Xu
 * @date 2022/1/19 1:25
 * @description:
 */
public class IncrChunk extends FullChunk {

    public IncrChunk(Extractor extractor, Loader loader, List<Transformer> transformers, Statistics statistics, SplitPkRange splitPkRange) {
        super(extractor, loader, transformers, statistics, splitPkRange);
    }

}
