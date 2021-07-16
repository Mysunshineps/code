package com.website.admin.controller.message;

import com.website.admin.controller.web.BaseCRUDController;
import com.website.service.message.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 聊天室控制层
 * @Author psq
 * @Date 2021/7/2/11:26
 */
@RestController
@RequestMapping("/admin/message/")
@Api(value = "聊天室",tags = "聊天室",description = "接口")
public class MessageController extends BaseCRUDController {

    @Autowired
    private MessageService messageService;

}