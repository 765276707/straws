package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.Oplog;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/8 5:07
 * @description:
 */
public interface SysOplogService {

    List<Oplog> getList(TextCondition condition);

    List<Oplog> getListByUsername(String username);
}
