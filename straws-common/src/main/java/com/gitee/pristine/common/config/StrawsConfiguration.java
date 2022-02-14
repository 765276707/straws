package com.gitee.pristine.common.config;

/**
 * 框架内配置类父接口
 * @author Pristine Xu
 * @date 2022/1/13 4:20
 * @description: 框架内所有配置都要实现该类
 */
public interface StrawsConfiguration {

    /**
     * 返回配置类的名称
     * @return
     */
    public String getConfigName();

}
