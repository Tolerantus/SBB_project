package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.AllJourneysInfo;
import com.dto.AllRoutesInfo;
import com.dto.ChoosedJourney;
import com.dto.InitDBRequest;
import com.dto.JourneyAndPassengers;
import com.dto.NewJourneyInfo;
import com.dto.NewRouteStartAndFinish;
import com.dto.NewStationInfo;
import com.dto.NewTrainInfo;
import com.dto.NewUserInfo;
import com.dto.PassengerInfo;
import com.dto.RequiredDataForNewRoute;
import com.dto.ResetRequest;
import com.dto.RouteStationList;
import com.dto.StationContainer;
import com.dto.StationForInsertInRoute;
import com.dto.StationsForSheduling;
import com.dto.TicketContainer;
import com.dto.UserInfo;
import com.dto.UserLoginContainer;
@Service("dispatcher")
public class Dispatcher {
	private CheckUser checkUser; 
	private UserRegistrator userRegistrator;
	private StationReturner stationReturner;
	private GetAppropriateJourneys getAppropriateJourneys;
	private SeatsChecker seatsChecker;
	private PassengerRegistrator passengerRegistrator;
	private TicketsChecker ticketsChecker;
	private RouteCreator routeCreator;
	private RouteStationInjector routeStationInjector;
	private RouteDirectionsChecker routeDirectionsChecker;
	private FinalRouteBuilder finalRouteBuilder;
	private TrainCreator trainCreator;
	private RoutesInformator routesInformator;
	private JourneyPlanner journeyPlanner;
	private StationCreator stationCreator;
	private JourneyBriefer journeyBriefer;
	private PassengersInformator passengersInformator;
	private Resetter resetter;
	private DBInitializer dbInitializer;
	private TicketService ticketService;
	
	public  Object service(Object dto) throws Exception{
		if (dto instanceof UserInfo){
			return checkUser.check((UserInfo)dto);
		} else
		if (dto instanceof NewUserInfo){
			return userRegistrator.register((NewUserInfo)dto);
		} else
		if (dto instanceof StationContainer){
			return stationReturner.getStations((StationContainer)dto);
		} else
		if (dto instanceof StationsForSheduling){
			return getAppropriateJourneys.getJourneys((StationsForSheduling) dto);
		} else
		if (dto instanceof ChoosedJourney){
			return seatsChecker.check((ChoosedJourney) dto);
		} else
		if (dto instanceof PassengerInfo){
			return passengerRegistrator.register((PassengerInfo) dto);
		} else
		if (dto instanceof UserLoginContainer){
			return ticketsChecker.check((UserLoginContainer) dto);
		} else
		if (dto instanceof NewRouteStartAndFinish){
			return routeCreator.append((NewRouteStartAndFinish) dto);
		} else
		if (dto instanceof StationForInsertInRoute){
			return routeStationInjector.inject((StationForInsertInRoute) dto);
		} else
		if (dto instanceof RouteStationList){
			return routeDirectionsChecker.check((RouteStationList) dto);
		} else
		if (dto instanceof RequiredDataForNewRoute){
			return finalRouteBuilder.build((RequiredDataForNewRoute) dto);
		} else
		if (dto instanceof NewTrainInfo){
			trainCreator.create((NewTrainInfo) dto);
		} else
		if (dto instanceof AllRoutesInfo){
			return routesInformator.getInfo((AllRoutesInfo) dto);
		} else
		if (dto instanceof NewJourneyInfo){
			return journeyPlanner.plan((NewJourneyInfo) dto);
		} else
		if (dto instanceof NewStationInfo){
			return stationCreator.create((NewStationInfo) dto);
		} else
		if (dto instanceof AllJourneysInfo){
			return journeyBriefer.getInfo((AllJourneysInfo) dto);
		} else
		if (dto instanceof JourneyAndPassengers){
			return passengersInformator.getInfo((JourneyAndPassengers) dto);
		} else
		if (dto instanceof ResetRequest){
			resetter.reset((ResetRequest) dto);
		} else
		if (dto instanceof InitDBRequest){
			dbInitializer.init((InitDBRequest) dto);
		} else
		if (dto instanceof TicketContainer) {
			return ticketService.dispatch((TicketContainer) dto);
		}
	return null;
	}
	@Autowired
	public void setCheckUser(CheckUser checkUser) {
		this.checkUser = checkUser;
	}
	@Autowired
	public void setUserRegistrator(UserRegistrator userRegistrator) {
		this.userRegistrator = userRegistrator;
	}
	@Autowired
	public void setStationReturner(StationReturner stationReturner) {
		this.stationReturner = stationReturner;
	}
	@Autowired
	public void setGetAppropriateJourneys(
			GetAppropriateJourneys getAppropriateJourneys) {
		this.getAppropriateJourneys = getAppropriateJourneys;
	}
	@Autowired
	public void setSeatsChecker(SeatsChecker seatsChecker) {
		this.seatsChecker = seatsChecker;
	}
	@Autowired
	public void setPassengerRegistrator(PassengerRegistrator passengerRegistrator) {
		this.passengerRegistrator = passengerRegistrator;
	}
	@Autowired
	public void setTicketsChecker(TicketsChecker ticketsChecker) {
		this.ticketsChecker = ticketsChecker;
	}
	@Autowired
	public void setRouteCreator(RouteCreator routeCreator) {
		this.routeCreator = routeCreator;
	}
	@Autowired
	public void setRouteStationInjector(RouteStationInjector routeStationInjector) {
		this.routeStationInjector = routeStationInjector;
	}
	@Autowired
	public void setRouteDirectionsChecker(
			RouteDirectionsChecker routeDirectionsChecker) {
		this.routeDirectionsChecker = routeDirectionsChecker;
	}
	@Autowired
	public void setFinalRouteBuilder(FinalRouteBuilder finalRouteBuilder) {
		this.finalRouteBuilder = finalRouteBuilder;
	}
	@Autowired
	public void setTrainCreator(TrainCreator trainCreator) {
		this.trainCreator = trainCreator;
	}
	@Autowired
	public void setRoutesInformator(RoutesInformator routesInformator) {
		this.routesInformator = routesInformator;
	}
	@Autowired
	public void setJourneyPlanner(JourneyPlanner journeyPlanner) {
		this.journeyPlanner = journeyPlanner;
	}
	@Autowired
	public void setStationCreator(StationCreator stationCreator) {
		this.stationCreator = stationCreator;
	}
	@Autowired
	public void setJourneyBriefer(JourneyBriefer journeyBriefer) {
		this.journeyBriefer = journeyBriefer;
	}
	@Autowired
	public void setPassengersInformator(PassengersInformator passengersInformator) {
		this.passengersInformator = passengersInformator;
	}
	@Autowired
	public void setResetter(Resetter resetter) {
		this.resetter = resetter;
	}
	@Autowired
	public void setDbInitializer(DBInitializer dbInitializer) {
		this.dbInitializer = dbInitializer;
	}
	@Autowired
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
}
