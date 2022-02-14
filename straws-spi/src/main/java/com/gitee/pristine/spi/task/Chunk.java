package com.gitee.pristine.spi.task;

import com.gitee.pristine.spi.result.SyncResult;

import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;

/**
 * @author Pristine Xu
 * @date 2022/1/13 18:00
 * @description:
 */
public interface Chunk extends Callable<SyncResult>, Delayed {

    public SyncResult service() throws Exception;

}
