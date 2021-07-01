package com.website.admin.controller.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
* @Description:    swagger
* @Author:         psq
* @CreateDate:     2021/6/4 10:26
* @Version:        1.0
*/
@RequestMapping("swagger/docs")
@RestController
public class SwaggerController extends BaseCRUDController {

    @GetMapping
    @ApiIgnore
    public void login() throws Exception{
        getResponse().sendRedirect("/swagger-ui.html");
    }

}
