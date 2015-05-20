package de.visiom.carpc.services.weather.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.messagebus.handlers.ValueChangeEventHandler;
import de.visiom.carpc.asb.servicemodel.parameters.Parameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StateValueObject;
import de.visiom.carpc.services.weather.publishers.RemoteTemperaturePublisher;

public class RemoteLocationUpdateHandler extends ValueChangeEventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteLocationUpdateHandler.class);
	
	private RemoteTemperaturePublisher remoteTemperaturePublisher;
	
	public void setRemoteTemperaturePublisher(RemoteTemperaturePublisher remoteTemperaturePublisher) {
		this.remoteTemperaturePublisher = remoteTemperaturePublisher;
	}
	
	@Override
	public void onValueChangeEvent(ValueChangeEvent valueChangeEvent) {
		StateValueObject stateValueObject = (StateValueObject) valueChangeEvent.getValue();
		Parameter parameter = valueChangeEvent.getParameter();
		String value = stateValueObject.getValue();
		LOG.info("Received an update for {}/{}:{}", parameter.getName(), parameter.getService().getName(), value);
		remoteTemperaturePublisher.setLocation(value);
	}
}
