package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.Datasource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DatasourceMapper extends Mapper<Datasource> {

    Datasource selectById(@Param("id") Long id);

    Datasource selectByName(@Param("name") String name);

    List<Datasource> selectList(@Param("condition") TextCondition condition);

}