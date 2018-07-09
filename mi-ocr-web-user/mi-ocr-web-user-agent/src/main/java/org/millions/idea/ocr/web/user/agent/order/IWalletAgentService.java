/***
 * @pName mi-ocr-web-user
 * @name IWalletAgentService
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.user.agent.order;


import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.millions.idea.ocr.web.user.entity.agent.WalletEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("${services.order}")
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface IWalletAgentService {

    @RequestMapping(value = "/wallet/get?uid={uid}",method = RequestMethod.GET)
    WalletEntity get(@PathVariable("uid") Integer uid);
}
