package com.gitee.pristine.domain.condition;

import com.gitee.pristine.domain.base.Condition;

/**
 * @author Pristine Xu
 * @date 2022/2/2 1:36
 * @description:
 */
public class TaskLogCondition extends Condition {

    private Long taskId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "TaskLogCondition{" +
                "taskId=" + taskId +
                '}';
    }
}
