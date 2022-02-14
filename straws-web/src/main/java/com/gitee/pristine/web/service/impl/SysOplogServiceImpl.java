package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.aop.log.core.Log;
import com.gitee.pristine.aop.log.core.LoggerStorage;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.Oplog;
import com.gitee.pristine.domain.entity.Script;
import com.gitee.pristine.web.mapper.OplogMapper;
import com.gitee.pristine.web.service.SysOplogService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/8 5:07
 * @description:
 */
@Service
public class SysOplogServiceImpl implements SysOplogService, LoggerStorage {

    @Resource
    private OplogMapper oplogMapper;

    @Override
    @Transactional
    public void saveLog(Log log) {
        Oplog oplog = new Oplog();
        BeanUtils.copyProperties(log, oplog);
        oplog.setDescription(log.getDesc());
        oplog.setTypeColor(log.getColor());
        oplog.setCreateTime(new Date());
        oplogMapper.insertSelective(oplog);
    }

    @Override
    public List<Oplog> getList(TextCondition condition) {
        Example example = new Example(Oplog.class);
        if (!StringUtils.isEmpty(condition.getSearchText())) {
            example.createCriteria()
                    .andLike("description", condition.like());
        }
        return oplogMapper.selectByExample(example);
    }

    @Override
    public List<Oplog> getListByUsername(String username) {
        return oplogMapper.selectListByUsername(username);
    }
}
