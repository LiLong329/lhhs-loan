package com.lhhs.loan.service.impl;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.zip.ZipUtils;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.LoanOrderCredentialsMapper;
import com.lhhs.loan.dao.LoanOrderCredentialsUrlMapper;
import com.lhhs.loan.dao.LoanProductCredentialsMapper;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.LoanOrderCredentialsUrl;
import com.lhhs.loan.dao.domain.LoanProductCredentials;
import com.lhhs.loan.service.CredentialsInfoService;
import com.lhhs.loan.service.transport.ProviderTransportService;

@Service
public class CredentialsInfoServiceImpl implements CredentialsInfoService {
	@Autowired
	private LoanOrderCredentialsMapper loanOrderCredentialsMapper;
	@Autowired
	private LoanProductCredentialsMapper loanProductCredentialsMapper;
	@Autowired
	private LoanOrderCredentialsUrlMapper loanOrderCredentialsUrlMapper;
	@Autowired
	private ProviderTransportService providerTransportService;
	
	@Override
	@Transactional
	public List<LoanOrderCredentials> findOrderCredentialsInfoLists(String orderNo, String productId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", orderNo);
		param.put("productId", productId);
		List<LoanOrderCredentials> list = loanOrderCredentialsMapper.findOrderCredentialsInfoLists(param);
		return list;
	}

	@Override
	@Transactional
	public boolean copyProductCredentialsToOrderCredentials(String orderNo, String childProductNo) {
		boolean ret = false;
		List<LoanProductCredentials> credentials = loanProductCredentialsMapper.findProductCredentialsByProductId(childProductNo);
		if(credentials != null && credentials.size() > 0){
			for(LoanProductCredentials c : credentials){
				LoanOrderCredentials temp = c.transformOrderCredentials(orderNo);
				loanOrderCredentialsMapper.insertSelective(temp);
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public List<LoanOrderCredentialsUrl> findOrderCredentialsURLs(Long orderCredentialsNo) {
		return loanOrderCredentialsUrlMapper.findOrderCredentialsURLs(orderCredentialsNo);
	}

	@Override
	public LoanOrderCredentials findOrderCredentialsByNo(Long orderCredentialsNo) {
		return loanOrderCredentialsMapper.selectByPrimaryKey(orderCredentialsNo);
	}

	@Override
	@Transactional(noRollbackFor = Exception.class)
	public int deleteFile(Long urlId, Integer num) {
		//TODO  OSS文件暂未删除
		int count = 0;
		LoanOrderCredentialsUrl credentialsUrl = loanOrderCredentialsUrlMapper.selectByPrimaryKey(urlId);
		if(credentialsUrl != null){
			//本地删除图片
		    LoanOrderCredentials credentials = loanOrderCredentialsMapper.selectByPrimaryKey(credentialsUrl.getOrderCredentialsNo());
			count = loanOrderCredentialsUrlMapper.deleteByPrimaryKey(urlId);
			if(num == 1 && count == 1 && credentials != null){
				credentials.setOrderCredentialsStatus("0");
				credentials.setOrderCredentialsUploadTime(null);
				count = loanOrderCredentialsMapper.updateByPrimaryKey(credentials);
			}
		}
		return count;
	}

	@Override
	@Transactional
	public Long saveFileURL(LoanOrderCredentialsUrl credentialsUrl) {
		if(credentialsUrl.getOrderCredentialsNo() != null){
			LoanOrderCredentials credentials = loanOrderCredentialsMapper.selectByPrimaryKey(credentialsUrl.getOrderCredentialsNo());
			if(credentials != null){
				credentialsUrl.setUpdateTime(new Date());
				loanOrderCredentialsUrlMapper.insertSelective(credentialsUrl);
				credentials.setOrderCredentialsStatus("1");
				credentials.setOrderCredentialsUploadTime(new Date());
				loanOrderCredentialsMapper.updateByPrimaryKeySelective(credentials);
			}
		}
		return credentialsUrl.getUrlId();
	}

	@Override
	public Map<String, Object> createFilesZip(List<Long> credentialsNoList, String bname, String path) {
		Map<String,Object> result = new HashMap<String,Object>();
		String fileName = DateUtils.getDate() + "_" + bname + ".zip";
		String tempDir = path + "download" + File.separator;
		try{
			for(Long id : credentialsNoList){
				if(id != null){
					LoanOrderCredentials cred =  loanOrderCredentialsMapper.selectByPrimaryKey(id);
					if(cred != null){
						File file = new File(tempDir+cred.getOrderCredentialsName()+File.separator);
						if(!file.exists()){
							file.mkdirs();
						}
						List<LoanOrderCredentialsUrl> urlList = loanOrderCredentialsUrlMapper.findOrderCredentialsURLs(cred.getOrderCredentialsNo());
						if(urlList != null && urlList.size() > 0){
							for(int i=0;i < urlList.size() ;i++){
								FileUtils.copyURLToFile(new URL(urlList.get(i).getPathUrl()), new File(tempDir+cred.getOrderCredentialsName()+File.separator+cred.getOrderCredentialsName()+"_"+i+urlList.get(i).getFileExtension()));
							}
						}
					}
				}
			}
			
			ZipUtils.createZip(tempDir, path+fileName);
			ZipUtils.deleteDir(new File(tempDir));
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put("fileName", fileName);
			
		}catch(Exception e){
			result.put(SystemConst.retCode, SystemConst.FAIL);
		}
		return result;
	}

	@Override
	public List<LoanOrderCredentials> findOrderCredentialsByworkFlow(String orderNo, String productId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", orderNo);
		param.put("productId", productId);
		param.put("orderCredentialsStatus", "1");
		List<LoanOrderCredentials> lists = loanOrderCredentialsMapper.findOrderCredentialsInfoLists(param);
		return lists;
	}

	@Override
	public List<LoanOrderCredentialsUrl> findAllOrderCredentialsURLs(String orderNo) {
		return loanOrderCredentialsUrlMapper.findAllOrderCredentialsURLs(orderNo);
	}

	@Override
	public List<Map<String, Object>> queryOrderCredentialsParseJson(Map<String, Object> param) {
		return loanOrderCredentialsMapper.queryOrderCredentialsParseJson(param);
	}

}
