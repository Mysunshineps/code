package com.website.admin.controller.room;

import com.alibaba.fastjson.JSON;
import com.website.admin.controller.web.BaseCRUDController;
import com.website.core.AjaxResponse;
import com.website.model.Room;
import com.website.model.User;
import com.website.model.UserRoomRelation;
import com.website.service.room.RoomService;
import com.website.service.user.UserRoomRelationService;
import com.website.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 聊天室控制层
 * @Author psq
 * @Date 2021/7/1/15:15
 */
@RestController
@RequestMapping("/admin/room/")
@Api(value = "聊天室模块", tags = "聊天室模块", description = "接口")
public class RoomController extends BaseCRUDController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoomRelationService userRoomRelationService;

    /**
     * 创建房间
     * @param name 房间名
     * @return
     */
    @ApiOperation(value = "创建房间", httpMethod = "POST", notes = "创建房间", response = AjaxResponse.class)
    @RequestMapping(method = RequestMethod.POST)
    public AjaxResponse create(@ApiParam(value = "name", name = "房间名", required = true) @RequestParam String name){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse response = new AjaxResponse();
        Room room = new Room();
        room.setName(name);
        List<Room> dbRoomList = roomService.select(room);
        if (null != dbRoomList && !dbRoomList.isEmpty()){
            return response.gengerateMsgError("该房间名已使用，请重新输入!");
        }
        room.setCreateTime(new Date());
        roomService.insertSelective(room);
        return response;
    }

    /**
     * 进入房间
     * @param request
     * @param roomid 房间id
     * @return
     */
    @ApiOperation(value = "进入房间", httpMethod = "PUT", notes = "进入房间", response = AjaxResponse.class)
    @RequestMapping(value = "/{roomid}/enter",method = RequestMethod.PUT)
    public AjaxResponse enter(HttpServletRequest request,@ApiParam(value = "roomid", name = "房间id", required = true) @PathVariable String roomid){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse response = new AjaxResponse();
        Object userId = request.getAttribute("userId");
        if(null == userId) {
            return response.gengerateMsgError("登录失效，请重新登录!");
        }
        User user = userService.selectByKey(userId);
        if (null == user || 0 == user.getStatus()){
            return response.gengerateMsgError("该用户信息无效或被冻结!");
        }
        Room room = roomService.selectByKey(roomid);
        if (null == room){
            return response.gengerateMsgError("该房间无效!");
        }
        Example example = new Example(UserRoomRelation.class);
        example.createCriteria().andEqualTo("userId",user.getId());
        userRoomRelationService.deleteByExample(example);

        UserRoomRelation userRoomRelation = new UserRoomRelation();
        userRoomRelation.setUserId(user.getId());
        userRoomRelation.setRoomId(room.getId());
        userRoomRelation.setCreateTime(new Date());
        userRoomRelationService.insertSelective(userRoomRelation);
        return response;
    }

    /**
     * 离开房间
     * @param request
     * @return
     */
    @ApiOperation(value = "离开房间", httpMethod = "PUT", notes = "离开房间", response = AjaxResponse.class)
    @RequestMapping(value = "/roomLeave",method = RequestMethod.PUT)
    public AjaxResponse roomLeave(HttpServletRequest request){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse response = new AjaxResponse();
        Object userId = request.getAttribute("userId");
        if(null == userId) {
            return response.gengerateMsgError("登录失效，请重新登录!");
        }
        Example example = new Example(UserRoomRelation.class);
        example.createCriteria().andEqualTo("userId",userId);
        userRoomRelationService.deleteByExample(example);
        return response;
    }

    /**
     * 获取聊天室信息
     * @param request
     * @param roomid 房间id
     * @return
     */
    @ApiOperation(value = "获取聊天室信息", httpMethod = "GET", notes = "获取聊天室信息", response = AjaxResponse.class)
    @RequestMapping(value = "/room/{roomid}",method = RequestMethod.GET)
    public AjaxResponse roomInfo(HttpServletRequest request,@ApiParam(value = "roomid", name = "房间id", required = true) @PathVariable String roomid){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse response = new AjaxResponse();
        Room room = roomService.selectByKey(roomid);
        if (null == room){
            return response.gengerateMsgError("该聊天室无效!");
        }
        return response.success(room);
    }

    /**
     * 获取该聊天室用户信息
     * @param request
     * @param roomid 房间id
     * @return
     */
    @ApiOperation(value = "获取聊天室信息", httpMethod = "GET", notes = "获取聊天室信息", response = AjaxResponse.class)
    @RequestMapping(value = "/room/{roomid}/users",method = RequestMethod.GET)
    public AjaxResponse roomUserInfo(HttpServletRequest request,@ApiParam(value = "roomid", name = "房间id", required = true) @PathVariable String roomid){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse response = new AjaxResponse();
        if (StringUtils.isBlank(roomid)){
            return response.gengerateMsgError("参数为空!");
        }
        List<Map<String,Object>> result = roomService.selectUsersByRoomId(roomid);
        return response.success(result);
    }


    /**
     * 获取聊天室列表
     * @param pageIndex 页码（不传默认 1）
     * @param pageSize 每页大小(不传默认 10)
     * @return
     */
    @ApiOperation(value = "获取聊天室列表", httpMethod = "POST", notes = "获取聊天室列表", response = AjaxResponse.class)
    @RequestMapping(value = "/roomList",method = RequestMethod.POST)
    public AjaxResponse roomList(
            @ApiParam(value = "页码（不传默认 1）",name = "pageIndex",example = "1") @RequestParam(required = false) Integer pageIndex,
            @ApiParam(value = "每页大小(不传默认 10)",name = "pageSize",example = "10") @RequestParam(required = false)Integer pageSize
    ){
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            Map<String,Object> params = getParams();
            System.out.println(JSON.toJSONString(params));
            doPages(pageIndex,pageSize);
            List<Room> result = roomService.selectAll();
            return ajaxResponse.success(doPageReturn(result));
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResponse.gengerateMsgError(e.getMessage());
        }
        return ajaxResponse;
    }



}
