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
import de.visiom.carpc.asb.servicemodel.valueobjects.ValueObject;
import de.visiom.carpc.asb.serviceregistry.ServiceRegistry;
import de.visiom.carpc.asb.serviceregistry.exceptions.NoSuchServiceException;
import de.visiom.carpc.services.weather.helpers.LocationCoordinatesMapper;
import de.visiom.carpc.services.weather.helpers.TokenManager;
import de.visiom.carpc.services.weather.helpers.WeatherServiceConstants;
import de.visiom.carpc.services.weather.helpers.WeatherClient;
import de.visiom.carpc.services.weather.helpers.WhoElseClient;

public class RemoteMatchingsPublisher extends ParallelWorker {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteMatchingsPublisher.class);
	private StringParameter remoteMatchingsParameter;
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
		remoteMatchingsParameter = (StringParameter) thisService.getParameter("remoteMatchings");
		
		initializeRemoteMatchings(thisService);
		whoelseClient = new WhoElseClient();
	}
	
	@Override
	public long getExecutionInterval() {
		return 10000;
	}
	
	@Override
	public void execute() {
		fireUpdate();
	}
	
	public void fireUpdate() {
		LOG.info("Tried to read USERTOKEN in PushRoutePublisher as {}", TokenManager.getToken());
		whoelseClient.setUserToken(TokenManager.getToken());
		ValueObject valueObject = StringValueObject.valueOf(whoelseClient.getMatchings());
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(remoteMatchingsParameter, valueObject);
		eventPublisher.publishValueChange(valueChangeEvent);
	}
	
	public void initializeRemoteMatchings(Service thisService) throws NoSuchParameterException {
		StringParameter initialMatchings = (StringParameter) thisService.getParameter("remoteMatchings");
		StringValueObject initialMatchingsValue = StringValueObject.valueOf("{}");
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(initialMatchings, initialMatchingsValue);
		eventPublisher.publishValueChange(valueChangeEvent);
	}
	
	public void doSth() {
		
	}
	

}
