package com.glsx.oms.bigdata.admin.bma.salesTask.controller;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.oreframework.web.ui.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.glsx.oms.bigdata.admin.bma.common.BaseController;
import com.glsx.oms.bigdata.admin.bma.framework.ImportExcelProperty;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.MonthSalesTask;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.SalesTask;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.SalesTaskCom;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.SendTemplate;
import com.glsx.oms.bigdata.admin.bma.salesTask.service.SalesService;
import com.glsx.oms.bigdata.admin.bma.system.bean.ImportedResult;
import com.glsx.oms.bigdata.admin.bma.util.ExcelUtils;
import com.glsx.oms.bigdata.admin.bma.vo.RespMessage;
import com.glsx.oms.bigdata.admin.bma.vo.RespStatusEnum;
import org.apache.commons.io.FileUtils;
import com.mysql.fabric.xmlrpc.base.Array;

import cn.com.glsx.club.user.model.User;
import cn.com.glsx.club.user.remote.UserRemote;
import cn.com.glsx.club.wechat.enums.WechatNoticeSetTypeEnum;
import cn.com.glsx.club.wechat.remote.WechatMessageRemote;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;

@RestController
@RequestMapping("/salesTask")

public class SalesTaskController extends BaseController<SalesTask>{

	private final static Logger LOGGER = LoggerFactory.getLogger(SalesTaskController.class);

	/**
	 * 静态变量配置类
	 */
	@Autowired
	private ImportExcelProperty importExcelProperty;

	@Resource
	private SalesService salesService;

	@Reference
	private WechatMessageRemote wechatMessageRemote;
	
	@Reference
	private UserRemote userRemote;
	
	@GetMapping("/getSalesTaskList")
	public RespMessage getSalesTaskList(SalesTask salesTask, Pagination pagination) {
		RespMessage rm = new RespMessage();
		PageInfo<SalesTask> page = salesService.getSalesTaskList(pagination);
		if (null != page) {
			rm.setData(page.getList());
			rm.setPageTotal(page.getTotal());
			LOGGER.info("getSalesTaskList记录条数：" + page.getTotal());
		}
		return rm;
	}

	@PostMapping("/updateTask")
	public RespMessage updateSalesTask(@RequestBody SalesTask salesTask) {
		RespMessage rm = new RespMessage();
		int res = salesService.updateSalesTask(salesTask);
		if (res <= 0) {
			rm.setStatus(RespStatusEnum.FAIL);
		}
		return rm;
	}
	
	//只推送任务数   /send/sendMessage/:taskType/:time/:type/:name   
	/*time要生成的月份的数据   推送的月份都是当前月   taskType === 0  显示完成度   taskType === 1  不显示完成度*/
	@GetMapping("/sendMessage")
	public RespMessage sendMessage(){
		RespMessage rm = new RespMessage();
		//查询到所有的大区经理，区域经理，sp
		List<String> leaders = salesService.getAllManagerLeader();
		List<String> managers = salesService.getAllManager();
		List<String> managerstemp = new ArrayList<>();
		SendTemplate sendTemplate = new SendTemplate();
		//区域经理可能有多个
		for (String manager : managers) {
			if(manager.indexOf('/') != -1){
				String[] managerSplit = manager.split("/");
				for (String string : managerSplit) {
					managerstemp.add(string);
				}
			}else{
				managerstemp.add(manager);
			}
		}
		List<String> sps = salesService.getAllSpCode();
		
		//1.推送的模板
		SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月");
		SimpleDateFormat ftd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
	    String month = ft.format(date);
	    String monthDay = ftd.format(date);
	    SimpleDateFormat ftt = new SimpleDateFormat("yyyy-MM");
	    String time = ftt.format(date);
		sendTemplate.setFirst("您好，您的"+month+"销售任务目标已生成");
		sendTemplate.setKeyWork1(month+"销售任务");
		sendTemplate.setKeyWork2(monthDay);
		//2.url
		sendTemplate.setUrl("http://192.168.2.69:8000/#/send/sendMessage/0/"+time+"/sp/"+sps.get(0));
		//3.根据sp_code返回用户id
		//  服务商的推送  
		LOGGER.info("服务商调用消息推送接口....");
		for (String sp : sps) {
			//Integer spTemp = Integer.parseInt(sp);
			Integer spTemp = Integer.parseInt("101454");
			RpcResponse<User> user = userRemote.getMerchant(spTemp);
			Integer spUserId = user.getResult().getId();
			
			LOGGER.info("要推送的消息数：服务商个数"+sps.size());
			//23
			RpcResponse<Integer> result = wechatMessageRemote.sendMessage(WechatNoticeSetTypeEnum.GH_SALE_DETAIL,spUserId,sendTemplate);
			
			LOGGER.info("推送成功数:"+result);
		}
		
		//调推送的接口  
		/*LOGGER.info("要推送的消息数：大区经理个数"+leaders.size()+"+区域经理个数"+managers.size()+"+服务商个数"+managerstemp.size());
		
		RpcResponse<Integer> result = wechatMessageRemote.sendMessage(WechatNoticeSetTypeEnum.MAINTAIN,111,null);
		
		LOGGER.info("推送成功数:"+result);*/
		return rm;
	}
	
	@GetMapping("/querySendInfo")
	public RespMessage getSendMessage(SalesTaskCom salesTask) throws IllegalAccessException, InvocationTargetException{
		RespMessage rm = new RespMessage();
		LOGGER.info("推送给"+salesTask.getType()+":"+salesTask.getName());
		List<MonthSalesTask> salesSendData = new ArrayList<>();
		//得到缓存的task数据
		//Map<String,SalesTask> dictTaskNum = getTaskInfoDict();
		
		salesSendData = salesService.getSendMessage(salesTask);
		
		//大区经理，区域经理查出对应的spCode   多个区域经理 模糊查询
		
		LOGGER.info("推送的报表数据记录数："+salesSendData.size());
		rm.setData(salesSendData);
		return rm;
	}
	
	
	@PostMapping(value = "/importSalesTask")
	public RespMessage importSalesTask(@RequestParam("file") MultipartFile file) {
		RespMessage rm = new RespMessage();
		List<ImportedResult> importListRes = new ArrayList<ImportedResult>();
		ImportedResult importedResult = new ImportedResult();
		importedResult.setIsImported(0);
		importedResult.setCause("导入成功");

		if (file != null) {
			try {
				String fileName = file.getOriginalFilename();

				// 判断是否有文件
				if (null != fileName && !"".equalsIgnoreCase(fileName.trim())
						&& (FilenameUtils.getExtension(fileName.trim()).equals("xls")
								|| FilenameUtils.getExtension(fileName.trim()).equals("xlsx"))) {
					// 可以选择把文件保存到服务器,创建输出文件对象
					String onlyName = fileName.replace("." + FilenameUtils.getExtension(fileName), "");
					String name = onlyName + "_" + UUID.randomUUID().toString() + "."
							+ FilenameUtils.getExtension(fileName);
					// 得到文件上传的URL
					String fileUrl = importExcelProperty.getUploadPath() + name;
					String downloadUrl = importExcelProperty.getDomain() + "upload/" + name;
					File outFile = new File(fileUrl);
					LOGGER.info("路径:" + downloadUrl);

					// 文件到输出文件对象
					FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
					LOGGER.info("success");

					// 获得输入流
					InputStream input = null;
					input = file.getInputStream();

					List<Object> importList = null;
					try {
						// 读取基础数据
						LOGGER.info("读取基础数据..........");
						// 读excel数据
						importList = ExcelUtils.getInstance().readExcel2Objs(input, SalesTask.class, 0, 0);
						List<SalesTask> salesTaskList = new ArrayList<SalesTask>();
						if (importList != null && importList.size() > 0) {
							for (Object object : importList) {
								SalesTask salesTask = (SalesTask) object;
								String spCode = salesService.getCodeBySpName(salesTask.getSpName());
								salesTask.setSpCode(spCode);
								salesTaskList.add(salesTask);
							}
							// 批量将数据插入数据库
							LOGGER.info("导入Excel数据,批量插入数据库...");
							salesService.insertAllSalesTask(importList);
						}

					} catch (Exception e) {
						LOGGER.error("导入Excel数据失败:" + e);
					}
				} else {
					importedResult.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
					LOGGER.warn("上传文件不为.xls或.xlsx格式");
					importedResult.setIsImported(2);
					importListRes.add(importedResult);
					rm.setData(importListRes);
					return rm;
				}
				rm.setCode(1000);
			} catch (Exception e) {
				LOGGER.warn("catch an exception in importTasks", e);
			}
		}
		return rm;
	}

}
