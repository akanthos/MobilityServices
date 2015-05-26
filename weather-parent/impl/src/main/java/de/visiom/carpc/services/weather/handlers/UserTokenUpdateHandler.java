package de.visiom.carpc.services.weather.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.messagebus.handlers.ValueChangeEventHandler;
import de.visiom.carpc.asb.servicemodel.parameters.Parameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StringValueObject;
import de.visiom.carpc.services.weather.publishers.UserTokenPublisher;

public class UserTokenUpdateHandler extends ValueChangeEventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(UserTokenUpdateHandler.class);
	
	private UserTokenPublisher userTokenPublisher;
	
	public void setUserTokenPublisher(UserTokenPublisher userTokenPublisher) {
		this.userTokenPublisher = userTokenPublisher;
	}
	
	@Override
	public void onValueChangeEvent(ValueChangeEvent valueChangeEvent) {
		StringValueObject stateValueObject = (StringValueObject) valueChangeEvent.getValue();
		Parameter parameter = valueChangeEvent.getParameter();
		String value = stateValueObject.getValue();
		LOG.info("Received User Token update for {}/{}:{}", parameter.getName(), parameter.getService().getName(), value);
		userTokenPublisher.doSomething();
	}
}
