package de.visiom.carpc.services.weather.publishers;

import de.visiom.carpc.asb.messagebus.EventPublisher;
import de.visiom.carpc.asb.messagebus.async.ParallelWorker;
import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.parametervalueregistry.exceptions.UninitalizedValueException;
import de.visiom.carpc.asb.servicemodel.Service;
import de.visiom.carpc.asb.servicemodel.exceptions.NoSuchParameterException;
import de.visiom.carpc.asb.servicemodel.parameters.NumericParameter;
import de.visiom.carpc.asb.servicemodel.parameters.StateParameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.NumberValueObject;
import de.visiom.carpc.asb.servicemodel.valueobjects.StateValueObject;
import de.visiom.carpc.asb.servicemodel.valueobjects.ValueObject;
import de.visiom.carpc.asb.serviceregistry.ServiceRegistry;
import de.visiom.carpc.asb.serviceregistry.exceptions.NoSuchServiceException;
import de.visiom.carpc.services.weather.helpers.LocationCoordinatesMapper;
import de.visiom.carpc.services.weather.helpers.WeatherServiceConstants;
import de.visiom.carpc.services.weather.helpers.WeatherClient;

public class RemoteTemperaturePublisher extends ParallelWorker {
	private NumericParameter remoteTemperatureParameter;
	private EventPublisher eventPublisher;
	private ServiceRegistry serviceRegistry;
	private LocationCoordinatesMapper locationCoordinatesMapper;
	private WeatherClient weatherClient;
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	private Double getCurrentLatitude() {
		return locationCoordinatesMapper.getLatitude();
	}
	
	private Double getCurrentLongitude() {
		return locationCoordinatesMapper.getLongitude();
	}
	
	@Override
	public void initialize() throws NoSuchParameterException, NoSuchServiceException, UninitalizedValueException {
		locationCoordinatesMapper = new LocationCoordinatesMapper();
		
		Service thisService = serviceRegistry.getService(WeatherServiceConstants.SERVICE_NAME);
		remoteTemperatureParameter = (NumericParameter) thisService.getParameter("remoteTemperature");
		
		
		StateValueObject initialRemoteLocationValue = initializeRemoteLocation(thisService);
		locationCoordinatesMapper.initialize(initialRemoteLocationValue.toString());
		
		weatherClient = new WeatherClient(getCurrentLatitude(), getCurrentLongitude());
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
		weatherClient.setLatitude(getCurrentLatitude());
		weatherClient.setLongitude(getCurrentLongitude());
		ValueObject valueObject = NumberValueObject.valueOf(weatherClient.getTemperature());
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(remoteTemperatureParameter, valueObject);
		eventPublisher.publishValueChange(valueChangeEvent);
	}
	
	public StateValueObject initializeRemoteLocation(Service thisService) throws NoSuchParameterException {
		StateParameter initialLocation = (StateParameter) thisService.getParameter("remoteLocation");
		StateValueObject initialLocationValue = StateValueObject.valueOf("Garching");
		ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(initialLocation, initialLocationValue);
		eventPublisher.publishValueChange(valueChangeEvent);
		return initialLocationValue;
	}
	
	public void setLocation(String locationName) {
		locationCoordinatesMapper.initialize(locationName);
		fireUpdate();
	}
	

}
