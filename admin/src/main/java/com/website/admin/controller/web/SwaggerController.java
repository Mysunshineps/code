package com.website.admin.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.website.core.AjaxResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
* @Description:    swagger
* @Author:         psq
* @CreateDate:     2021/6/4 10:26
* @Version:        1.0
*/
@RequestMapping("swagger/docs")
@RestController
@Api(value = "聊天室", tags = "聊天室", description = "聊天室 相关接口")
public class SwaggerController extends BaseCRUDController {

    @GetMapping
    @ApiIgnore
    public void login() throws Exception{
        getResponse().sendRedirect("/swagger-ui.html");
    }

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     */
    private void doPages(Integer pageNum,Integer pageSize){
        if (pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 15;
        }
        PageHelper.startPage(pageNum,pageSize,true);
    }

    /**
     * 返回参数
     * @param dataList
     * @return
     */
    private Map<String,Object> doPageReturn(List dataList){
        PageInfo pageInfo = new PageInfo<>(dataList);
        JSONObject object = new JSONObject();
        object.put("Data", dataList);
        object.put("TotalCount", pageInfo.getTotal());
        object.put("PageSize", pageInfo.getPageSize());
        object.put("pages", pageInfo.getPages());
        object.put("CurrentPage", pageInfo.getPageNum());
        return object;
    }

    /**
     * 门店列表
     */
    @ApiOperation(value = "门店列表", httpMethod = "GET", notes = "商城门店不区分店铺和门店，只有门店概念（机构代码和门店Id代表同一个门店）", response = AjaxResponse.class)
    @GetMapping("AppOrg")
    public AjaxResponse appShop(
            @ApiParam(value = "机构代码（门店编号）（支持左匹配）",name = "OrgId") @RequestParam(required = false)String OrgId,
            @ApiParam(value = "门店名称（支持模糊查询）",name = "OrgName") @RequestParam(required = false)String OrgName,
            @ApiParam(value = "页码（不传默认 1）",name = "CurrentPage",example = "1") @RequestParam(required = false) Integer CurrentPage,
            @ApiParam(value = "每页大小(  ,默认 15)",name = "PageSize",example = "15") @RequestParam(required = false)Integer PageSize
    ){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setMessage("测试成功");
        return ajaxResponse;
    }
}
