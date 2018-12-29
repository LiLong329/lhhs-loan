package com.lhhs.loan.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.LoanOrderCredentialsUrl;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.CredentialsInfoService;
import com.lhhs.loan.service.MortgageInfoService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.pathcurve.ir.http.DeepDreamClient;
import com.pathcurve.oss.bean.Policy;
import com.pathcurve.oss.policy.PostObjectPolicyComponent;

/**
 * 抵押物信息接口
 * 
 * @ClassName: MortgageInfoController
 * @Description: TODO
 * @author xiangfeng
 * @date 2017年5月22日 上午10:36:24
 *
 */

@Controller
@RequestMapping("/mortgageInfo")
public class MortgageInfoController {
	private static final Logger logger = LoggerFactory.getLogger(MortgageInfoController.class);

	@Autowired
	private MortgageInfoService mortgageInfoService;
	@Autowired
	private CredentialsInfoService credentialsInfoService;
	@Autowired
	private PostObjectPolicyComponent postObjectPolicyComponent;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ApprovalProcessService processService;
	/**
	 * 抵押物信息保存
	 * @param houseList
	 * @param carList
	 * @return
	 */
	@RequestMapping("/saveMortgageInfo")
	@ResponseBody
	public Map<String, Object> saveMortgageInfo(@RequestParam(required = false) String houseList,
			@RequestParam(required = false) String carList, @RequestParam(required = false) String orderNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<LoanOrderHouseExtend> houseLists = StrUtils.isNullOrEmpty(houseList) ? null
				: JSON.parseArray(houseList, LoanOrderHouseExtend.class);
		List<LoanOrderCarExtend> carLists = StrUtils.isNullOrEmpty(carList) ? null
				: JSON.parseArray(carList, LoanOrderCarExtend.class);
		boolean ret = mortgageInfoService.saveMortgageInfo(houseLists, carLists, orderNo);
		if (ret) {
			//调用接口同步至碰碰旺
			orderInfoService.updateMortgageInfo(houseLists, carLists, orderNo);
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u62B5\u62BC\u7269\u4FE1\u606F\u4FDD\u5B58\u6210\u529F");// 抵押物信息保存成功
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u62B5\u62BC\u7269\u4FE1\u606F\u4FDD\u5B58\u5931\u8D25");// 抵押物信息保存失败
		}
		return result;
	}

	/**
	 * 查询资质信息列表
	 * 
	 * @param currentPageNo
	 * @param pageSize
	 * @param orderNo
	 *            订单号
	 * @param productId
	 *            二级产品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/toOrderCredentials")
	public String toOrderCredentials(@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "productId", required = false) String productId, 
			Model model,
			String isAgreement,
			String isAuditing) {
		List<LoanOrderCredentials> list=null;
		if (!StrUtils.isNullOrEmpty(orderNo) && !StrUtils.isNullOrEmpty(productId)) {
			list=credentialsInfoService.findOrderCredentialsInfoLists(orderNo, productId);
		}
		model.addAttribute("list", list);
		model.addAttribute("isAgreement", isAgreement);
		model.addAttribute("isAuditing", isAuditing);
		return "auditing/credentialsList";
	}

	/**
	 * 跳转到资质上传页
	 * 
	 * @param orderCredentialsNo
	 * @return
	 */
	@RequestMapping("/toUploadPage/{orderCredentialsNo}")
	public String toUploadPage(@PathVariable("orderCredentialsNo") Long orderCredentialsNo, Model model,String isAgreement,String isAuditing) {
		List<LoanOrderCredentialsUrl> urlLists = null;
		LoanOrderCredentials credentials = null;
		if (orderCredentialsNo != null) {
			credentials = credentialsInfoService.findOrderCredentialsByNo(orderCredentialsNo);
			urlLists = credentialsInfoService.findOrderCredentialsURLs(orderCredentialsNo);
		}
		model.addAttribute("credentials", credentials);
		model.addAttribute("urlLists", urlLists);
		model.addAttribute("isAgreement", isAgreement);
		model.addAttribute("isAuditing", isAuditing);
		return "auditing/toUploadCredentialsPage";
	}

	/**
	 * 跳转到资质查看页
	 * 
	 * @param orderCredentialsNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toViewPage/{orderCredentialsNo}")
	public String toViewPage(@PathVariable("orderCredentialsNo") Long orderCredentialsNo, Model model,String isAgreement,String isAuditing) {
		List<LoanOrderCredentialsUrl> urlLists = null;
		LoanOrderCredentials credentials = null;
		if (orderCredentialsNo != null) {
			credentials = credentialsInfoService.findOrderCredentialsByNo(orderCredentialsNo);
			urlLists = credentialsInfoService.findOrderCredentialsURLs(orderCredentialsNo);
		}
		model.addAttribute("credentials", credentials);
		model.addAttribute("urlLists", urlLists);
		model.addAttribute("isAgreement", isAgreement);
		model.addAttribute("isAuditing", isAuditing);
		return "auditing/toViewCredentialsPage";
	}

	/**
	 * 删除资质文件
	 * 
	 * @param urlId
	 * @return
	 */
	@RequestMapping("/deleteFile/{urlId}/{num}")
	@ResponseBody
	public Map<String, Object> deleteFile(@PathVariable("urlId") Long urlId, @PathVariable("num") Integer num) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (urlId != null) {
			int ret = credentialsInfoService.deleteFile(urlId, num);
			if (ret == 1) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "\u5220\u9664\u6210\u529F");// 删除成功
				
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u53C2\u6570\u4E0D\u5408\u6CD5\uFF0C\u5220\u9664\u5931\u8D25");// 参数不合法，删除失败
			}
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u53C2\u6570\u4E0D\u5408\u6CD5\uFF0C\u5220\u9664\u5931\u8D25");// 参数不合法，删除失败
		}
		return result;
	}

	/**
	 * 获取PC上传图片的策略
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/policy")
	public @ResponseBody Policy requestPolicy(Model model) {
		Policy policy = null;
		try {
			policy = postObjectPolicyComponent.creatPolicy("loan");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("@test:" + policy);
		return policy;

	}

	/**
	 * 上传资质信息
	 * 
	 * @param credentialsUrl
	 * @return
	 */
	@RequestMapping("/saveFileURL")
	@ResponseBody
	public Map<String, Object> saveFileURL(LoanOrderCredentialsUrl credentialsUrl) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(credentialsUrl.getImgBase64Str())){
			DeepDreamClient demo = new DeepDreamClient();
			// 如果文档的输入中含有inputs字段，设置为True， 否则设置为False
			Boolean is_old_format = true; 
			// 请根据线上文档修改configure字段
			JSONObject configObj = new JSONObject();
			configObj.put("side", "face");
			String base64Str=credentialsUrl.getImgBase64Str().split(";")[1].split(",")[1];
			try {
				String content = demo.imgAnalyze(base64Str, is_old_format, configObj);
				if(StringUtil.isNotEmpty(content)){
					logger.info("识别图片信息为：" + content);
					Map<String, Object> map=JSON.parseObject(content);
					if((boolean)map.get("success")){
						Map<String, Object> temp = new HashMap<String, Object>();
						temp.put("address", map.get("address"));
						temp.put("nationality", map.get("nationality"));
						temp.put("num", map.get("num"));
						temp.put("sex", map.get("sex"));
						temp.put("name", map.get("name"));
						temp.put("birth", map.get("birth"));
						credentialsUrl.setParseJson(JSON.toJSONString(temp));
					}
				}
			} catch (Exception e) {
				logger.info("无法识别图片信息");
			}
		}
		Long id = credentialsInfoService.saveFileURL(credentialsUrl);
		if (id != null) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put("urlId", id);
			result.put("credentialsUrl", credentialsUrl.getPathUrl());
			result.put("fileExtension", credentialsUrl.getFileExtension());
			result.put(SystemConst.retMsg, "\u4E0A\u4F20\u6210\u529F");// 上传成功
		   }else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u4E0A\u4F20\u5931\u8D25");// 上传失败
		   }
		return result;
	}

	/**
	 * 打包选中的文件
	 * @param request
	 * @param CredentialsNos
	 * @param bname
	 * @return
	 */
	@RequestMapping("/createFilesZip")
	@ResponseBody
	public Map<String, Object> createFilesZip(HttpServletRequest request,
			@RequestParam("CredentialsNos") String CredentialsNos, @RequestParam("bname") String bname) {
		String path = request.getServletContext().getRealPath("/");
		return credentialsInfoService.createFilesZip(JSON.parseArray(CredentialsNos, Long.class), bname, path);
	}

	/**
	 * 下载zip文件
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 */
	@RequestMapping("/downloadZip")
	public void downloadZip(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fileName") String fileName) {
		FileInputStream fis = null;
		OutputStream myout = null;
		try {
			String path = request.getServletContext().getRealPath("/");
			File file = new File(path + fileName);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
			response.setContentLength((int) file.length());
			response.setContentType("application/zip");// 定义输出类型

			fis = new FileInputStream(file);
			byte[] b = new byte[1024];// 相当于我们的缓存
			int len = 0;
			myout = response.getOutputStream();// 从response对象中得到输出流,准备下载
			// 开始循环下载
			while ((len = fis.read(b)) != -1) {
				myout.write(b, 0, len);
			}
			myout.flush();
			file.delete();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if(myout!=null){
					myout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据订单编号查询所有资质信息
	 * @param orderCredentialsNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toViewPageByDetails")
	public String toViewPageByDetails(String orderNo, Model model) {
		List<LoanOrderCredentialsUrl> urlLists = null;
		if (orderNo != null) {
			urlLists = credentialsInfoService.findAllOrderCredentialsURLs(orderNo);
		}
		model.addAttribute("urlLists", urlLists);
		return "auditing/toViewCredentialsPageByDetails";
	}
	
	/**
	 * 查看抵押物-房产信息重复校验
	 * @param request
	 * @param entity
	 */
	@RequestMapping("/queryHouseExtend")
	@ResponseBody
	public Map<String, Object> queryHouseExtend(HttpServletRequest request,@RequestParam(required = false) String houseList){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		List<LoanOrderHouseExtend> houseLists = StrUtils.isNullOrEmpty(houseList) ? null : JSON.parseArray(houseList, LoanOrderHouseExtend.class);
		if(null!=houseLists&&houseLists.size()>0){
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			String str="";
			for(LoanOrderHouseExtend house:houseLists){
				try {
					int count=mortgageInfoService.queryOrderHouseExtend(house);
					if(count>0){
						resultMap.put(SystemConst.retCode, SystemConst.FAIL);
						str+="产权证号:'"+house.getPropertyNum()+"';产权人:'"+house.getPropertyName()+"', ";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			resultMap.put(SystemConst.retMsg, str);
		}
		return resultMap;
	}
	
	
}
