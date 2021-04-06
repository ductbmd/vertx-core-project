package vn.vmg.api.common;

import org.apache.log4j.Logger;
import vn.vmg.api.crypt.CryptStarApp;

import java.io.IOException;

public class CryptUtils {
    private static Logger logger=Logger.getLogger(CryptUtils.class);
	public static boolean runNewCrypt=false;
    public static String encrypt(String data) throws IOException, Exception {
    	
    	//cryptstart crypt data
        if(runNewCrypt) return CryptStarApp.getInstall().buidCrypt().enCryptStr(data);
        
        //oracle crypt old version
        return OraAesCrypt.getIns().encrypt(data);
    }

    public static synchronized String decrypt(String data) throws IOException, Exception {
        try {
            //cryptstart crypt data
            if (runNewCrypt) return CryptStarApp.getInstall().buidCrypt().deCryptStr(data);

            //oracle crypt old version
            return OraAesCrypt.getIns().decrypt(data);
        }catch (Exception e){
            logger.error("Decrypt '" + data + "' error",e);
            throw e;
        }
    }
    
    
    public static void main(String[] args) throws Exception {
		SerConfig.init();
//		System.out.println(CryptUtils.decrypt("A6EDCB10ABDFEF8EA6E095CF8BAC7E5D"));
//		System.out.println(CryptUtils.decrypt("33AED964749F38C66D9F4B55C5FFB33C"));
//		System.out.println(CryptUtils.decrypt("33D27D41BD2BB47543C8C3C87B3AC57F"));
//		System.out.println(CryptUtils.encrypt("012067527"));
//		System.out.println("123.12312.123".split("[.]").length);
		
		System.out.println(CryptUtils.encrypt("12312312")+"|");
	}
}
