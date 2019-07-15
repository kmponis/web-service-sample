package com.web.service.sample.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource({ "classpath:response.properties" })
public class ResponseProperties {

	@Value("${object.saved}")
	public String objectSaved;

	@Value("${object.updated}")
	public String objectUpdated;

	@Value("${object.deleted}")
	public String objectDeleted;

	@Value("${error.object.not.saved}")
	public String errorObjectNotSaved;

	@Value("${error.object.doesnt.exists}")
	public String errorObjectDoesntExists;

	@Value("${thread.main.start}")
	public String threadMainStart;

	@Value("${thread.main.wait}")
	public String threadMainWait;

	@Value("${thread.main.notify.count0}")
	public String threadMainNotifyCount0;

	@Value("${thread.main.notify.count1}")
	public String threadMainNotifyCount1;

	@Value("${thread.main.notify.count2}")
	public String threadMainNotifyCount2;

	@Value("${thread.main.notify.count3}")
	public String threadMainNotifyCount3;

	@Value("${thread.main.notify.count4}")
	public String threadMainNotifyCount4;

	@Value("${thread.main.stop}")
	public String threadMainStop;

	@Value("${thread.interruptedException}")
	public String threadInterruptedException;

	@Value("${thread.illegalArgumentException}")
	public String threadIllegalArgumentException;

}
