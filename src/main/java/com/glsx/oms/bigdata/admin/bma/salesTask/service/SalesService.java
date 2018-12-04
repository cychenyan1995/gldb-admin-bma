package com.glsx.oms.bigdata.admin.bma.salesTask.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.oreframework.web.ui.Pagination;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.glsx.oms.bigdata.admin.bma.common.BaseService;
import com.glsx.oms.bigdata.admin.bma.salesTask.mapper.SalesTaskMapper;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.MonthSalesTask;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.MonthSalesTaskDetail;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.SalesTask;
import com.glsx.oms.bigdata.admin.bma.salesTask.model.SalesTaskCom;

import cn.com.glsx.club.wechat.remote.WechatMessageRemote;


@Service
public class SalesService extends BaseService<SalesTask>{
	@Resource
	private SalesTaskMapper salesTaskMapper;
	

	private SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
	Date date = new Date();
    //String month = ft.format(date);
    String month = "2018-11";
	
	public PageInfo<SalesTask> getSalesTaskList(Pagination pagination){
		PageInfo<SalesTask> pageInfo = setPageInfo(pagination);
		Page<SalesTask> page = getPage(pageInfo);
        
		List<SalesTask> SalesTaskList = salesTaskMapper.getSalesTaskList(month);
		setPageInfo(pageInfo, page, SalesTaskList);
		return pageInfo;
	}
	
	public int updateSalesTask(SalesTask salesTask){
        salesTask.setMonth(month);
		return salesTaskMapper.updateSalesTask(salesTask);
	}
	
	public void insertAllSalesTask(List<Object> salesTaskList){
		salesTaskMapper.insertAllSalesTask(salesTaskList);
    }
	
	public List<String> getAllManagerLeader(){
		
		return salesTaskMapper.getAllManagerLeader(month);
	}
	public List<String> getAllManager(){
		
		return salesTaskMapper.getAllManager(month);
	}
	public List<String> getAllSpCode(){
		
		return salesTaskMapper.getAllSpCode(month);
	}
	

	public String getCodeBySpName(String spName){
		return salesTaskMapper.getCodeBySpName(spName);
	}
	
	
	public List<MonthSalesTask> getSendMessage(SalesTaskCom salesTask) throws IllegalAccessException, InvocationTargetException{
		Map<String,Object> paraMap = new HashMap<>();
		List<MonthSalesTask> spInfoList = new ArrayList<MonthSalesTask>();
		List<MonthSalesTask> resList = new ArrayList<MonthSalesTask>();
		Map<String,SalesTaskCom> areaMap = new HashMap<>();
		List<String> spCodeList = new ArrayList<>();
		List<SalesTaskCom> salesList = new ArrayList<>();
		//详情表的list
		
		String type = salesTask.getType();
		String name = salesTask.getName();
		//salesTask.setMonth(month);

		if(type.equals("sp")){
			spCodeList.add(name);
			paraMap.put("spCodeList", spCodeList);
			paraMap.put("month", month);
			//1.2得到各个服务商的7个产品销售数量和任务数
			salesList = salesTaskMapper.getDetailMessage(paraMap);
			salesTask.setSpName(name);
			for (SalesTaskCom sales : salesList) {
				MonthSalesTask monthSalesTask = new MonthSalesTask();
				monthSalesTask.setManager(sales.getSpName());
				//7个产品销售数量  任务数和  将任务数赋值到sales中   7个产品和
				computeMonthSales(sales,monthSalesTask);
				// 把详细表数据也计算出来   详细列表的数据********
				List<MonthSalesTaskDetail> monthSalesTaskDetailList = operateProsRatio(sales);
				monthSalesTask.setMonthSalesTaskDetailList(monthSalesTaskDetailList);
				resList.add(monthSalesTask);
				
			}
		}else{
			if(type.equals("leader")){
				salesTask.setRegionalManagerLeader(name);
				spCodeList = salesTaskMapper.getSpCodeList(salesTask);
				if(spCodeList.size() > 0){
					paraMap.put("spCodeList", spCodeList);
				}
				paraMap.put("month", salesTask.getMonth());
				//1.2得到各个服务商的7个产品销售数量和任务数
				salesList = salesTaskMapper.getDetailMessage(paraMap);
				//1.3根据任务数 销售数计算
				for (SalesTaskCom sales : salesList) {
					MonthSalesTask monthSalesTask = new MonthSalesTask();
					monthSalesTask.setManager(sales.getSpName());
					//7个产品销售数量  任务数和  将任务数赋值到sales中   7个产品和
					computeMonthSales(sales,monthSalesTask);
					// 把详细表数据也计算出来   详细列表的数据 ********
					List<MonthSalesTaskDetail> monthSalesTaskDetailList = operateProsRatio(sales);
					monthSalesTask.setMonthSalesTaskDetailList(monthSalesTaskDetailList);
					//各个服务商的数据放到list集合中
					spInfoList.add(monthSalesTask);
					if(areaMap.get(sales.getArea()) != null){
						SalesTaskCom salesTaskBefore = areaMap.get(sales.getArea());
						//salesTaskBefore,sales   7个产品的销量和任务数分别相加  
						operateProducts(salesTaskBefore,sales);
						areaMap.put(sales.getArea(), salesTaskBefore);
					}else{
						areaMap.put(sales.getArea(), sales);
					}
					
				}
			}else if(type.equals("manager")){
				salesTask.setRegionalManager(name);
				spCodeList = salesTaskMapper.getSpCodeList(salesTask);
				if(spCodeList.size() > 0){
					paraMap.put("spCodeList", spCodeList);
				}
				paraMap.put("month", month);
				//1.2得到各个服务商的7个产品销售数量和任务数
				salesList = salesTaskMapper.getDetailMessage(paraMap);
				//1.3计算
				for (SalesTaskCom sales : salesList) {
					MonthSalesTask monthSalesTask = new MonthSalesTask();
					monthSalesTask.setManager(sales.getSpName());
					//7个产品销售数量  任务数和  将任务数赋值到sales中   7个产品和
					computeMonthSales(sales,monthSalesTask);
					// 把详细表数据也计算出来   详细列表的数据  ********
					List<MonthSalesTaskDetail> monthSalesTaskDetailList = operateProsRatio(sales);
					monthSalesTask.setMonthSalesTaskDetailList(monthSalesTaskDetailList);
					//各个服务商的数据放到list集合中
					spInfoList.add(monthSalesTask);
				}
			}
			
			//2.1 大区经理  区域经理的数据  各个服务商的销量和  任务数和
			int totalTaskNum = 0;
			int totalSalesNum = 0;
			for (MonthSalesTask monthSalesTask : spInfoList) {
				totalTaskNum += monthSalesTask.getTotalTaskNum();
				totalSalesNum += monthSalesTask.getTotalSaleNum();
			}
			MonthSalesTask monthSalesTask = new MonthSalesTask();
			if(type.equals("manager")){
				monthSalesTask.setManager(salesTask.getRegionalManager());
			}else {
				monthSalesTask.setManager(salesTask.getRegionalManagerLeader());
			}
			monthSalesTask.setTotalSaleNum(totalSalesNum);
			monthSalesTask.setTotalTaskNum(totalTaskNum);
			monthSalesTask.setTotalRatio(computedRatio(totalTaskNum,totalSalesNum));
			
			//计算大区经理  区域经理 对应的详情表数据********
			SalesTaskCom salesTaskDetail = new SalesTaskCom();
			BeanUtils.copyProperties(salesTaskDetail, salesList.get(0));
			for (int i =1;i < salesList.size(); i++) {
				operateProducts(salesTaskDetail,salesList.get(i));
			}
			List<MonthSalesTaskDetail> monthSalesTaskDetailList = operateProsRatio(salesTaskDetail);
			monthSalesTask.setMonthSalesTaskDetailList(monthSalesTaskDetailList);
			//2.2加到list集合
			resList.add(monthSalesTask);
			
			if(salesTask.getType().equals("leader")){
				//3.2大区经理下的区域计算
				for (Entry<String, SalesTaskCom> entry : areaMap.entrySet()) {
					SalesTaskCom areaSalesTask = entry.getValue();
					String area = entry.getKey();
					MonthSalesTask monthSalesTaskArea = new MonthSalesTask();
					monthSalesTaskArea.setManager(area);
					computeMonthSales(areaSalesTask,monthSalesTaskArea);
					// 把详细表数据也计算出来   详细列表的数据 ********
					monthSalesTaskDetailList = operateProsRatio(areaSalesTask);
					monthSalesTaskArea.setMonthSalesTaskDetailList(monthSalesTaskDetailList);
					resList.add(monthSalesTaskArea);
				}
			}
			
			//4将服务商的计算信息保存到leaderList中
			for (MonthSalesTask spInfo : spInfoList) {
				resList.add(spInfo);
			}
		}
		
		return resList;
	}
	
	
	/*public List<MonthSalesTask> getManagerDetailMessage(SalesTask salesTask){
		List<MonthSalesTask> managerList = new ArrayList<>();
		List<MonthSalesTask> spInfoList = new ArrayList<MonthSalesTask>();
		Map<String,Object> paraMap = new HashMap<>();
		//1.1得到区域经理对应的spCodeList
		List<String> spCodeList = salesTaskMapper.getSpCodeList(salesTask);
		//1.2得到区域经理对应服务商的的销量  任务数
		paraMap.put("spCodeList", spCodeList);
		paraMap.put("month", month);
		List<SalesTask> salesList = salesTaskMapper.getDetailMessage(paraMap);
		//1.3计算
		for (SalesTask sales : salesList) {
			MonthSalesTask monthSalesTask = new MonthSalesTask();
			monthSalesTask.setManager(sales.getSpName());
			//7个产品销售数量  任务数和  将任务数赋值到sales中   7个产品和
			computeMonthSales(sales,monthSalesTask);
			//各个服务商的数据放到list集合中
			spInfoList.add(monthSalesTask);
		}
		
		//2.1区域经理的数据  各个服务商的销量和  任务数和
		int totalTaskNum = 0;
		int totalSalesNum = 0;
		for (MonthSalesTask monthSalesTask : spInfoList) {
			totalTaskNum += monthSalesTask.getTotalTaskNum();
			totalSalesNum += monthSalesTask.getTotalSaleNum();
		}
		MonthSalesTask monthSalesTask = new MonthSalesTask();
		monthSalesTask.setManager(salesTask.getRegionalManagerLeader());
		monthSalesTask.setTotalSaleNum(totalSalesNum);
		monthSalesTask.setTotalTaskNum(totalTaskNum);
		monthSalesTask.setTotalRatio(totalSalesNum / totalTaskNum * 100 + "%");
		//2.2加到list集合
		managerList.add(monthSalesTask);
		
		//3将服务商添加到managerList
		for (MonthSalesTask spInfo : spInfoList) {
			managerList.add(spInfo);
		}
		
		return managerList;
	}*/

	/*public List<MonthSalesTaskDetail> getDetailMessage(SalesTask salesTask){
		List<MonthSalesTaskDetail> MonthSalesTaskDetailList = new ArrayList<>();
		List<String> spCodeList = new ArrayList<>();
		String type = salesTask.getType();
		String name = salesTask.getName();
		
		Map<String,Object> paraMap = new HashMap<>();

		if(type.equals("sp")){
			spCodeList.add(name);
		}else{
			spCodeList = salesTaskMapper.getSpCodeList(salesTask);
		}
		
		paraMap.put("spCodeList", spCodeList);
		paraMap.put("month", month);
		
		//1.2得到各个服务商的7个产品销售数量和任务数
		List<SalesTask> salesList = salesTaskMapper.getDetailMessage(paraMap);
		if(salesList.size() > 0){
			if(type.equals("sp")){
				//一个商户只对应一个
				SalesTask salesAndTask = salesList.get(0);
				//operateProsRatio(salesAndTask,MonthSalesTaskDetailList);
			}else{
				
			}
		}
		
		return MonthSalesTaskDetailList;
	}*/
	
	public List<String> getSpCodeList(SalesTaskCom salesTask){
		return salesTaskMapper.getSpCodeList(salesTask);
	}
	
	private void computeMonthSales(SalesTaskCom salesTask,MonthSalesTask monthSalesTask){
		int totalSalesNum = 0;
		int totalTaskNum = 0;
		
		if(salesTask.getSalesGlDvd() != null){
			totalSalesNum += salesTask.getSalesGlDvd();
		}
		if(salesTask.getSalesGlGps() != null){
			totalSalesNum += salesTask.getSalesGlGps();
		}
		if(salesTask.getSalesYunjing() != null){
			totalSalesNum += salesTask.getSalesYunjing();
		}
		if(salesTask.getSalesWirelessCharge() != null){
			totalSalesNum += salesTask.getSalesWirelessCharge();
		}
		if(salesTask.getSalesDidihuService() != null){
			totalSalesNum += salesTask.getSalesDidihuService();
		}
		if(salesTask.getSalesRearview() != null){
			totalSalesNum += salesTask.getSalesRearview();
		}
		if(salesTask.getSalesFunctionRearview() != null){
			totalSalesNum += salesTask.getSalesFunctionRearview();
		}
		
		
		if(salesTask.getTasknumDvd() != null){
			totalTaskNum += salesTask.getTasknumDvd();
		}
		if(salesTask.getTasknumGps() != null){
			totalTaskNum += salesTask.getTasknumGps();
		}
		if(salesTask.getTasknumYunjing() != null){
			totalTaskNum += salesTask.getTasknumYunjing();
		}
		if(salesTask.getTasknumWirelessCharge() != null){
			totalTaskNum += salesTask.getTasknumWirelessCharge();
		}
		if(salesTask.getTasknumDidihuService() != null){
			totalTaskNum += salesTask.getTasknumDidihuService();
		}
		if(salesTask.getTasknumRearview() != null){
			totalTaskNum += salesTask.getTasknumRearview();
		}
		if(salesTask.getTasknumFunctionRearview() != null){
			totalTaskNum += salesTask.getTasknumFunctionRearview();
		}
		monthSalesTask.setTotalSaleNum(totalSalesNum);
		monthSalesTask.setTotalTaskNum(totalTaskNum);
		monthSalesTask.setTotalRatio(computedRatio(totalTaskNum,totalSalesNum));
		
	}
	
	private void operateProducts(SalesTaskCom salesTaskBefore,SalesTaskCom salesTask){
		salesTaskBefore.setSalesGlDvd(salesTaskBefore.getSalesGlDvd()+salesTask.getSalesGlDvd());
		salesTaskBefore.setSalesGlGps(salesTaskBefore.getSalesGlGps()+salesTask.getSalesGlGps());
		salesTaskBefore.setSalesYunjing(salesTaskBefore.getSalesYunjing()+salesTask.getSalesYunjing());
		salesTaskBefore.setSalesWirelessCharge(salesTaskBefore.getSalesWirelessCharge()+salesTask.getSalesWirelessCharge());
		salesTaskBefore.setSalesDidihuService(salesTaskBefore.getSalesDidihuService()+salesTask.getSalesDidihuService());
		salesTaskBefore.setSalesRearview(salesTaskBefore.getSalesRearview()+salesTask.getSalesRearview());
		salesTaskBefore.setSalesFunctionRearview(salesTaskBefore.getSalesFunctionRearview()+salesTask.getSalesFunctionRearview());
		
		salesTaskBefore.setTasknumDvd(salesTaskBefore.getTasknumDvd()+salesTask.getTasknumDvd());
		salesTaskBefore.setTasknumGps(salesTaskBefore.getTasknumGps()+salesTask.getTasknumGps());
		salesTaskBefore.setTasknumYunjing(salesTaskBefore.getTasknumYunjing()+salesTask.getTasknumYunjing());
		salesTaskBefore.setTasknumWirelessCharge(salesTaskBefore.getTasknumWirelessCharge()+salesTask.getTasknumWirelessCharge());
		salesTaskBefore.setTasknumDidihuService(salesTaskBefore.getTasknumDidihuService()+salesTask.getTasknumDidihuService());
		salesTaskBefore.setTasknumRearview(salesTaskBefore.getTasknumRearview()+salesTask.getTasknumRearview());
		salesTaskBefore.setTasknumFunctionRearview(salesTaskBefore.getTasknumFunctionRearview()+salesTask.getTasknumFunctionRearview());
	}
	
	private List<MonthSalesTaskDetail> operateProsRatio(SalesTaskCom salesTask){
		List<MonthSalesTaskDetail> MonthSalesTaskDetailList = new ArrayList<>();
		salesTask.setRatioGlDvd(devide(salesTask.getSalesGlDvd(),salesTask.getTasknumDvd()));
		salesTask.setRatioGlGps(devide(salesTask.getSalesGlGps(), salesTask.getTasknumGps()));
		salesTask.setRatioYunjing(devide(salesTask.getSalesYunjing(), salesTask.getTasknumYunjing()));
		salesTask.setRatioDidihuService(devide(salesTask.getSalesDidihuService(), salesTask.getTasknumDidihuService()));
		salesTask.setRatioWirelessCharge(devide(salesTask.getSalesWirelessCharge(),salesTask.getTasknumWirelessCharge()));
		salesTask.setRatioRearview(devide(salesTask.getSalesRearview(),salesTask.getTasknumRearview()));
		salesTask.setRatioFunctionRearview(devide(salesTask.getSalesFunctionRearview(),salesTask.getTasknumFunctionRearview()));
		
		MonthSalesTaskDetail pro1 = new MonthSalesTaskDetail();
		pro1.setTaskType("广联DVD");
		pro1.setSaleNum(salesTask.getSalesGlDvd());
		pro1.setTaskNum(salesTask.getTasknumDvd());
		pro1.setRatio(salesTask.getRatioGlDvd());
		MonthSalesTaskDetailList.add(pro1);
		
		MonthSalesTaskDetail pro2 = new MonthSalesTaskDetail();
		pro2.setTaskType("广联GPS");
		pro2.setSaleNum(salesTask.getSalesGlGps());
		pro2.setTaskNum(salesTask.getTasknumGps());
		pro2.setRatio(salesTask.getRatioGlGps());
		MonthSalesTaskDetailList.add(pro2);
		
		MonthSalesTaskDetail pro4 = new MonthSalesTaskDetail();
		pro4.setTaskType("无线车充");
		pro4.setSaleNum(salesTask.getSalesWirelessCharge());
		pro4.setTaskNum(salesTask.getTasknumWirelessCharge());
		pro4.setRatio(salesTask.getRatioWirelessCharge());
		MonthSalesTaskDetailList.add(pro4);
		
		MonthSalesTaskDetail pro3 = new MonthSalesTaskDetail();
		pro3.setTaskType("嘀嘀虎智能云镜");
		pro3.setSaleNum(salesTask.getSalesYunjing());
		pro3.setTaskNum(salesTask.getTasknumYunjing());
		pro3.setRatio(salesTask.getRatioYunjing());
		MonthSalesTaskDetailList.add(pro3);
		
		MonthSalesTaskDetail pro5 = new MonthSalesTaskDetail();
		pro5.setTaskType("嘀嘀虎车联网服务激活卡-1年版");
		pro5.setSaleNum(salesTask.getSalesDidihuService());
		pro5.setTaskNum(salesTask.getTasknumDidihuService());
		pro5.setRatio(salesTask.getRatioDidihuService());
		MonthSalesTaskDetailList.add(pro5);
		
		MonthSalesTaskDetail pro6 = new MonthSalesTaskDetail();
		pro6.setTaskType("纯流媒体后视镜");
		pro6.setSaleNum(salesTask.getSalesRearview());
		pro6.setTaskNum(salesTask.getTasknumRearview());
		pro6.setRatio(salesTask.getRatioRearview());
		MonthSalesTaskDetailList.add(pro6);
		
		MonthSalesTaskDetail pro7 = new MonthSalesTaskDetail();
		pro7.setTaskType("全功能_流媒体后视镜");
		pro7.setSaleNum(salesTask.getSalesFunctionRearview());
		pro7.setTaskNum(salesTask.getTasknumFunctionRearview());
		pro7.setRatio(salesTask.getRatioFunctionRearview());
		MonthSalesTaskDetailList.add(pro7);
		
		MonthSalesTaskDetail total = new MonthSalesTaskDetail();
		total.setTaskType("合计");
		
		MonthSalesTask monthSalesTask = new MonthSalesTask();
		computeMonthSales(salesTask,monthSalesTask);
		
		total.setSaleNum(monthSalesTask.getTotalSaleNum());
		total.setTaskNum(monthSalesTask.getTotalTaskNum());
		total.setRatio(monthSalesTask.getTotalRatio());
		MonthSalesTaskDetailList.add(total);
		
		return MonthSalesTaskDetailList;
	}
	
	private String devide(int sale, int task){
		String res = "";
		if(task != 0){
			res = Math.round((sale * 100.0 / task) * 100) / 100.0 + "%";
		}
		return res;
		
	}
	
	private String computedRatio(int task,int sale){
		String res = Math.round((sale * 100.0 / task) * 100) / 100.0 + "%";
		return res;
	}
	
}
