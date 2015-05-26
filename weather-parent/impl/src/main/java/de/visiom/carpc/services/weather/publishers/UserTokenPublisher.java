package de.visiom.carpc.services.weather.publishers;

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
import de.visiom.carpc.services.weather.helpers.WeatherServiceConstants;

public class UserTokenPublisher extends ParallelWorker {
	private EventPublisher eventPublisher;
	private ServiceRegistry serviceRegistry;

	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	@Override
	public void initialize() throws NoSuchParameterException, NoSuchServiceException, UninitalizedValueException {

		
		Service thisService = serviceRegistry.getService(WeatherServiceConstants.SERVICE_NAME);
		
		initializeUserToken(thisService);
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
	}
	
	public void initializeUserToken(Service thisService) throws NoSuchParameterException {
		StringParameter initialUserToken = (StringParameter) thisService.getParameter("userToken");
		StringValueObject initialUserTokenValue = StringValueObject.valueOf("Dimitris");
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(initialUserToken, initialUserTokenValue);
		eventPublisher.publishValueChange(valueChangeEvent);
//		return initialUserTokenValue;
	}
	
	public void doSomething(){
		
	}

}
