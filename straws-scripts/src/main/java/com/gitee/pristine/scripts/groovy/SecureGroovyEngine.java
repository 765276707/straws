package com.gitee.pristine.scripts.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.transform.ThreadInterrupt;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.codehaus.groovy.syntax.Types;
import org.kohsuke.groovy.sandbox.SandboxTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全的Groovy脚本引擎
 * @author Pristine Xu
 * @date 2022/1/14 3:08
 * @description: 添加了AST和沙箱机制
 */
public class SecureGroovyEngine {

    private static CompilerConfiguration configuration = null;
    private static ClassLoader classLoader = null;

    static {
        configuration = getCompilerConfiguration();
        classLoader = SecureGroovyEngine.class.getClassLoader();
    }

    /**
     * 获取安全的带有沙盒的加载器
     * @return
     */
    public static GroovyClassLoader getSecureLoader() {
        return new GroovyClassLoader(classLoader, configuration);
    }


    /**
     * 加载器配置
     * @return
     */
    private static CompilerConfiguration getCompilerConfiguration() {
        if (configuration == null) {
            synchronized (SecureGroovyEngine.class) {
                if (configuration == null) {
                    configuration = new CompilerConfiguration();

                    // 添加线程中断拦截器
                    ASTTransformationCustomizer ast = new ASTTransformationCustomizer(ThreadInterrupt.class);
                    configuration.addCompilationCustomizers(ast);

                    // 添加沙盒环境
                    SandboxTransformer sandbox = new SandboxTransformer();
                    configuration.addCompilationCustomizers(sandbox);

                    // 注册拦截器
                    InterceptorRegister.scanAndRegisterOfPrefix("com.gitee.pristine.scripts.groovy.interceptor");

                    // 配置 SecureASTCustomizer
                    SecureASTCustomizer secure = new SecureASTCustomizer();
                    configSecureASTCustomizer(secure);
                    configuration.addCompilationCustomizers(secure);
                }
            }
        }
        return configuration;
    }

    /**
     * 配置 SecureASTCustomizer
     * @param secure
     */
    private static void configSecureASTCustomizer(SecureASTCustomizer secure) {
        // 禁止使用闭包
        secure.setClosuresAllowed(true);
        // 设置直接导入检查
        secure.setIndirectImportCheckEnabled(true);
        // 设置黑名单
        List<Integer> blackList = new ArrayList<>(3);
        blackList.add(Types.KEYWORD_WHILE);
        blackList.add(Types.KEYWORD_DO);
        blackList.add(Types.KEYWORD_GOTO);
        secure.setTokensBlacklist(blackList);
        // 设置Statement黑名单
        List<Class<? extends Statement>> statBlackList = new ArrayList<>(1);
        statBlackList.add(WhileStatement.class);
        secure.setStatementsBlacklist(statBlackList);
    }

}
