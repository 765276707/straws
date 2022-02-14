package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.dto.ConfigDTO;
import com.gitee.pristine.domain.entity.Config;

/**
 * @author Pristine Xu
 * @date 2022/2/7 13:32
 * @description:
 */
public interface SysConfigService {

    Config getDefault();

    int updateDefault(ConfigDTO entity);

    int resetDefault();

}
