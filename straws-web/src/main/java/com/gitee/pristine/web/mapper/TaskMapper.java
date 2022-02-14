package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.Task;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskMapper extends Mapper<Task> {

    List<Task> selectList(@Param("condition") TextCondition condition);

}