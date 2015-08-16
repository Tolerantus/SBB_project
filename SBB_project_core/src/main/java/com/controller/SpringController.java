package com.controller;


import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dto.FilteredJourneysInfo;
import com.dto.AllRoutesInfo;
import com.dto.ChoosedJourney;
import com.dto.CodeRole;
import com.dto.DirectionData;
import com.dto.HoursMinutesCost;
import com.dto.InitDBRequest;
import com.dto.JourneyAndPassengers;
import com.dto.JourneysInfo;
import com.dto.ListOfTickets;
import com.dto.MoneyPutRequest;
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
				resp.sendRedirect("../login?mail");
			} else {
				resp.sendRedirect("../login?wrongCode");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
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
			LOG.error(e.getMessage() + " " + e.getCause());
		} 
	}
	
	@RequestMapping(value = "/cash", method = RequestMethod.GET)
	@ResponseBody
	public Double checkCash() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUser = auth.getName();
	    try {
			return (Double) dispatcher.service(currentUser);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return null;
		}
	}
	@RequestMapping(value = "/putMoney", method = RequestMethod.POST)
	@ResponseBody
	public String putMoney(HttpServletRequest req) {
		String cash = req.getParameter("money");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userName = auth.getName();
		MoneyPutRequest putRequest = new MoneyPutRequest(userName, cash);
		try {
			dispatcher.service(putRequest);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "fail";
		}
		return "success";
	}
	@RequestMapping(value = {"/menu", "/"}, method = RequestMethod.GET)
    public String returnToMenu(HttpServletRequest request, Model model) {
		return "Menu";
	}
	@RequestMapping(value = "/newUserForm", method = RequestMethod.GET)
	public String newUserForm(HttpServletResponse resp) {
		return "NewUser";
	}
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String newManagerForm(HttpServletResponse resp) {
		return "manager";
	}
	@RequestMapping(value = "/sendLetterToManager", method = RequestMethod.POST)
	@ResponseBody
	public String createManager(HttpServletRequest req) {
		String username = req.getParameter("email");
		String password = req.getParameter("password");
		NewUserInfo userInfo = new NewUserInfo(username, password, true);
		UserExist user = null;
		try {
			user = (UserExist)dispatcher.service(userInfo);
		
			if (!user.isExist()){
				return "success";
			}else{
				return "exist";
			}
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "fail";
		}
	}
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public void newUser(HttpServletRequest request, HttpServletResponse resp, Model model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password1");
		NewUserInfo userInfo = new NewUserInfo(username, password, false);
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
			LOG.error(e.getMessage() + " " + e.getCause());
		}
		if (!container.getStations().isEmpty())
			session.setAttribute("allStations", container.getStations());
		model.addAttribute("simpleShedule", true);
		return "stationChooseAjax";
	}

	@RequestMapping(value="/searchJourneys/{station}", method = RequestMethod.GET,
			produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
			consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> searchJourneys(@PathVariable String station) {
		StationsForSheduling sts = new StationsForSheduling(station, null, null, null);
		JourneysInfo info = null;
		try {
			info = (JourneysInfo)dispatcher.service(sts);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
		}
		if (info.getJourneyStringData() == null){
			return null;
		}else{
			return info.getJourneyStringData();
		}
	}
	
	
	@RequestMapping(value="/searchJourneys", method = RequestMethod.GET)
	@ResponseBody
	public List<String> searchJourneys(HttpServletRequest req) {
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		String date = req.getParameter("date");
		HttpSession session = req.getSession();
		session.removeAttribute("journeysData");
		session.removeAttribute("helpInfo");
		StationsForSheduling sts = new StationsForSheduling(null, from, to, date);
		JourneysInfo info = null;
		try {
			info = (JourneysInfo)dispatcher.service(sts);
			session.setAttribute("journeysData", info.getJourneyStringData());
			session.setAttribute("helpInfo", info.getJourneyHelpInfo());
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
		}
		if (info.getJourneyStringData() == null){
			return new ArrayList<String>();
		}else{
			return info.getJourneyStringData();
		}
	}
	
	@RequestMapping(value = "/journeys", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getBriefInfoJourneys(HttpServletRequest req) {
		String start = req.getParameter("start");
		String stop = req.getParameter("stop");
		FilteredJourneysInfo info = new FilteredJourneysInfo(start, stop, null);
		try {
			info = (FilteredJourneysInfo) dispatcher.service(info);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
		}
		return info.getJourneys();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/registerPassenger", method = RequestMethod.POST)
	@ResponseBody
	public String regPassenger(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String journeyId = req.getParameter("journey");
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String birthday = req.getParameter("birthday");
		 
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUser = auth.getName();
		List<String> journeys = (List<String>) session.getAttribute("helpInfo");
		ChoosedJourney journey = new ChoosedJourney(journeyId, journeys,0);
		TicketInfo ticketInfo = null;
		try {
			journey = (ChoosedJourney)dispatcher.service(journey);
			String passengerDepAndDestStations = journey.getJourneyId();
			List<String> allJourneysData = (List<String>)session.getAttribute("journeysData");
			PassengerInfo passengerInfo = new PassengerInfo
					(currentUser, passengerDepAndDestStations, name, surname, birthday, allJourneysData);
			ticketInfo = (TicketInfo) dispatcher.service(passengerInfo);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "fail";
		}
		if (ticketInfo.isExist()) {
			return "exist";
		} else
		if (ticketInfo.getTicketInfo() == null) {
			return "money";
		} else {
			return ticketInfo.getTicketInfo();
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
			LOG.error(e.getMessage() + " " + e.getCause());
			return "500";
		}
		if (!container.getStations().isEmpty())
		session.setAttribute("allStations", container.getStations());
		return "RouteCreator";
	}
	@RequestMapping(value="/newRoute/newStartAndFinish", method = RequestMethod.POST)
	public String createStartFinish(HttpServletRequest request,	Model model) {
		HttpSession session = request.getSession();
		String typedDep = request.getParameter("dep");
		String typedArr = request.getParameter("arr");
		String selectDep = request.getParameter("old_st_dep");
		String selectArr = request.getParameter("old_st_arr");
		session.removeAttribute("newRoute");
		NewRouteStartAndFinish stations = new NewRouteStartAndFinish(typedDep, typedArr, selectDep, selectArr);
		RouteStationList route = null;
		try {
			route = (RouteStationList) dispatcher.service(stations);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
		}
		if (route.getRoute() == null){
			return "RouteCreator";
		}else{
			session.setAttribute("newRoute", route.getRoute());
			return "StationAdding";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/newRoute/delete", method = RequestMethod.GET)
	public String deleteStation(HttpServletRequest req, HttpSession session) {
		
		String station = req.getParameter("station");
		List<String> stations = (List<String>) session.getAttribute("newRoute");
		session.removeAttribute("newRoute");
		Iterator<String> iter = stations.iterator();
		while (iter.hasNext()) {
			String target = iter.next();
			if (target.equals(station)) {
				iter.remove();
			}
		}
		if (stations.size()<2) {
			return "RouteCreator";
		} else {
			session.setAttribute("newRoute", stations);
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
			LOG.error(e.getMessage() + " " + e.getCause());
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
	@RequestMapping(value="/train", method = RequestMethod.GET)
	@ResponseBody
	public String createTrain(HttpServletRequest req) {
		String trainCapacity = req.getParameter("seats");
		NewTrainInfo train = new NewTrainInfo(trainCapacity);
		try {
			dispatcher.service(train);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "fail";
		}
		return "success";
	}
	@RequestMapping(value="/station", method = RequestMethod.GET)
	@ResponseBody
	public String createStation(HttpServletRequest req) {
		String station = req.getParameter("station");
		NewStationInfo info = new NewStationInfo(station,false);
		try {
			info = (NewStationInfo) dispatcher.service(info);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "fail";
		}
		if (info.isExist()){
			return "exist";
		}else{
			return "success";
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
			LOG.error(e.getMessage() + " " + e.getCause());
			return "500";
		}
		session.setAttribute("routes", routes.getRoutes());
		return "ChoosingRoute";
	}
	@RequestMapping(value="/journey", method = RequestMethod.GET)
	@ResponseBody
	public String createJourney(HttpServletRequest req) {
		String routeInfo = req.getParameter("route");
		String date = req.getParameter("date");
		String time = req.getParameter("time");
		NewJourneyInfo journey = new NewJourneyInfo(routeInfo, date, time, null, null, null, false);
		try {
			journey = (NewJourneyInfo) dispatcher.service(journey);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "fail";
		}
		if (journey.getDate().equals("") || journey.getTime().equals("")){
			return "time";
		}else
		if (journey.isTrainsLack()){
			return "train";
		}else
		if (journey.getTrain() == null){
			return "fail";
		}else{
			return "<p>Journey planned: #" + journey.getJourneyId() + ";</p> <p>route " + journey.getRouteName() +
					";</p> <p>department " + journey.getDate() + " " + journey.getTime() + ";</p> <p>train #" + journey.getTrain() + "</p>";
		}
	}

	@RequestMapping(value="/journeyList", method = RequestMethod.GET)
	public String getJourneyList(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("journeys");
		FilteredJourneysInfo info = new FilteredJourneysInfo(null, null, null);
		try {
			info = (FilteredJourneysInfo) dispatcher.service(info);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			return "500";
		}
		session.setAttribute("journeys", info.getJourneys());
		return "JourneyChoosing";
	}
	@RequestMapping(value="/passengers", method = RequestMethod.GET)
	@ResponseBody
	public List<String> showPassengers(HttpServletRequest req) {
		List<String> resp = new ArrayList<String>();
		String journeyInfo = req.getParameter("journey");
		JourneyAndPassengers journey = new JourneyAndPassengers(journeyInfo, null);
		try {
			journey = (JourneyAndPassengers) dispatcher.service(journey);
		} catch (Exception e) {
			LOG.error(e.getMessage() + " " + e.getCause());
			String error = "fail"; resp.add(error);
			return resp;
		}
		if (journey.getPassInfo()==null){
			return resp;
		}else{
			return journey.getPassInfo();
		}
	}

	@RequestMapping(value="/resetDB", method = RequestMethod.GET)
	public void resetDB(HttpServletResponse resp) {
		if (isAdmin())
			try {
				dispatcher.service(new ResetRequest());
				resp.sendRedirect("/SBB_project_core/logout");
			} catch (Exception e) {
				LOG.error(e.getMessage() + " " + e.getCause());
			}
	}
	@RequestMapping(value="/initDB", method = RequestMethod.GET)
	public void initDB(HttpServletResponse resp) {
		if (isAdmin())
			try { 
				dispatcher.service(new InitDBRequest());
				resp.sendRedirect("/SBB_project_core/logout");
			} catch (Exception e) {
				LOG.error(e.getMessage() + " " + e.getCause());
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
