package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.TaskDTO;
import com.gitee.pristine.domain.entity.Task;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:30
 * @description:
 */
public interface SysTaskService {

    Task getById(Long id);

    Task getByName(String name);

    List<Task> getList(TextCondition condition);

    List<Task> getAll();

    int insertEntity(TaskDTO entity);

    int updateEntity(TaskDTO entity);

    int deleteById(Long id);

    void createJob(Task task);

    void resumeOrPauseTask(Long id);

    void triggerTask(Long id);
}
