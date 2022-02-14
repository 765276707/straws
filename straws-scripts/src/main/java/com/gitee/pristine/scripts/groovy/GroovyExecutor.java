package com.gitee.pristine.scripts.groovy;

import com.gitee.pristine.scripts.ex.ScriptCompileException;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 脚本执行器
 * @author Pristine Xu
 * @date 2022/1/14 4:00
 * @description:
 */
public class GroovyExecutor {

    // 日志
    private static Logger log = LoggerFactory.getLogger(GroovyExecutor.class);
    // 存储已编译完的Groovy脚本
    private final static Map<String, GroovyObject> scriptCache = new ConcurrentHashMap<>();

    /**
     * 执行脚本方法
     * @param script 脚本
     * @param method 方法名
     * @param args 方法参数
     * @return
     */
    public static Object execute(String name, String script, String method, Object args) {
        return compile(name, script).invokeMethod(method, args);
    }

    /**
     * 获取脚本对象
     * @param script
     * @return
     */
    public static GroovyObject compile(String name, String script) {
        GroovyObject scriptObj = null;
        if (scriptCache.containsKey(name)) {
            scriptObj = scriptCache.get(name);
        } else {
            scriptObj = compileInSandbox(script);
            scriptCache.put(name, scriptObj);
        }
        return scriptObj;
    }

    /**
     * 获取指定类型的脚本对象
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getScript(String name, Class<T> type) {
        return (T) scriptCache.get(name);
    }

    /**
     * 在沙盒环境中编译
     * @param script
     * @return
     */
    public static GroovyObject compileInSandbox(String script) {
        GroovyObject groovyObject = null;
        try {
            GroovyClassLoader loader = SecureGroovyEngine.getSecureLoader();
            Class var = loader.parseClass(script);
            groovyObject = (GroovyObject) var.newInstance();
        }
        catch (Exception se) {
            if (log.isErrorEnabled()) {
                log.error("groovy script compile failed. cause by {}", se.getMessage());
            }
            throw new ScriptCompileException("groovy script compile failed.", se);
        }
        return groovyObject;
    }


    /**
     * 刷新脚本
     * @param name
     */
    public static void compileAndPutCache(String name, String script) {
        GroovyObject scriptObj = compileInSandbox(script);
        scriptCache.put(name, scriptObj);
    }

    /**
     * 删除脚本
     * @param name
     */
    public static void remove(String name) {
        scriptCache.remove(name);
    }

    /**
     * 清空脚本
     */
    public static void clear() {
        scriptCache.clear();
    }

}
