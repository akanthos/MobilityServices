package de.visiom.carpc.services.weather.publishers;

import de.visiom.carpc.asb.messagebus.EventPublisher;
import de.visiom.carpc.asb.messagebus.async.ParallelWorker;
import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.parametervalueregistry.exceptions.UninitalizedValueException;
import de.visiom.carpc.asb.servicemodel.Service;
import de.visiom.carpc.asb.servicemodel.exceptions.NoSuchParameterException;
import de.visiom.carpc.asb.servicemodel.parameters.NumericParameter;
import de.visiom.carpc.asb.servicemodel.parameters.StateParameter;
import de.visiom.carpc.asb.servicemodel.parameters.StringParameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.NumberValueObject;
import de.visiom.carpc.asb.servicemodel.valueobjects.StateValueObject;
import de.visiom.carpc.asb.servicemodel.valueobjects.StringValueObject;
import de.visiom.carpc.asb.servicemodel.valueobjects.ValueObject;
import de.visiom.carpc.asb.serviceregistry.ServiceRegistry;
import de.visiom.carpc.asb.serviceregistry.exceptions.NoSuchServiceException;
import de.visiom.carpc.services.weather.helpers.LocationCoordinatesMapper;
import de.visiom.carpc.services.weather.helpers.ServiceConstants;
import de.visiom.carpc.services.weather.helpers.WeatherClient;
import de.visiom.carpc.services.weather.helpers.WhoElseClient;
import de.visiom.carpc.services.weather.helpers.UserTokenHolder;

public class NewRoutePublisher extends ParallelWorker {
	private StringParameter currentMatchingParameter;
	private EventPublisher eventPublisher;
	private ServiceRegistry serviceRegistry;
	private UserTokenHolder userTokenHolder;
	private WhoElseClient matchingClient;
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	private StringValueObject initializeCurrentUserToken(Service thisService) throws NoSuchParameterException, NoSuchServiceException {
		// Somehow fetch user token from parameter userToken
		StringParameter initialUserToken = (StringParameter) thisService.getParameter("userToken");
		StringValueObject initialUserTokenValue = StringValueObject.valueOf("");
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(initialUserToken, initialUserTokenValue);
		eventPublisher.publishValueChange(valueChangeEvent);
		return initialUserTokenValue;
	}
	
	@Override
	public void initialize() throws NoSuchParameterException, NoSuchServiceException, UninitalizedValueException {
		Service thisService = serviceRegistry.getService(ServiceConstants.SERVICE_NAME);
		currentMatchingParameter = (StringParameter) thisService.getParameter("currentMatching");
		
		StringValueObject initialUserTokenValue = initializeCurrentUserToken(thisService); 
		userTokenHolder = new UserTokenHolder(initialUserTokenValue.toString());
		matchingClient = new WhoElseClient(userTokenHolder.getUserToken());
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
		try {
			ValueObject valueObject = NumberValueObject.valueOf(matchingClient.getMatchings());
			ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(currentMatchingParameter, valueObject);
			eventPublisher.publishValueChange(valueChangeEvent);
		}
		catch(Exception e) {
			
		}
	}

}
