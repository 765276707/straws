package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.LoginLog;
import com.gitee.pristine.security.clients.Client;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/8 8:17
 * @description:
 */
public interface SysLoginLogService {

    void saveLogWithClient(String username, Client client);

    List<LoginLog> getList(TextCondition condition);
}
