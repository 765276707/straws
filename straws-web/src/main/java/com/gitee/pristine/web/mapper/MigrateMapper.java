package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.Migrate;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MigrateMapper extends Mapper<Migrate> {

    Migrate selectById(@Param("id") Long id);

    Migrate selectByName(@Param("name") String name);

    List<Migrate> selectList(@Param("condition") TextCondition condition);

}