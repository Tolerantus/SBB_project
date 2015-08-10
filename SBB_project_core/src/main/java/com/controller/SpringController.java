package com.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dto.AllJourneysInfo;
import com.dto.AllRoutesInfo;
import com.dto.ChoosedJourney;
import com.dto.CodeRole;
import com.dto.DirectionData;
import com.dto.HoursMinutesCost;
import com.dto.InitDBRequest;
import com.dto.JourneyAndPassengers;
import com.dto.JourneysInfo;
import com.dto.ListOfTickets;
import com.dto.NewJourneyInfo;
import com.dto.NewRouteStartAndFinish;
import com.dto.NewRouteSummary;
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
import com.dto.TicketInfo;
import com.dto.UserExist;
import com.dto.UserInfo;
import com.dto.UserLoginContainer;
import com.entities.UserAccessCode;
import com.service.Dispatcher;


@Controller
public class SpringController {
	private static final Logger LOG = Logger.getLogger("springController");
	@Autowired
	private Dispatcher dispatcher;
	

	@RequestMapping(value = "/userVerification/{accessCode}")
	public void activateUserAccount(@PathVariable String accessCode, HttpServletResponse resp) {
		UserAccessCode userAccessCode = new UserAccessCode();
		userAccessCode.setCode(accessCode);
		CodeRole codeRole = new CodeRole();
		codeRole.setUserAccessCode(userAccessCode); 
		codeRole.setAdmin(false);
		try {
			CodeRole result = (CodeRole) dispatcher.service(codeRole);
			
			if (result.getUserAccessCode().getUser() != null) {
				resp.sendRedirect("../login?mail");
			} else {
				resp.sendRedirect("../login?wrongCode");
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
	
	
	@RequestMapping(value = "/adminVerification/{accessCode}")
	public void activateAdminAccount(@PathVariable String accessCode, HttpServletResponse resp) {
		UserAccessCode userAccessCode = new UserAccessCode();
		userAccessCode.setCode(accessCode);
		CodeRole codeRole = new CodeRole();
		codeRole.setUserAccessCode(userAccessCode); 
		codeRole.setAdmin(true);
		try {
			codeRole = (CodeRole) dispatcher.service(codeRole);
			if (codeRole.getUserAccessCode().getUser() != null) {
				resp.sendRedirect("../login?checkEmail");
			} else {
				resp.sendRedirect("../login?wrongCode");
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
	
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		@RequestParam(value = "mail", required = false) String mail,
		@RequestParam(value = "wrongCode", required = false) String wrongCode,
		@RequestParam(value = "checkEmail", required = false) String checkEmail,
		@RequestParam(value = "internalError", required = false) String internalError) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		if (mail != null) {
			model.addObject("msg", "E-mail was verified.");
		}
		
		if (wrongCode != null) {
			model.addObject("error", "Wrong access code");
		}
		
		if (checkEmail != null) {
			model.addObject("msg", "Check your e-mail for confirmation letter.");
		}
		
		if (internalError != null) {
			model.addObject("err", "Internal server error.");
		}
		model.setViewName("login");

		return model;

	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.logout();
			resp.sendRedirect("/SBB_project_core/login?logout");
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			throw new RuntimeException(e);
		} 
	}
	
	
	
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
    public String validateUser(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String login = request.getParameter("username");
		String password = request.getParameter("password");
		UserInfo userInfo = new UserInfo(login, password);
		UserExist user = null;
		try {
			user = (UserExist)dispatcher.service(userInfo);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (user.isExist()){
			LOG.info("User " + login + " logged in");
			session.setAttribute("user", login);
			session.setAttribute("admin", user.isAdmin());
			
			return "Menu";
		}else{
			model.addAttribute("error", "invalid combination login/password");
			return "Auth";
		}
	}
	@RequestMapping(value = {"/menu", "/"}, method = RequestMethod.GET)
    public String returnToMenu(HttpServletRequest request, Model model) {
		return "Menu";
	}
	@RequestMapping(value = "/newUserForm", method = RequestMethod.GET)
	public String newUserForm(HttpServletResponse resp) {
		return "NewUser";
	}
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public void newUser(HttpServletRequest request, HttpServletResponse resp, Model model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password1");
		NewUserInfo userInfo = new NewUserInfo(username, password);
		UserExist user = null;
		try {
			user = (UserExist)dispatcher.service(userInfo);
		
			if (!user.isExist()){
				LOG.info("User "+username+" has been registered");
				resp.sendRedirect("/SBB_project_core/login?checkEmail");;
			}else{
				model.addAttribute("error", "This username is alerady used");
				resp.sendRedirect("/SBB_project_core/newUserForm");
			}
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			throw new RuntimeException(e);
		}
	}
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String getSchedule(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();	
		session.removeAttribute("allStations");
		StationContainer container = new StationContainer(null);
		try {
			container = (StationContainer)dispatcher.service(container);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
		}
		if (!container.getStations().isEmpty())
			session.setAttribute("allStations", container.getStations());
		model.addAttribute("simpleShedule", true);
		return "stationChoose";
	}
	@RequestMapping(value = "/appropriateJourneys", method = RequestMethod.POST)
    public String getAppropriateJourneys(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String singleStation = request.getParameter("station");
		String st_dep = request.getParameter("st_dep");
		String st_arr = request.getParameter("st_arr");
		String date = request.getParameter("date");
		session.removeAttribute("journeysData");
		session.removeAttribute("helpInfo");
		StationsForSheduling sts = new StationsForSheduling(singleStation, st_dep, st_arr,date);
		JourneysInfo info = null;
		try {
			info = (JourneysInfo)dispatcher.service(sts);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (info.getJourneyStringData() == null){
			model.addAttribute("error", true);
			if (singleStation != null)
			model.addAttribute("simpleShedule", true);
			return "stationChoose";
		}else{
			model.addAttribute("st_dep", st_dep);
			model.addAttribute("st_arr", st_arr);
			model.addAttribute("station", singleStation);
			session.setAttribute("journeysData", info.getJourneyStringData());
			session.setAttribute("helpInfo", info.getJourneyHelpInfo());
			if (info.getJourneyHelpInfo() == null){
				return "stationShedule";
			}else{
				return "apprJours";
			}
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appropriateJourneys/buyTicket", method = RequestMethod.POST)
    public String buyTicket(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String journeyId = request.getParameter("journey");
		session.removeAttribute("journeyData");
		session.removeAttribute("emptySeats");
		List<String> journeys = (List<String>) session.getAttribute("helpInfo");
		ChoosedJourney journey = new ChoosedJourney(journeyId, journeys,0);
		try {
			journey = (ChoosedJourney)dispatcher.service(journey);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		session.setAttribute("journeyData", journey.getJourneyId());
		session.setAttribute("emptySeats", journey.getEmptySeats());
		return "passengerRegistration";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appropriateJourneys/buyTicket/passenger", method = RequestMethod.POST)
    public String registerPassenger(HttpServletRequest request, Model model, HttpSession session) {
		String passengerDepAndDestStations = (String) session.getAttribute("journeyData");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUser = auth.getName();
		List<String> allJourneysData = (List<String>)session.getAttribute("journeysData");
		PassengerInfo passengerInfo = new PassengerInfo
				(currentUser, passengerDepAndDestStations, name, surname, year, month, day, allJourneysData);
		TicketInfo ticketInfo;
		try {
			ticketInfo = (TicketInfo) dispatcher.service(passengerInfo);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (ticketInfo.isExist()){
			model.addAttribute("error", true);
			return "passengerRegistration";
		}else{
			model.addAttribute("ticketInfo", ticketInfo.getTicketInfo());
			return "NewTicket";
		}
	}
	@RequestMapping(value = "/stationsChoosing", method = RequestMethod.GET)
    public String findJourney(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("allStations");
		StationContainer container = new StationContainer(null);
		try {
			container = (StationContainer)dispatcher.service(container);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (!container.getStations().isEmpty())
		session.setAttribute("allStations", container.getStations());
		return "stationChoose";
	}
	@RequestMapping(value = "/myTickets", method = RequestMethod.GET)
    public String checkTickets(HttpServletRequest request, Model model) {
		
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		UserLoginContainer userLogin = new UserLoginContainer(user);
		ListOfTickets info;
		try {
			info = (ListOfTickets) dispatcher.service(userLogin);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		model.addAttribute("tickets", info.getTicketsInfo());
		return "tickets";
	}
	
	@RequestMapping(value="/newRoute", method = RequestMethod.GET)
	public String createRoute(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("allStations");
		StationContainer container = new StationContainer(null);
		try {
			container = (StationContainer)dispatcher.service(container);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (!container.getStations().isEmpty())
		session.setAttribute("allStations", container.getStations());
		return "RouteCreator";
	}
	@RequestMapping(value="/newRoute/newStartAndFinish", method = RequestMethod.POST)
	public String createStartFinish(HttpServletRequest request,	Model model) {
		HttpSession session = request.getSession();
		String typed_dep = request.getParameter("dep");
		String typed_arr = request.getParameter("arr");
		String select_dep = request.getParameter("old_st_dep");
		String select_arr = request.getParameter("old_st_arr");
		session.removeAttribute("newRoute");
		NewRouteStartAndFinish stations = new NewRouteStartAndFinish(typed_dep, typed_arr, select_dep, select_arr);
		RouteStationList route = null;
		try {
			route = (RouteStationList) dispatcher.service(stations);
		} catch (Exception e) {
			
		}
		if (route.getRoute() == null){
			return "RouteCreator";
		}else{
			session.setAttribute("newRoute", route.getRoute());
			return "StationAdding";
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/newRoute/newStartAndFinish/newStation", method = RequestMethod.POST)
	public String injectNewStationInRoute(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String newStation = request.getParameter("newStation");
		String selectedStation = request.getParameter("stations");
		String step = request.getParameter("step");
		RouteStationList route = new RouteStationList((List<String>) session.getAttribute("newRoute"));
		StationForInsertInRoute station = new StationForInsertInRoute(newStation, selectedStation, step, route);
		try {
			route = (RouteStationList) dispatcher.service(station);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		session.setAttribute("newRoute", route.getRoute());
		return "StationAdding";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/newRoute/newStartAndFinish/timeAndCost", method = RequestMethod.POST)
	public String inputTimeAndCost(HttpServletRequest request) {
		HttpSession session = request.getSession();
		RouteStationList route = new RouteStationList((List<String>) session.getAttribute("newRoute"));
		DirectionData directionData;
		try {
			directionData = (DirectionData) dispatcher.service(route);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		session.setAttribute("requiredDirectionData", directionData.getDirections());
		return "inputDirections";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/newRoute/newStartAndFinish/timeAndCost/newRoute", method = RequestMethod.POST)
	public String buildNewRoute(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("info");
		List<String> newRoute = (List<String>) session.getAttribute("newRoute");
		List<String> newDirections = (List<String>) session.getAttribute("requiredDirectionData");
		List<HoursMinutesCost> data = new ArrayList<HoursMinutesCost>();
		String routename = request.getParameter("route");
		for (String direction : newDirections){
			String hours = request.getParameter(direction+"-time-hours");
			String minutes = request.getParameter(direction+"-time-minutes");
			String cost = request.getParameter(direction+"-cost");
			data.add(new HoursMinutesCost(hours, minutes, cost));
		}
		NewRouteSummary summary;
		try {
			summary = (NewRouteSummary) dispatcher.service
					(new RequiredDataForNewRoute(newRoute, newDirections, data, routename));
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (summary.getSummary() == null){
			model.addAttribute("error", true);
			return "inputDirections";
		}else{
			session.setAttribute("info", summary.getSummary());
			return "newRouteInfo";
		}
	}
	@RequestMapping(value="/creatingTrain", method = RequestMethod.GET)
	public String showNewTrainForm(HttpServletRequest request, Model model) {
		return "TrainCreator";
	}
	
	@RequestMapping(value="/newTrain", method = RequestMethod.POST)
	public void createTrain(HttpServletRequest request, HttpServletResponse resp) {
		String trainCapacity = request.getParameter("seats");
		NewTrainInfo train = new NewTrainInfo(trainCapacity);
		try {
			dispatcher.service(train);
			resp.sendRedirect("/SBB_project_core/");
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
		}
	}
	@RequestMapping(value="/routesInfo", method = RequestMethod.GET)
	public String getRoutesInfo(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("routes");
		AllRoutesInfo routes = new AllRoutesInfo(null);
		try {
			routes = (AllRoutesInfo) dispatcher.service(routes);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		session.setAttribute("routes", routes.getRoutes());
		return "ChoosingRoute";
	}
	@RequestMapping(value="/routesInfo/newJourney", method = RequestMethod.POST)
	public String planNewJourney(HttpServletRequest request, Model model) {
		String routeInfo = request.getParameter("route");
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		NewJourneyInfo journey = new NewJourneyInfo(routeInfo, date, time, null, null, null, false);
		try {
			journey = (NewJourneyInfo) dispatcher.service(journey);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (journey.getDate()==""||journey.getTime()==""){
			model.addAttribute("err", true);
			return "ChoosingRoute";
		}else
		if (journey.isTrainsLack()){
			model.addAttribute("trainsLack", true);
			return "ChoosingRoute";
		}else
		if (journey.getTrain() == null){
			model.addAttribute("err", true);
			return "ChoosingRoute";
		}else{
			model.addAttribute("id", journey.getJourneyId());
			model.addAttribute("route", journey.getRouteName());
			model.addAttribute("date", journey.getDate());
			model.addAttribute("train", journey.getTrain());
			return "CreatingJourneySuccess";
		}
	}
	@RequestMapping(value="/newStationForm", method = RequestMethod.GET)
	public String showNewStationForm(HttpServletRequest request, Model model) {
		return "StationCreator";
	}
	@RequestMapping(value="/newStation", method = RequestMethod.POST)
	public String createStation(HttpServletRequest request, Model model) {
		String station = request.getParameter("station");
		NewStationInfo info = new NewStationInfo(station,false);
		try {
			info = (NewStationInfo) dispatcher.service(info);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (info.isExist()){
			model.addAttribute("err", true);
			return "StationCreator";
		}else{
			return "Menu";
		}
	}
	@RequestMapping(value="/journeyList", method = RequestMethod.GET)
	public String getJourneyList(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("journeys");
		AllJourneysInfo info = new AllJourneysInfo(null);
		try {
			info = (AllJourneysInfo) dispatcher.service(info);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		session.setAttribute("journeys", info.getJourneys());
		return "JourneyChoosing";
	}
	@RequestMapping(value="/journeyList/passengers", method = RequestMethod.POST)
	public String showPassengers(HttpServletRequest request, Model model) {
		String journeyInfo = request.getParameter("journey");
		JourneyAndPassengers journey = new JourneyAndPassengers(journeyInfo, null);
		try {
			journey = (JourneyAndPassengers) dispatcher.service(journey);
		} catch (Exception e) {
			LOG.error(e.getStackTrace());
			return "500";
		}
		if (journey.getPassInfo()==null){
			model.addAttribute("err", true);
			return "JourneyChoosing";
		}else{
			model.addAttribute("journey", journey.getJourneyInfo());
			model.addAttribute("passengers", journey.getPassInfo());
			return "Passengers";
		}
	}
	@RequestMapping(value="/resetDB", method = RequestMethod.GET)
	public void resetDB(HttpServletResponse resp) {
		if (isAdmin())
			try {
				dispatcher.service(new ResetRequest());
				resp.sendRedirect("/SBB_project_core/");
			} catch (Exception e) {
				LOG.error(e.getStackTrace());
			}
	}
	@RequestMapping(value="/initDB", method = RequestMethod.GET)
	public void initDB(HttpServletResponse resp) {
		if (isAdmin())
			try { 
				dispatcher.service(new InitDBRequest());
				resp.sendRedirect("/SBB_project_core/");
			} catch (Exception e) {
				LOG.error(e.getStackTrace());
				
			}
	}
	
	public boolean isAdmin() {
		boolean hasRole = false;
		for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				hasRole = true;
			}
		}
		return hasRole;
	}
}
