package vn.vmg.api.common;

import org.apache.log4j.PropertyConfigurator;

import vn.vmg.api.common.utils.JsonUtils;
import vn.vmg.api.common.utils.ParamMain;
import vn.vmg.api.dto.RouteConfig;

import java.io.IOException;

public class SerConfig {

	// config file app
	private static final String appConfDir = ParamMain.onCreateStr("config.server_app_conf", "etc/app.json", false);

	public static void init() throws IOException {
		app = JsonUtils.mapFileToClass(appConfDir, AppConf.class, true);
		routeStandar = JsonUtils.mapFileToClass(app.routeConfig, RouteConfig.class, true);

		// load config log4j
		PropertyConfigurator.configure(app.log4j);

		if (SerConfig.app.runNewCrypt)
			CryptLoad.onLoad();
		CryptUtils.runNewCrypt = SerConfig.app.runNewCrypt;
		UrlTrans.DEFAULT_TIME_OUT=SerConfig.app.urlTransTimeout;
	}

	public static AppConf app;
	public static RouteConfig routeStandar;

	public static class AppConf {
		public int serverPort = 8088;
		
		public String log4j = "etc/log4j.cfg";
		
		// oracle database
		public String dbConfig = "etc/db.json";

		// Mongo database
		public String dbMongoApp = "etc/dbMongoApp.json";

		// crypt config
		public String cryptConfDir = "etc/cryptConf.json";

		public String templateImportKYC 	= "template/import_infosky_template.xls";
		public String downloadTempImport  	= "downloads/exportTemplateImport";
		
		public String oraAesKey = "1111111111111111111111111111111";
		public boolean runNewCrypt = false;

		
		public String routeConfig = "etc/routeStandard.json";

		/**
		 * Cau hinh timeout eventbus
		 */
		// constant request
		public int routeTimeOutStandard = 60000;
		public int vertxWorkerMaxThreadStandard = 300;
		public int vertxWorkerMaxTimeExcuteStandard = 300000;
		
		public int urlTransTimeout=60000;

		public String timeZone = "Asia/Bangkok";

	}

}
