package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.LoginLog;
import com.gitee.pristine.domain.entity.Oplog;
import com.gitee.pristine.security.clients.Client;
import com.gitee.pristine.web.mapper.LoginLogMapper;
import com.gitee.pristine.web.service.SysLoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/8 8:17
 * @description:
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public void saveLogWithClient(String username, Client client) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginUser(username);
        loginLog.setLoginIp(client.getIp());
        loginLog.setLoginOs(client.getOs());
        loginLog.setLoginMode("密码模式");
        loginLog.setLoginTime(new Date());
        loginLogMapper.insertSelective(loginLog);
    }

    @Override
    public List<LoginLog> getList(TextCondition condition) {
        Example example = new Example(LoginLog.class);
        if (!StringUtils.isEmpty(condition.getSearchText())) {
            example.createCriteria()
                    .andLike("loginUser", condition.like());
        }
        return loginLogMapper.selectByExample(example);
    }
}
