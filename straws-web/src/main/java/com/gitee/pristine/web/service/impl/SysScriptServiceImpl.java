package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.common.utils.DigestUtil;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.ScriptDTO;
import com.gitee.pristine.domain.entity.Script;
import com.gitee.pristine.scripts.ex.ScriptCompileException;
import com.gitee.pristine.scripts.groovy.GroovyExecutor;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.ScriptMapper;
import com.gitee.pristine.web.service.SysScriptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:32
 * @description:
 */
@Service
public class SysScriptServiceImpl implements SysScriptService {

    @Resource
    private ScriptMapper scriptMapper;

    @Override
    public Script getById(Long id) {
        return scriptMapper.selectByPrimaryKey(id);
    }

    @Override
    public Script getByName(String name) {
        Script condition = new Script();
        condition.setName(name);
        return scriptMapper.selectOne(condition);
    }

    @Override
    public List<Script> getList(TextCondition condition) {
        Example example = new Example(Script.class);
        if (!StringUtils.isEmpty(condition.getSearchText())) {
            example.createCriteria()
                    .andLike("name", condition.like());
        }
        return scriptMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public int insertEntity(ScriptDTO entity) {
        // 判断 name 是否已存在
        Script var = this.getByName(entity.getName());
        if (var != null) {
            throw new ServiceException("脚本名称已被使用");
        }
        // 编译脚本
        try {
            GroovyExecutor.compileAndPutCache(entity.getName(), entity.getContent());
        } catch (ScriptCompileException e) {
            throw new ServiceException("脚本编译失败，请检查脚本是否正确");
        }
        // 计算内容的 hash
        String hashKey = null;
        try {
            hashKey = DigestUtil.md5(entity.getContent());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ServiceException("对脚本内容签名失败");
        }
        // 赋值
        Script script = new Script();
        BeanUtils.copyProperties(entity, script);
        script.setHashKey(hashKey);
        script.setCreateTime(new Date());
        // 插入数据库
        return scriptMapper.insertSelective(script);
    }

    @Override
    @Transactional
    public int updateEntity(ScriptDTO entity) {
        // 判断 记录是否存在
        Script var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("更新的数据不存在");
        }
        // 判断 name 是否已被使用
        Script var2 = this.getByName(entity.getName());
        if (var2!=null && !var2.getId().equals(entity.getId())) {
            throw new ServiceException("脚本名称已被使用");
        }
        // 重新编译并刷新脚本
        try {
            GroovyExecutor.compileAndPutCache(entity.getName(), entity.getContent());
        } catch (ScriptCompileException e) {
            throw new ServiceException("脚本编译失败，请检查脚本是否正确");
        }
        // 计算内容的 hash
        String hashKey = null;
        try {
            hashKey = DigestUtil.md5(entity.getContent());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ServiceException("对脚本内容签名失败");
        }
        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setHashKey(hashKey);
        var1.setUpdateTime(new Date());
        // 更新数据库
        return scriptMapper.updateByPrimaryKey(var1);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        Script script = this.getById(id);
        if (script != null) {
            int rows = scriptMapper.deleteByPrimaryKey(id);
            if (rows > 1) {
                // 移除缓存中的脚本
                GroovyExecutor.remove(script.getName());
            }
            return rows;
        }
        return 0;
    }
}
