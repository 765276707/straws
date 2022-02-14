package com.gitee.pristine.security.clients;

import com.gitee.pristine.security.utils.BrowserUtil;
import com.gitee.pristine.security.utils.DomainUtil;
import com.gitee.pristine.security.utils.IPUtil;
import com.gitee.pristine.security.utils.OSUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端构建器
 * @author Pristine Xu
 * @date 2022/2/3 6:34
 * @description:
 */
public class ClientBuilder {

    /**
     * 构建客户端信息
     * @param request
     * @return
     */
    public static Client build(HttpServletRequest request) {
        Client client = new Client();
        client.setIp(IPUtil.getClientIpAddr(request));
        client.setOs(OSUtil.getClientOS(request));
        client.setBrowser(BrowserUtil.getClientBrowser(request));
        client.setDomain(DomainUtil.getDomainName(request.getRequestURL().toString()));
        return client;
    }

}
