package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.entity.Dict;
import com.gitee.pristine.web.mapper.DictMapper;
import com.gitee.pristine.web.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 10:06
 * @description:
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public List<Dict> getList(String dictType) {
        // 构建查询条件
        Dict condition = new Dict();
        condition.setDictType(dictType);
        condition.setEnabled(true);
        // 查询结果
        return dictMapper.select(condition);
    }

}
