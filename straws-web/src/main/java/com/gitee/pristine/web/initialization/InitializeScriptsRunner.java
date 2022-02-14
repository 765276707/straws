package com.gitee.pristine.web.initialization;

import com.gitee.pristine.domain.entity.Script;
import com.gitee.pristine.scripts.groovy.GroovyExecutor;
import com.gitee.pristine.web.mapper.ScriptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 初始化所有已存在的脚本
 * @author Pristine Xu
 * @date 2022/1/22 17:38
 * @description:
 */
@Component
public class InitializeScriptsRunner implements CommandLineRunner {

    private final Logger log  = LoggerFactory.getLogger(InitializeScriptsRunner.class);

    @Resource
    private ScriptMapper scriptMapper;

    @Override
    public void run(String... args) throws Exception {
        // 查询所有脚本
        List<Script> scripts = scriptMapper.selectAll();
        // 编译脚本
        for (Script script : scripts) {
            GroovyExecutor.compileAndPutCache(script.getName(), script.getContent());
        }
        // 打印日志
        log.info("[Straws] Successfully loaded and compiled {} scripts", scripts.size());
    }

}
