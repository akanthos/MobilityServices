package de.visiom.carpc.services.weather.publishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.visiom.carpc.asb.messagebus.EventPublisher;
import de.visiom.carpc.asb.messagebus.async.ParallelWorker;
import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.parametervalueregistry.exceptions.UninitalizedValueException;
import de.visiom.carpc.asb.servicemodel.Service;
import de.visiom.carpc.asb.servicemodel.exceptions.NoSuchParameterException;
import de.visiom.carpc.asb.servicemodel.parameters.StringParameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StringValueObject;
import de.visiom.carpc.asb.serviceregistry.ServiceRegistry;
import de.visiom.carpc.asb.serviceregistry.exceptions.NoSuchServiceException;
import de.visiom.carpc.services.weather.helpers.TokenManager;
import de.visiom.carpc.services.weather.helpers.WeatherServiceConstants;
import de.visiom.carpc.services.weather.helpers.WhoElseClient;

public class PushRoutePublisher extends ParallelWorker {
	private static final Logger LOG = LoggerFactory.getLogger(PushRoutePublisher.class);
//	private StringParameter pushRouteParameter;
	private EventPublisher eventPublisher;
	private ServiceRegistry serviceRegistry;
	private WhoElseClient whoelseClient;
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	@Override
	public void initialize() throws NoSuchParameterException, NoSuchServiceException, UninitalizedValueException {

		
		Service thisService = serviceRegistry.getService(WeatherServiceConstants.SERVICE_NAME);
//		pushRouteParameter = (StringParameter) thisService.getParameter("pushRoute");
		whoelseClient = new WhoElseClient();
		
		initializePushRoute(thisService);
	}
	
	@Override
	public long getExecutionInterval() {
		return 10000;
	}
	
	@Override
	public void execute() {
//		fireUpdate();
	}
	
	
	public void setRoute(String route) {
		LOG.info("Tried to read USERTOKEN in PushRoutePublisher as {}", TokenManager.getToken());
		whoelseClient.setUserToken(TokenManager.getToken());
		whoelseClient.pushRoute(route);
	}
	
	public void initializePushRoute(Service thisService) throws NoSuchParameterException {
		StringParameter initialPushRoute = (StringParameter) thisService.getParameter("pushRoute");
		StringValueObject initialPushRouteValue = StringValueObject.valueOf("{}");
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(initialPushRoute, initialPushRouteValue);
		eventPublisher.publishValueChange(valueChangeEvent);
//		return initialUserTokenValue;
	}
	
	

}
