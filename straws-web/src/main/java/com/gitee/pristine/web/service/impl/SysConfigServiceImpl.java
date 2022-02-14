package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.dto.ConfigDTO;
import com.gitee.pristine.domain.entity.Config;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.ConfigMapper;
import com.gitee.pristine.web.service.SysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Pristine Xu
 * @date 2022/2/7 13:32
 * @description:
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private ConfigMapper configMapper;

    // 唯一的有效的配置名称，其他配置名称无效
    private final static String CONFIG_NAME = "DEFAULT";

    @Override
    public Config getDefault() {
        Config condition = new Config();
        condition.setName(CONFIG_NAME);
        return configMapper.selectOne(condition);
    }

    @Override
    @Transactional
    public int updateDefault(ConfigDTO entity) {
        Config config = this.getDefault();
        if (config == null) {
            throw new ServiceException("系统缺少默认配置");
        }
        BeanUtils.copyProperties(entity, config);
        int rows = configMapper.updateByPrimaryKeySelective(config);
        return rows;
    }

    @Override
    @Transactional
    public int resetDefault() {
        Config config = this.getDefault();
        if (config == null) {
            throw new ServiceException("系统缺少默认配置");
        }
        config.setForceUpdatePassword(true);
        config.setForceUpdateInterval(24*30); // 30天
        config.setEncDspwdInDb(true);
        int rows = configMapper.updateByPrimaryKeySelective(config);
        return rows;
    }
}
