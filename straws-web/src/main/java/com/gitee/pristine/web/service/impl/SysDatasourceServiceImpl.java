package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.DatasourceDTO;
import com.gitee.pristine.domain.dto.DeleteEntityDTO;
import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.DatasourceMapper;
import com.gitee.pristine.web.service.SysDatasourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:31
 * @description:
 */
@Service
public class SysDatasourceServiceImpl implements SysDatasourceService {

    @Resource
    private DatasourceMapper datasourceMapper;

    @Override
    public Datasource getById(Long id) {
        return datasourceMapper.selectById(id);
    }

    @Override
    public Datasource getByName(String name) {
        return datasourceMapper.selectByName(name);
    }

    @Override
    public List<Datasource> getList(TextCondition condition) {
        return datasourceMapper.selectList(condition);
    }

    @Override
    @Transactional
    public int insertEntity(DatasourceDTO entity) {
        // 判断 name 是否已存在
        Datasource var = this.getByName(entity.getName());
        if (var != null) {
            throw new ServiceException("数据源名称已被使用");
        }
        // 赋值
        Datasource datasource = new Datasource();
        BeanUtils.copyProperties(entity, datasource);
        datasource.setCreateTime(new Date());
        // 插入数据库
        return datasourceMapper.insertSelective(datasource);
    }

    @Override
    @Transactional
    public int updateEntity(DatasourceDTO entity) {
        // 判断 记录是否存在
        Datasource var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("更新的数据不存在");
        }
        // 判断 name 是否已被使用
        Datasource var2 = this.getByName(entity.getName());
        if (var2!=null && !var2.getId().equals(entity.getId())) {
            throw new ServiceException("数据源名称已被使用");
        }
        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        // 更新数据库
        return datasourceMapper.updateByPrimaryKey(var1);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return datasourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteAfterCheckPwd(DeleteEntityDTO deleteDTO) {
        // 校验用户密码
        if (!"123456".equalsIgnoreCase(deleteDTO.getPassword())) {
            throw new ServiceException("您输入的密码不正确，删除失败");
        }
        // 执行删除
        return deleteById(deleteDTO.getId());
    }

}
