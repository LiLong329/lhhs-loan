package com.lhhs.loan.service;


import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.LoanOrderCredentialsUrl;

/**
 * 资质信息相关的服务
 * @ClassName: CredentialsInfoService
 * @Description: TODO
 * @author xiangfeng
 * @date 2017年5月24日 下午2:11:22
 *
 */
public interface CredentialsInfoService {
	
	/**
	 * 查询订单资质信息列表页对象
	 * @param page
	 * @param orderNo
	 * @param productId
	 */
	public List<LoanOrderCredentials> findOrderCredentialsInfoLists(String orderNo, String productId);
	/**
	 * 复制当前二级产品资质信息到当前订单的资质信息中
	 * @param orderNo
	 * @param childProductNo
	 * @return
	 */
	public boolean copyProductCredentialsToOrderCredentials(String orderNo,String childProductNo);
	/**
	 * 根据订单资质编号查询订单资质URL
	 * @param orderCredentialsNo
	 * @return
	 */
	public List<LoanOrderCredentialsUrl> findOrderCredentialsURLs(Long orderCredentialsNo);
	/**
	 * 根据订单资质编号查询订单资质
	 * @param orderCredentialsNo
	 * @return
	 */
	public LoanOrderCredentials findOrderCredentialsByNo(Long orderCredentialsNo);
	/**
	 * 删除文件
	 * @param urlId
	 * @param num 
	 * @return
	 */
	public int deleteFile(Long urlId, Integer num);
	/**
	 * 保存资质URL  修改资质上传状态和时间
	 * @param credentialsUrl
	 * @return
	 */
	public Long saveFileURL(LoanOrderCredentialsUrl credentialsUrl);
	/**
	 * 打包资质信息，生成zip文件
	 * @param credentialsNoList
	 * @param bname
	 * @param path
	 * @return
	 */
	public Map<String, Object> createFilesZip(List<Long> credentialsNoList, String bname, String path);
	/**
	 * 查询订单资质信息
	 * @param orderNo
	 * @param object
	 * @return
	 */
	public List<LoanOrderCredentials> findOrderCredentialsByworkFlow(String orderNo, String productId);
	/**
	 * 根据订单编号查询所有资质信息
	 * @param orderNo
	 * @return
	 */
	public List<LoanOrderCredentialsUrl> findAllOrderCredentialsURLs(String orderNo);
	
	/**
	 * 查询已经上传的图片信息解析文本
	 * @param Map<String, Object>
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryOrderCredentialsParseJson(Map<String, Object> param);
}
