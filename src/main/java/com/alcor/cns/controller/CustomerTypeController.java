package com.alcor.cns.controller;

import com.alcor.cns.entity.CustomerTypeEntity;
import com.alcor.cns.service.CustomerTypeService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;
import pers.roamer.boracay.helper.JsonUtilsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提醒事件的 controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/11  下午12:13
 */
@Log4j2
@RestController("com.alcor.cns.controller.CustomerTypeController")
@SessionCheckKeyword(checkIt = true)
public class CustomerTypeController extends BaseController {


    @Autowired
    CustomerTypeService customerTypeService;

    /**
     * 跳转到客户分类信息显示的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/customer_type/show")
    public ModelAndView create() throws ControllerException {
        log.debug("显示客户分类显示的界面");
        ModelAndView modelAndView = new ModelAndView("/customer_type/show");
        log.debug("显示客户分类显示的界面 end");
        return modelAndView;
    }


    /**
     * 跳转到客户分类信息维护的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/customer_type/edit")
    public ModelAndView edit() throws ControllerException {
        log.debug("显示客户分类维护的界面");
        ModelAndView modelAndView = new ModelAndView("/customer_type/edit");
        log.debug("显示客户分类维护的界面 end");
        return modelAndView;
    }


    @PostMapping("/customer_type/create")
    @ResponseBody
    public String create(@RequestBody CustomerTypeEntity customerTypeEntity) throws ControllerException {
        log.debug("开始更新或者增加一个 customerType : begin");
        try {
            log.debug(JsonUtilsHelper.objectToJsonString(customerTypeEntity));
            customerTypeService.create(customerTypeEntity);
        } catch (ServiceException | JsonProcessingException e) {
            log.trace(e);
            throw new ControllerException(e.getMessage());
        }
        log.debug("开始更新或者增加一个 customerType : end");
        return HttpResponseHelper.successInfoInbox("增加成功");
    }

    /**
     * 返回用于 jsTree 控件使用的显示所有客户类型的 json 形式的数据
     */
    @GetMapping("customer_type/list4jsTree")
    @ResponseBody
    @SessionCheckKeyword(checkIt = false)
    public String findAllForJsTree() throws ControllerException {
        log.debug("获取使用在 jsTree 上的所有客户类型的 json 对象: begin");
        try {

            List<CustomerTypeEntity> customerTypeEntityList = customerTypeService.findAll();

            List<JsTreeItemEntity> jsTreeItemEntityList = new ArrayList<>();
            log.debug("共发现{}条记录！",customerTypeEntityList.size());
            for (CustomerTypeEntity item : customerTypeEntityList) {
                JsTreeItemEntity jsTreeItemEntity = new JsTreeItemEntity();
                jsTreeItemEntity.setId(item.getId());
                jsTreeItemEntity.setParent(item.getpId());
                jsTreeItemEntity.setText(item.getName());
                jsTreeItemEntityList.add(jsTreeItemEntity);
            }
            Map map = new HashMap();
            map.put("data", jsTreeItemEntityList);
            String m_rtn = JsonUtilsHelper.objectToJsonString(map);
            log.debug("返回所有的客户类型的字符串是【{}】", m_rtn);
            log.debug("获取使用在 jsTree 上的所有客户类型的 json 对象: end");
            return m_rtn;
        } catch (JsonProcessingException | ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    /**
     * 返回用于 select2 控件使用的显示所有客户类型的 json 形式的数据
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("customer_type/list4Select2")
    @ResponseBody
    @SessionCheckKeyword(checkIt = true)
    public String findAllForSelect2() throws ControllerException {
        log.debug("获取所有客户类型的 json 对象");
        try {
            @Data
            class Select2ItemEntity {
                private String id;
                private String text;
                private List<Select2ItemEntity> children;
            }
            List<CustomerTypeEntity> customerTypeEntityList = customerTypeService.findWithPid("0");

            List<Select2ItemEntity> select2ItemEntities = new ArrayList<Select2ItemEntity>();

            for (CustomerTypeEntity item : customerTypeEntityList) {
                Select2ItemEntity select2ItemEntity = new Select2ItemEntity();
                select2ItemEntity.setId(item.getId());
                select2ItemEntity.setText(item.getName());


                List<CustomerTypeEntity> customerTypeEntityChildenList = customerTypeService.findWithPid(item.getId());

                List<Select2ItemEntity> select2ChildenItemEntities = new ArrayList<Select2ItemEntity>();
                for (CustomerTypeEntity childItem : customerTypeEntityChildenList) {
                    Select2ItemEntity select2ChildItemEntity = new Select2ItemEntity();
                    select2ChildItemEntity.setId(childItem.getId());
                    select2ChildItemEntity.setText(childItem.getName());
                    select2ChildenItemEntities.add(select2ChildItemEntity);
                }
                select2ItemEntity.setChildren(select2ChildenItemEntities);

                select2ItemEntities.add(select2ItemEntity);
            }
//            Map map = new HashMap();
//            map.put("results", select2ItemEntities);
            String m_rtn = JsonUtilsHelper.objectToJsonString(select2ItemEntities);
            log.debug("返回所有的客户类型的字符串是【{}】", m_rtn);
            return m_rtn;
        } catch (JsonProcessingException | ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }
}

@Data
class Select2ItemEntity {
    private String id;
    private String text;
    private List<Select2ItemEntity> children;
}

@Data
class JsTreeItemEntity{
    private String id ;
    private String parent;
    private String text ;
}

