package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.entity.MigrateDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MigrateDetailMapper extends Mapper<MigrateDetail> {

    int checkExistTableName(@Param("migrateId") Long migrateId, @Param("originTableName") String originTableName, @Param("targetTableName") String targetTableName, @Param("excludeId") Long excludeId);

    void insertBatch(@Param("details") List<MigrateDetail> details);
}