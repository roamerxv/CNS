package com.alcor.cns.controller;

import com.alcor.cns.entity.UserEntity;
import com.alcor.cns.service.ServiceException;
import com.alcor.cns.service.SystemConfigureService;
import com.alcor.cns.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.businesslogger.BusinessMethod;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.configer.ConfigHelper;
import pers.roamer.boracay.helper.HttpResponseHelper;
import pers.roamer.boracay.util.web.FileUploadResult;
import pers.roamer.boracay.util.web.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 用户操作的 Controller
 *
 * @author roamer - 徐泽宇
 * @create 2017-09-2017/9/28  上午9:53
 */
@Log4j2
@RestController("com.alcor.cns.controller.UserController")
@SessionCheckKeyword(checkIt = true)
public class UserController extends com.alcor.cns.controller.BaseController {

    @Autowired
    UserService userService;

    @Autowired
    SystemConfigureService systemConfigureService;


    /**
     * 管理员登出功能
     *
     * @return
     *
     * @throws ServiceException
     */
    @BusinessMethod(value = "管理员登出", isLogged = true)
    @SessionCheckKeyword(checkIt = false)
    @RequestMapping(value = "/user/logout")
    public ModelAndView userLogout() throws ControllerException {
        log.debug("开始登出");
        Enumeration<String> eume = httpSession.getAttributeNames();
        while (eume.hasMoreElements()) {
            String name = eume.nextElement();
            httpSession.removeAttribute(name);
        }
        log.debug("user登出完成");
        return new ModelAndView("/user/login");
    }

    @BusinessMethod(value = "管理员登录")
    @PostMapping("/signIn")
    @SessionCheckKeyword(checkIt = false)
    public String login(@RequestBody UserEntity userEntity) throws ControllerException {
        log.debug("管理员 登录!");
        try {
            if (userService.login(userEntity)) {
                httpSession.setAttribute(ConfigHelper.getConfig().getString("System.SessionUserKeyword"), userEntity.getName());
                String systemBanner = systemConfigureService.findByName("banner_message").getValue();
                httpSession.setAttribute("SystemBanner", systemBanner);
                httpSession.setAttribute("UserEntity", userService.findByID(userEntity.getName()));
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ControllerException(e.getMessage());
        }
        log.debug("管理员登录成功");
        return HttpResponseHelper.successInfoInbox("成功登录");
    }

    /**
     * 跳转到显示用户 profile 的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @RequestMapping("/user/profile")
    public ModelAndView profile() throws ControllerException {
        ModelAndView modelAndView;
        UserEntity userEntity = userService.findByID(super.getUserID());
        log.debug(userEntity.toString());
        modelAndView = new ModelAndView("/user/profile");
        modelAndView.addObject("UserEntity", userEntity);
        return modelAndView;
    }

    @BusinessMethod(value = "更新用户资料")
    @PostMapping("/user/{id}")
    @SessionCheckKeyword
    @ResponseBody
    public String updateUser(@RequestBody UserEntity userEntity) throws ControllerException {
        log.debug("用户资料更新");
        UserEntity userInDb = userService.findByID(userEntity.getName());
        userEntity.setPasswd(userInDb.getPasswd());
        userEntity.setAvatar(userInDb.getAvatar());
        try {
            userService.update(userEntity);
            log.debug("用户资料更新完成");
            return HttpResponseHelper.successInfoInbox("成功登录");
        }catch (ServiceException e){
            throw new ControllerException(e.getMessage());
        }

    }

    @BusinessMethod(value = "更新用户头像")
    @ResponseBody
    @PostMapping(value = "/updateAvatar", consumes = {"multipart/form-data;charset=utf-8"})
    public String updateAvatar(@RequestPart("avatar") MultipartFile[] avatar) throws ControllerException {
        log.debug("开始处理更新头像");
        if (avatar.length <= 0) {
            throw new ControllerException("没有传入有效的头像文件！");
        }
        try {
            ArrayList<FileUploadResult> avatarFileList = new UploadFileUtil().saveFile(avatar, true);
            FileUploadResult avatarFile = avatarFileList.get(0);
            log.debug("保存的文件信息是：{}", avatarFile.toString());
            UserEntity userEntity = userService.findByID(super.getUserID());
            userEntity.setAvatar(avatarFile.getId());
            userService.update(userEntity);
            log.debug("头像更新完成！");
            return HttpResponseHelper.successInfoInbox("成功更新");
        } catch (IOException | NoSuchAlgorithmException | ServiceException e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
    }

    /**
     * 显示用户头像
     *
     * @throws ControllerException
     */
    @RequestMapping(value = "/avatar")
    public void showAvatar(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.debug("开始显示用户头像");
        String userID = super.getUserID();
        UserEntity userEntity = userService.findByID(userID);
        String avatarId = userEntity.getAvatar();
        log.debug("头像的ID 是{}", avatarId);
        String saveFilePath = ConfigHelper.getConfig().getString("System.UploadFile.saveFilePath") + File.separator + avatarId;
        File[] listFiles = new File(saveFilePath).listFiles();
        log.debug(listFiles.length);
        try {
            File avatarFile = null;
            if (StringUtils.isEmpty(avatarId)) {
                log.debug("头像没有设置，使用缺省的头像文件");
                avatarFile = new ClassPathResource("/static/assets/img/logo/logo.png").getFile();
            } else if (listFiles.length <= 0) {
                log.debug("头像文件不存，使用缺省的头像文件");
                avatarFile = new ClassPathResource("/static/assets/img/logo/logo.png").getFile();
            } else {
                avatarFile = listFiles[0];
            }
            log.debug(avatarFile);
            FileInputStream inputStream = new FileInputStream(avatarFile);
            byte[] data = new byte[(int) avatarFile.length()];
            inputStream.read(data);
            inputStream.close();
            response.setContentType("image/png");
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
    }

    @RequestMapping(value = "/user/modify_password")
    @ResponseBody
    public String modifyPassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword) throws ControllerException{
        log.debug("密码修改，老密码：{},新密码{}",oldPassword,newPassword);
        try{
            userService.modifyPassword(super.getUserID(),oldPassword,newPassword);
            return HttpResponseHelper.successInfoInbox("密码修改成功");
        }catch (ServiceException e){
            throw new ControllerException(e.getMessage());
        }
    }

    /**
     * 测试需要记录日志的业务方法
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping(value = "/businessMethodLog")
    @BusinessMethod(value = "测试需要记录日志的业务方法")
    public String businessMethodLog() throws ControllerException {
        return HttpResponseHelper.successInfoInbox("记录业务日志的方法被成功调用！现在可以到日志结果查看功能里面去查看日志是否被成功记录！");
    }


}
