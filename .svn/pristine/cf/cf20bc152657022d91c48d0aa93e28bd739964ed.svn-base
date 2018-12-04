package com.glsx.oms.bigdata.admin.bma.salesHistory.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.oreframework.web.ui.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.glsx.oms.bigdata.admin.bma.common.BaseController;
import com.glsx.oms.bigdata.admin.bma.framework.ExportProperty;
import com.glsx.oms.bigdata.admin.bma.salesHistory.model.SalesHistory;
import com.glsx.oms.bigdata.admin.bma.salesHistory.service.SalesHistoryService;
import com.glsx.oms.bigdata.admin.bma.vo.RespMessage;

@RestController
@RequestMapping("/salesHistory")
public class SalesHistoryController extends BaseController<SalesHistory> {

	@Autowired
	// @Resource
	private SalesHistoryService salesHistoryService;

	@Resource
	private ExportProperty exportProperty;

	private final static Logger LOGGER = LoggerFactory.getLogger(SalesHistoryController.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

	@GetMapping("/getListByPage")
	public RespMessage getListByPage(SalesHistory salesHistory, Pagination pagination) {
		RespMessage rm = new RespMessage();
		PageInfo<SalesHistory> page = salesHistoryService.getSpSalesHistory(pagination, salesHistory);
		if (null != page) {
			rm.setData(page.getList());
			rm.setPageTotal(page.getTotal());
		}
		return rm;
	}

	@GetMapping("/getLeaderList")
	public RespMessage getLeaderList(SalesHistory salesHistory, Pagination pagination) {
		RespMessage rm = new RespMessage();
		List<SalesHistory> leader = salesHistoryService.getLeader();
		if (null != leader) {
			rm.setData(leader);
			rm.setPageTotal(leader.size());
		}
		return rm;
	}

	@GetMapping("/getAreaList")
	public RespMessage getAreaList(SalesHistory salesHistory, Pagination pagination) {
		RespMessage rm = new RespMessage();
		List<SalesHistory> area = salesHistoryService.getArea();
		if (null != area) {
			rm.setData(area);
			rm.setPageTotal(area.size());
		}
		return rm;
	}

	@GetMapping("/exportMonthSalesHistory")
	public RespMessage exportMonthSalesHistory(SalesHistory salesHistory) {
		RespMessage rm = new RespMessage();
		try {
			List<SalesHistory> list = salesHistoryService.getSpSalesHistoryByMonth(salesHistory);
			String url = null;
			if (null != list && list.size() > 0) {
				String fileName = salesHistory.getMonth() + "月商户销售" + "_" + System.currentTimeMillis() / 1000 + ".xlsx";
				// 不需要模版导出
				ExcelUtil.getInstance().exportObj2Excel(exportProperty.getLocation() + fileName, list,
						SalesHistory.class);
				url = exportProperty.getDomain() + fileName;
			}
			rm.setData(list);
			rm.setPageTotal(list.size());
			LOGGER.info(salesHistory.getMonth() + "月商户销售表数据导出成功！");
		} catch (Exception e) {
			rm.setMessage("导出失败");
		}
		return rm;
	}

	@GetMapping("/getSumSalesHistoryByType")
	public RespMessage getSumSalesHistoryByType(String taskType) {
		RespMessage rm = new RespMessage();
		List<SalesHistory> sumSaleByMonth = salesHistoryService.getSumSalesByMonth();
		List<SalesHistory> sumTaskByMonth = salesHistoryService.getSumTaskByMonth();
		// 取近10个月的数据进行展示
		List<String> monthList = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < 10; i++) {
			String lastMonth = sdf.format(cal.getTime());
			cal.add(cal.MONTH, -1);
			monthList.add(lastMonth);
		}
		Collections.reverse(monthList);

		List<Integer> saleList = new ArrayList<Integer>();
		List<Integer> taskList = new ArrayList<Integer>();
		List<Float> rateList = new ArrayList<Float>();
		// 任务完成数
		for (int i = 0; i < 10; i++) {
			Integer sumSale = 0;
			for (SalesHistory sale : sumSaleByMonth) {
				if (monthList.get(i).equals(sale.getMonth())) {
					switch (taskType) {
					case "sum":
						sumSale = sale.getSalesGlDvd() + sale.getSalesGlGps() + sale.getSalesYunjing()
								+ sale.getSalesDidihuService() + sale.getSalesWirelessCharge() + sale.getSalesRearview()
								+ sale.getSalesFunctionRearview();
						break;
					case "dvd":
						sumSale = sale.getSalesGlDvd();
						break;
					case "gps":
						sumSale = sale.getSalesGlGps();
						break;
					case "yunjing":
						sumSale = sale.getSalesYunjing();
						break;
					case "didihuService":
						sumSale = sale.getSalesDidihuService();
						break;
					case "wirelessCharge":
						sumSale = sale.getSalesWirelessCharge();
						break;
					case "rearview":
						sumSale = sale.getSalesRearview();
						break;
					case "functionRearview":
						sumSale = sale.getSalesFunctionRearview();
						break;
					}
					break;
				} else {
					sumSale = 0;
				}
			}
			saleList.add(sumSale);

		}
		// 任务计划数
		for (int i = 0; i < 10; i++) {
			Integer sumTask = 0;
			for (SalesHistory task : sumTaskByMonth) {
				if (monthList.get(i).equals(task.getMonth())) {
					switch (taskType) {
					case "sum":
						sumTask = task.getTasknumDvd() + task.getTasknumGps() + task.getTasknumYunjing()
								+ task.getTasknumDidihuService() + task.getTasknumWirelessCharge()
								+ task.getTasknumRearview() + task.getTasknumFunctionRearview();
						break;
					case "dvd":
						sumTask = task.getTasknumDvd();
						break;
					case "gps":
						sumTask = task.getTasknumGps();
						break;
					case "yunjing":
						sumTask = task.getTasknumYunjing();
						break;
					case "didihuService":
						sumTask = task.getTasknumDidihuService();
						break;
					case "wirelessCharge":
						sumTask = task.getTasknumWirelessCharge();
						break;
					case "rearview":
						sumTask = task.getTasknumRearview();
						break;
					case "functionRearview":
						sumTask = task.getTasknumFunctionRearview();
						break;
					}
					break;
				} else {
					sumTask = 0;
				}
			}
			taskList.add(sumTask);
		}
		// 任务完成率
		for (int i = 0; i < 10; i++) {
			float rate = 0;
			if (0 != saleList.get(i) && 0 != taskList.get(i)) {
				float quotient = (float) saleList.get(i) * 100 / (float) taskList.get(i);
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
				String s = df.format(quotient);// 返回的是String类型的
				rate = Float.parseFloat(s);
			} else {
				rate = 0;
			}
			rateList.add(rate);
		}
		LOGGER.info(taskType + "图标信息查询成功！");
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("monthList", monthList);
		resultMap.put("saleList", saleList);
		resultMap.put("taskList", taskList);
		resultMap.put("rateList", rateList);
		rm.setData(resultMap);
		return rm;
	}

	@GetMapping("/getChartListBySp")
	public RespMessage getChartListBySp(String spCode, Pagination pagination, String taskType) {
		RespMessage rm = new RespMessage();
		SalesHistory salesHistory = new SalesHistory();
		salesHistory.setSpCode(spCode);
		List<SalesHistory> saleBySp = salesHistoryService.getSalesBySp(salesHistory);
		List<SalesHistory> taskBySp = salesHistoryService.getTasksBySp(salesHistory);
		PageInfo<SalesHistory> page = salesHistoryService.getSpSalesHistory(pagination, salesHistory);

		// 取近10个月的数据进行展示
		List<String> monthList = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < 10; i++) {
			String lastMonth = sdf.format(cal.getTime());
			cal.add(cal.MONTH, -1);
			monthList.add(lastMonth);
		}
		Collections.reverse(monthList);

		List<Integer> saleList = new ArrayList<Integer>();
		List<Integer> taskList = new ArrayList<Integer>();
		List<Float> rateList = new ArrayList<Float>();

		// 因为存在没有销售数，但有任务数的情况 所以需要分开来查询
		// 任务完成数
		for (int i = 0; i < 10; i++) {
			Integer sumSale = 0;
			for (SalesHistory sale : saleBySp) {
				if (monthList.get(i).equals(sale.getMonth())) {
					switch (taskType) {
					case "sum":
						sumSale = sale.getSalesGlDvd() + sale.getSalesGlGps() + sale.getSalesYunjing()
								+ sale.getSalesDidihuService() + sale.getSalesWirelessCharge() + sale.getSalesRearview()
								+ sale.getSalesFunctionRearview();
						break;
					case "dvd":
						sumSale = sale.getSalesGlDvd();
						break;
					case "gps":
						sumSale = sale.getSalesGlGps();
						break;
					case "yunjing":
						sumSale = sale.getSalesYunjing();
						break;
					case "didihuService":
						sumSale = sale.getSalesDidihuService();
						break;
					case "wirelessCharge":
						sumSale = sale.getSalesWirelessCharge();
						break;
					case "rearview":
						sumSale = sale.getSalesRearview();
						break;
					case "functionRearview":
						sumSale = sale.getSalesFunctionRearview();
						break;
					}
					break;
				} else {
					sumSale = 0;
				}
			}
			saleList.add(sumSale);
		}

		// 任务计划数
		for (int i = 0; i < 10; i++) {
			Integer sumTask = 0;
			for (SalesHistory task : taskBySp) {
				if (monthList.get(i).equals(task.getMonth())) {
					if (null != task.getMonth()) {
						switch (taskType) {
						case "sum":
							sumTask = task.getTasknumDvd() + task.getTasknumGps() + task.getTasknumYunjing()
									+ task.getTasknumDidihuService() + task.getTasknumWirelessCharge()
									+ task.getTasknumRearview() + task.getTasknumFunctionRearview();
							break;
						case "dvd":
							sumTask = task.getTasknumDvd();
							break;
						case "gps":
							sumTask = task.getTasknumGps();
							break;
						case "yunjing":
							sumTask = task.getTasknumYunjing();
							break;
						case "didihuService":
							sumTask = task.getTasknumDidihuService();
							break;
						case "wirelessCharge":
							sumTask = task.getTasknumWirelessCharge();
							break;
						case "rearview":
							sumTask = task.getTasknumRearview();
							break;
						case "functionRearview":
							sumTask = task.getTasknumFunctionRearview();
							break;
						}
						break;
					} else {
						LOGGER.info(monthList.get(i) + "没有导入销售任务");
						sumTask = 0;
					}
				} else {
					sumTask = 0;
				}
			}
			taskList.add(sumTask);
		}
		// 任务完成率
		for (int i = 0; i < 10; i++) {
			float rate = 0;
			if (0 != saleList.get(i) && 0 != taskList.get(i)) {
				float quotient = (float) saleList.get(i) * 100 / (float) taskList.get(i);
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
				String s = df.format(quotient);// 返回的是String类型的
				rate = Float.parseFloat(s);
			} else {
				rate = 0;
			}
			rateList.add(rate);
		}

		/*if (null != page) {
			List<SalesHistory> list = page.getList();

			// 任务完成数&&任务计划数
			for (int i = 0; i < 10; i++) {
				Integer sumSale = 0;
				Integer sumTask = 0;
				for (SalesHistory sp : list) {
					if (monthList.get(i).equals(sp.getMonth())) {
						if (null != sp.getTmonth()) {
							switch (taskType) {
							case "sum":
								sumSale = sp.getSalesGlDvd() + sp.getSalesGlGps() + sp.getSalesYunjing()
										+ sp.getSalesDidihuService() + sp.getSalesWirelessCharge()
										+ sp.getSalesRearview() + sp.getSalesFunctionRearview();
								sumTask = sp.getTasknumDvd() + sp.getTasknumGps() + sp.getTasknumYunjing()
										+ sp.getTasknumDidihuService() + sp.getTasknumWirelessCharge()
										+ sp.getTasknumRearview() + sp.getTasknumFunctionRearview();
								break;
							case "dvd":
								sumSale = sp.getSalesGlDvd();
								sumTask = sp.getTasknumDvd();
								break;
							case "gps":
								sumSale = sp.getSalesGlGps();
								sumTask = sp.getTasknumGps();
								break;
							case "yunjing":
								sumSale = sp.getSalesYunjing();
								sumTask = sp.getTasknumYunjing();
								break;
							case "didihuService":
								sumSale = sp.getSalesDidihuService();
								sumTask = sp.getTasknumDidihuService();
								break;
							case "wirelessCharge":
								sumSale = sp.getSalesWirelessCharge();
								sumTask = sp.getTasknumWirelessCharge();
								break;
							case "rearview":
								sumSale = sp.getSalesRearview();
								sumTask = sp.getTasknumRearview();
								break;
							case "functionRearview":
								sumSale = sp.getSalesFunctionRearview();
								sumTask = sp.getTasknumFunctionRearview();
								break;
							}
						} else {
							LOGGER.info(monthList.get(i) + "没有导入销售任务");
							switch (taskType) {
							case "sum":
								sumSale = sp.getSalesGlDvd() + sp.getSalesGlGps() + sp.getSalesYunjing()
										+ sp.getSalesDidihuService() + sp.getSalesWirelessCharge()
										+ sp.getSalesRearview() + sp.getSalesFunctionRearview();
								sumTask = 0;
								break;
							case "dvd":
								sumSale = sp.getSalesGlDvd();
								sumTask = 0;
								break;
							case "gps":
								sumSale = sp.getSalesGlGps();
								sumTask = 0;
								break;
							case "yunjing":
								sumSale = sp.getSalesYunjing();
								sumTask = 0;
								break;
							case "didihuService":
								sumSale = sp.getSalesDidihuService();
								sumTask = 0;
								break;
							case "wirelessCharge":
								sumSale = sp.getSalesWirelessCharge();
								sumTask = 0;
								break;
							case "rearview":
								sumSale = sp.getSalesRearview();
								sumTask = 0;
								break;
							case "functionRearview":
								sumSale = sp.getSalesFunctionRearview();
								sumTask = 0;
								break;
							}
						}
						break;
					} else {
						sumSale = 0;
						sumTask = 0;
					}
				}
				saleList.add(sumSale);
				taskList.add(sumTask);
			}
		}*/
		LOGGER.info(taskType + "图标信息查询成功！");
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("monthList", monthList);
		resultMap.put("saleList", saleList);
		resultMap.put("taskList", taskList);
		resultMap.put("rateList", rateList);
		rm.setData(resultMap);
		return rm;
	}

}
