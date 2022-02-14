package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.entity.Dict;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 10:06
 * @description:
 */
public interface SysDictService {

    List<Dict> getList(String dictType);

}
