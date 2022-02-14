package com.gitee.pristine.web.manager;

import com.gitee.pristine.common.utils.VerifyCodeUtil;
import com.gitee.pristine.domain.vo.VerifyCodeVO;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 验证码管理器
 * @author Pristine Xu
 * @date 2022/2/4 11:12
 * @description:
 */
@Component
public class VerifyCodeManager {

    // 验证码存储在Redis固定前缀
    private final static String VERIFY_CODE_PREFIX_IN_CACHE = "verify:code:";
    // 验证码过期时间，单位：分钟
    private final static int expire = 5;

    private final Logger log = LoggerFactory.getLogger(VerifyCodeManager.class);

    private Cache<String, String> cache;

    @PostConstruct
    public void init() {
        // 初始化缓存
        this.cache = getCache();
    }

    private Cache<String, String> getCache() {
        Cache<String, String> cache = Caffeine.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(expire, TimeUnit.MINUTES)
                .maximumSize(100000)
                .build();
        return cache;
    }


    /**
     * 创建验证码
     * @param length 验证码长度，默认：6
     * @param expire 过期时间，单位：分钟， 默认：5 minute
     * @return
     */
    public VerifyCodeVO createVerifyCode(Integer length, Integer expire) {
        // 生成验证码和编号
        String verifyId = VerifyCodeUtil.generateVerifyId();
        String verifyCode = VerifyCodeUtil.generateVerifyCode(length==null ? 6:length);
        // 存入缓存
        saveToCache(verifyId, verifyCode, expire);
        // 返回
        return new VerifyCodeVO(verifyId, verifyCode);
    }


    /**
     * 校验验证码
     * @param verifyId 验证码编号
     * @param verifyCode 验证码内容
     * @return
     */
    public boolean checkVerifyCode(String verifyId, String verifyCode) {
        // 查询 ID
        String verifyCodeInCache = getCodeFromCache(verifyId);
        // 验证码内容是否一致
        if (verifyCodeInCache == null) {
            return false;
        }
        return verifyCodeInCache.equals(verifyCode);
    }


    /**
     * 验证码的Key
     * @param verifyId 验证码编号
     * @return
     */
    private String generatorCacheKey(@NonNull String verifyId) {
        return VERIFY_CODE_PREFIX_IN_CACHE + verifyId;
    }


    /**
     * 验证码信息存入缓存
     * @param verifyId 验证码编号
     * @param verifyCode 验证码内容
     * @param expire 过期时间
     */
    private void saveToCache(String verifyId, String verifyCode, Integer expire) {
        // 默认5分钟
        expire = expire == null ? 5 : expire;
        String cacheKey = generatorCacheKey(verifyId);
        cache.put(cacheKey, verifyCode);
        if (log.isDebugEnabled()) {
            log.debug("验证码已存入缓存，编号为: {}，内容：{}，过期时间为：{}分钟", verifyId, verifyCode, expire);
        }
    }


    /**
     * 查询缓存中验证码
     * @param verifyId 验证码编号
     * @return
     */
    private String getCodeFromCache(String verifyId) {
        String cacheKey = generatorCacheKey(verifyId);
        String verifyCode = cache.getIfPresent(cacheKey);
        if (verifyCode == null) {
            return null;
        }
        // 取出缓存并删除
        cache.invalidate(cacheKey);
        if (log.isDebugEnabled()) {
            log.debug("验证码已从缓存取出并删除，验证码为: {}", verifyCode);
        }
        return verifyCode;
    }

}
