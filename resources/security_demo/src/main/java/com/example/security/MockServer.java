/***
 * @pName security
 * @name MockServer
 * @user HongWei
 * @date 2018/7/17
 * @desc
 */
package com.example.security;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class MockServer {
    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8081);
        WireMock.removeAllMappings();

        mockRequest("/order/1", "mock/order.txt");
    }

    private static void mockRequest(String testUrl, String filePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        String json = StringUtils.join(FileUtils.readLines(classPathResource.getFile(), "UTF-8").toArray(), "\n");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(testUrl))
                .willReturn(ResponseDefinitionBuilder.responseDefinition()
                        .withBody(json)
                        .withStatus(200)));
    }
}
