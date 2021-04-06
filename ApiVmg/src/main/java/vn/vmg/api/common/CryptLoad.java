package vn.vmg.api.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vn.vmg.api.common.utils.JsonUtils;
import vn.vmg.api.crypt.AESKeyLength;
import vn.vmg.api.crypt.CryptStarApp;
import vn.vmg.api.crypt.CryptStarDetail;

public class CryptLoad {
	private static final Logger logger = Logger.getLogger(CryptLoad.class);

	public static void onLoad() {
		try {
			CryptConf conf = JsonUtils.mapFileToClass(SerConfig.app.cryptConfDir, CryptConf.class, true);
			CryptStarApp cryptApp = CryptStarApp.getInstall();
			if (conf.lstCrypt.size() == 1 && conf.lstCrypt.get(0).cryptId == null) {
				cryptApp.loadCrypt(conf.apiPortListen, conf.cryptServerUrl, conf.lstCrypt.get(0).appName,
						conf.lstCrypt.get(0).appId, conf.lstCrypt.get(0).keyLength);
				logger.info("load key crypt complete!");
			} else {
				List<CryptStarDetail> listApiCrypt = new ArrayList<CryptStarDetail>();
				for (CryptConfDetail cryptMap : conf.lstCrypt) {
					listApiCrypt.add(new CryptStarDetail(cryptMap.cryptId, cryptMap.appName, cryptMap.appId,
							cryptMap.keyLength));
				}
				
				cryptApp.loadListCrypt(conf.apiPortListen, conf.cryptServerUrl, listApiCrypt);
				logger.info("load list key crypt complete!");
			}
		} catch (Exception e) {
			logger.error("on load crypt key error!", e);
			System.exit(0);
		}
		
		//de su dung: CryptStarApi apiCrypt = CryptStarApp.getInstall().buidCrypt();
	}

	public static class CryptConf {
		public String cryptServerUrl;
		public int apiPortListen;
		public List<CryptConfDetail> lstCrypt;
	}

	public static class CryptConfDetail {
		public String cryptId;
		public String appName;
		public String appId;
		public AESKeyLength keyLength;
	}
	
}

