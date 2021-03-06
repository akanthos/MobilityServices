package de.visiom.carpc.services.weather.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.messagebus.handlers.ValueChangeEventHandler;
import de.visiom.carpc.asb.servicemodel.parameters.Parameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StringValueObject;
import de.visiom.carpc.services.weather.publishers.RemoteMatchingsPublisher;

public class RemoteMatchingsUpdateHandler extends ValueChangeEventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteMatchingsUpdateHandler.class);
	
	private RemoteMatchingsPublisher remoteMatchingsPublisher;
	
	public void setRemoteTemperaturePublisher(RemoteMatchingsPublisher remoteMatchingsPublisher) {
		this.remoteMatchingsPublisher = remoteMatchingsPublisher;
	}
	
	@Override
	public void onValueChangeEvent(ValueChangeEvent valueChangeEvent) {
		StringValueObject stringValueObject = (StringValueObject) valueChangeEvent.getValue();
		Parameter parameter = valueChangeEvent.getParameter();
		String value = stringValueObject.getValue();
		LOG.info("Received an update for {}/{}:{}", parameter.getName(), parameter.getService().getName(), value);
		remoteMatchingsPublisher.doSth();
	}
}
