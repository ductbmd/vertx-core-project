package vn.vmg.api.impl;

import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import vn.vmg.api.common.utils.ApiUtils;

public abstract class EventBusBaseImpl extends AbstractVerticle {
	protected Logger logger = this.getLogger();

	public abstract Logger getLogger();

	protected void createEventBus(EventIdEnum eventId, EventHandle handleIn) {
		EventBus event = vertx.eventBus();
		
		event.consumer(eventId.toString(), r -> {
			vertx.executeBlocking(f -> {
				try {
					r.reply(handleIn.process(ApiUtils.toStr(r.body())));
				} catch (Exception e) {
					logger.error("this event [" + eventId + "] has some error! ", e);
					r.fail(400, "Something went wrong!");
				}
				f.complete();
			}, false, err -> {
			});
		});

	}


	@FunctionalInterface
	protected interface EventHandle {
		public String process(String body) throws Exception;
	}

}
