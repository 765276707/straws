package com.gitee.pristine.scripts.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * 脚本编译异常
 * @author Pristine Xu
 * @date 2022/1/14 3:06
 * @description: 脚本编译失败就会抛出此异常
 */
public class ScriptCompileException extends StrawsException {

    public ScriptCompileException(String message) {
        super(message);
    }

    public ScriptCompileException(String message, Throwable cause) {
        super(message, cause);
    }

}
