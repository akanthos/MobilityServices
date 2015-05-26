package de.visiom.carpc.services.weather.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.messagebus.handlers.ValueChangeEventHandler;
import de.visiom.carpc.asb.servicemodel.parameters.Parameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StringValueObject;
import de.visiom.carpc.services.weather.publishers.PushRoutePublisher;

public class PushRouteUpdateHandler extends ValueChangeEventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(PushRouteUpdateHandler.class);
	
	private PushRoutePublisher pushRoutePublisher;
	
	public void setPushRoutePublisher(PushRoutePublisher pushRoutePublisher) {
		this.pushRoutePublisher = pushRoutePublisher;
	}
	
	@Override
	public void onValueChangeEvent(ValueChangeEvent valueChangeEvent) {
		StringValueObject stateValueObject = (StringValueObject) valueChangeEvent.getValue();
		Parameter parameter = valueChangeEvent.getParameter();
		String value = stateValueObject.getValue();
		LOG.info("Received Push Route update for  {}/{}:{}", parameter.getName(), parameter.getService().getName(), value);
		pushRoutePublisher.setRoute(value);
	}
}
