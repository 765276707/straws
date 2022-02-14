package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.dto.AccountLoginDTO;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.domain.vo.UserInfoVO;
import com.gitee.pristine.security.clients.Client;

/**
 * @author Pristine Xu
 * @date 2022/2/3 5:48
 * @description:
 */
public interface SysAuthService {

    /**
     * 账号密码登录
     * @param accountLoginDTO 账号密码参数
     * @param client 客户端信息
     * @return
     */
    User executeLogin(AccountLoginDTO accountLoginDTO, Client client);

    /**
     * 获取用户的登录信息
     * @param username
     * @return
     */
    UserInfoVO getUserInfo(String username);
}
