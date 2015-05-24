package de.visiom.carpc.services.weather.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.messagebus.handlers.ValueChangeEventHandler;
import de.visiom.carpc.asb.servicemodel.parameters.Parameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StateValueObject;
import de.visiom.carpc.asb.servicemodel.valueobjects.StringValueObject;
import de.visiom.carpc.services.weather.publishers.NewRoutePublisher;

public class RequestMatchingUpdateHandler extends ValueChangeEventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteLocationUpdateHandler.class);
	
	private NewRoutePublisher newRoutePublisher;
	
	public void setNewMatchingPublisher(NewRoutePublisher newRoutePublisher) {
		this.newRoutePublisher = newRoutePublisher;
	}
	
	@Override
	public void onValueChangeEvent(ValueChangeEvent valueChangeEvent) {
		StringValueObject stringValueObject = (StringValueObject) valueChangeEvent.getValue();
		Parameter parameter = valueChangeEvent.getParameter();
		String value = stringValueObject.getValue();
		LOG.info("Received a request for {}/{}:{}", parameter.getName(), parameter.getService().getName(), value);
		newRoutePublisher.fireUpdate();
	}
}
