package com.gitee.pristine.web.initialization;

import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.web.service.SysTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务初始化
 * @author Pristine Xu
 * @date 2022/1/19 9:54
 * @description: 在系统启动的时候加载已经存在在数据库中的任务进行初始化
 */
@Component
public class InitializeTaskRunner implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(InitializeTaskRunner.class);

    @Resource
    private SysTaskService sysTaskService;

    @Override
    public void run(String... args) throws Exception {
        // 查询所有需要进行触发的定时任务
        List<Task> tasks = sysTaskService.getAll();
        // 启动任务
        for (Task task : tasks) {
            sysTaskService.createJob(task);
        }
        // 打印日志
        log.info("[Straws] Successfully loaded {} tasks", tasks.size());
    }

}
