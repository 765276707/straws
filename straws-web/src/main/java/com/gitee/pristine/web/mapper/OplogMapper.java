package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.entity.Oplog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OplogMapper extends Mapper<Oplog> {

    List<Oplog> selectListByUsername(@Param("username") String username);

}