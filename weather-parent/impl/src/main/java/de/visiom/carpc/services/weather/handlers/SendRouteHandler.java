package de.visiom.carpc.services.weather.handlers;

import de.visiom.carpc.asb.messagebus.CommandPublisher;
import de.visiom.carpc.asb.messagebus.EventPublisher;
import de.visiom.carpc.asb.messagebus.commands.GenericResponse;
import de.visiom.carpc.asb.messagebus.commands.Response;
import de.visiom.carpc.asb.messagebus.commands.ValueChangeRequest;
import de.visiom.carpc.asb.messagebus.events.ValueChangeEvent;
import de.visiom.carpc.asb.messagebus.handlers.ValueChangeRequestHandler;
import de.visiom.carpc.asb.servicemodel.parameters.Parameter;
import de.visiom.carpc.asb.servicemodel.valueobjects.StateValueObject;

public class SendRouteHandler extends ValueChangeRequestHandler {
	private CommandPublisher commandPublisher;
	private EventPublisher eventPublisher;
	
	public void setCommandPublisher(CommandPublisher commandPublisher) {
		this.commandPublisher = commandPublisher;
	}
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public void onValueChangeRequest(ValueChangeRequest request, Long requestID) {
		Parameter parameter = request.getParameter();
		Response response = null;
		if(parameter.getName().equals("remoteLocation")) {
			StateValueObject valueObject = (StateValueObject) request.getValue();
			ValueChangeEvent valueChangeEvent = ValueChangeEvent.createValueChangeEvent(parameter, valueObject);
			eventPublisher.publishValueChange(valueChangeEvent);
			response = GenericResponse.createGenericResponse(GenericResponse.STATUS_OK);
		}
		else {
			response = GenericResponse.createGenericResponse(GenericResponse.STATUS_ERROR);
		}
		commandPublisher.publishResponse(response, requestID, request.getParameter().getService());
	}
}
