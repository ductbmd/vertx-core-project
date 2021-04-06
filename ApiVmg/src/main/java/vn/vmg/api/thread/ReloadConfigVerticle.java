package vn.vmg.api.thread;

import javax.inject.Inject;
import org.apache.log4j.Logger;
import io.vertx.core.AbstractVerticle;
import vn.vmg.api.common.utils.ApiUtils;

public class ReloadConfigVerticle extends AbstractVerticle{
	private Logger logger=Logger.getLogger(ReloadConfigVerticle.class);
	@Inject public ReloadConfigVerticle() {
		
	}
	
	@Override
	public void start() throws Exception {
		//add thread check reload config with vertx blocking
		vertx.executeBlocking(r ->{
			while(true) {
				
//				logger.info("Load config complete!!!");
				ApiUtils.sleep(30000);
			}
		}, r->{
			logger.info("Complete");
		});
	}
	
	
}
