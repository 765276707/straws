package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.MigrateDTO;
import com.gitee.pristine.domain.entity.Migrate;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.MigrateMapper;
import com.gitee.pristine.web.service.SysMigrateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:32
 * @description:
 */
@Service
public class SysMigrateServiceImpl implements SysMigrateService {

    @Resource
    private MigrateMapper migrateMapper;

    @Override
    public Migrate getById(Long id) {
        return migrateMapper.selectById(id);
    }

    @Override
    public Migrate getByName(String name) {
        return migrateMapper.selectByName(name);
    }

    @Override
    public List<Migrate> getList(TextCondition condition) {
        return migrateMapper.selectList(condition);
    }

    @Override
    @Transactional
    public int insertEntity(MigrateDTO entity) {
        // 判断name是否存在
        Migrate var = this.getByName(entity.getName());
        if (var != null) {
            throw new ServiceException("迁移任务名称已被使用");
        }
        // 赋值
        Migrate migrate = new Migrate();
        BeanUtils.copyProperties(entity, migrate);
        migrate.setCreateTime(new Date());
        // 插入数据库
        return migrateMapper.insertSelective(migrate);
    }

    @Override
    @Transactional
    public int updateEntity(MigrateDTO entity) {
        // 判断记录是否存在
        Migrate var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("修改的迁移任务不存在");
        }
        // 判断名称是否已被使用
        Migrate var2 = this.getByName(entity.getName());
        if (var2!=null && !var2.getId().equals(entity.getId())) {
            throw new ServiceException("迁移任务名称已被使用");
        }
        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        // 更新数据库
        return migrateMapper.updateByPrimaryKeySelective(var1);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return migrateMapper.deleteByPrimaryKey(id);
    }
}
