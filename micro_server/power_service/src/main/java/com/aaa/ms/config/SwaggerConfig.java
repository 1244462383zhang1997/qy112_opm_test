package com.aaa.ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    /**
     * 创建一个docket
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // .enable(false)  //swagger不能访问
                .select()
                //配置要扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.aaa.ms.controller"))
                //路径过滤
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * apiInfo
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("使用Swagger2为当前权限系统构建RESTful APIs")
                .description("更多请关注http://www.baidu.com")
                .termsOfServiceUrl("https://www.baidu.com")
                .contact("aaa")
                .version("1.0")
                .build();
    }


}
