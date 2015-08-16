package com.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.entities.Direction;
import com.entities.Journey;
import com.entities.Passenger;
import com.entities.Route;
import com.entities.Seats;
import com.entities.Shedule;
import com.entities.Station;
import com.entities.Ticket;
import com.entities.Train;
import com.entities.User;
import com.entities.UserAccessCode;
import com.entities.UserAndTicket;
import com.entities.UserRole;

@Repository("dao")
public class Dao {
private Random random = new Random(47);
@Resource(name="sessionFactory")
private SessionFactory sessionFactory;

public Date getDate() {
	return new Date();
}
public  void createTrain( int seats) {
	Session session = sessionFactory.getCurrentSession();
	
	Train train = new Train();
	train.setTrainSeats(seats);
	session.save(train);
}


public  void initTrains() {
	clearTrains();
	for (int i=0; i<10; i++) {
		createTrain(100);
	}
}

public  Train getTrain( int trainId) {
	Session session = sessionFactory.getCurrentSession();
	return (Train)session.get(Train.class, trainId);
}
public  void deleteTrain( int trainId) {
	Session session = sessionFactory.getCurrentSession();
	Train t = (Train)session.get(Train.class, trainId);
	session.delete(t);
}
@SuppressWarnings("unchecked")
public  void clearTrains() {
	Session session = sessionFactory.getCurrentSession();
	List<Train> trains = session.createQuery("from Train").list();
	for (Train t : trains) {
		session.delete(t);
	}
}
public UserAccessCode createUserAccessCode(User user, String code) {
	Session session = sessionFactory.getCurrentSession();
	UserAccessCode userAccessCode = new UserAccessCode();
	userAccessCode.setUser(user);
	userAccessCode.setCode(code);
	session.save(userAccessCode);
	return userAccessCode;
}

@SuppressWarnings("unchecked")
public User getUserByAccessCode(String accessCode) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from UserAccessCode as uac where uac.code=:param");
	query.setParameter("param", accessCode);
	List<UserAccessCode> accessCodes = query.list();
	if (accessCodes.isEmpty()) {
		return null;
	} else 
	if (accessCodes.size() > 1) {
		throw new RuntimeException();
	} else {
		return accessCodes.get(0).getUser();
	}
}
@SuppressWarnings("unchecked")
public UserAccessCode getUserAccessCode(User user) {
	Session session = sessionFactory.getCurrentSession();
	List<UserAccessCode> accessCodes = session.createQuery("from UserAccessCode as c where c.user.userId=" + user.getUserId()).list();
	if (accessCodes.size() != 1) {
		throw new RuntimeException();
	}
	return accessCodes.get(0);
}

public UserAccessCode deleteUserAccessCode(User user) {
	Session session = sessionFactory.getCurrentSession();
	UserAccessCode accessCode = getUserAccessCode(user);
	session.delete(accessCode);
	return accessCode;
}

@SuppressWarnings("unchecked")
public void clearAccessCodes() {
	Session session = sessionFactory.getCurrentSession();
	List<UserAccessCode> accessCodes = session.createQuery("from UserAccessCode").list();
	for (UserAccessCode code : accessCodes) {
		session.delete(code);
	}
}
public Station createStation(String name) {
	Session session = sessionFactory.getCurrentSession();
	Station station = new Station();
	station.setStationName(name);
	session.save(station);
	return station;
}

public  void initStations() {
	clearStations();
	String[] cities = {"Moscow", "Saint-Petersburg", "Ryazan", "Vladivostok", "Petrozavodsk", "Volgograd", "Novgorod", "Samara"};
	for (String city:cities) {
		createStation(city);
	}
}

public  Station getStation( int stationId) {
	Session session = sessionFactory.getCurrentSession();
	return (Station)session.get(Station.class, stationId);
}

@SuppressWarnings("unchecked")
public  Station getStationByName(String name) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Station as s where s.stationName=:name");
	query.setParameter("name", name);
	List<Station> stations = query.list();
	if (stations.size()==0) {
		return null;
	} else
	if (stations.size()>1) {
		throw new RuntimeException("we have duplicate stations");
	} else {
	return stations.get(0);
	}
	
}

@SuppressWarnings("unchecked")
public  Set<Station> getAllStationsOnRoute( int routeId) {

	Session session = sessionFactory.getCurrentSession();
	Set<Station> stations = new HashSet<Station>();
	Query query = session.createQuery("from Shedule as s where s.route.routeId=?");
	query.setParameter(0, routeId);
	for (Shedule s : (List<Shedule>)query.list()) {	
		Direction d = s.getDirection();
		stations.add(d.getStArr());
		stations.add(d.getStDep());
	}
	
	return stations;
}

public  void deleteStation( int stationId) {
	Session session = sessionFactory.getCurrentSession();
	Station s = (Station) session.load(Station.class, stationId);
	session.delete(s);
}

@SuppressWarnings("unchecked")
public  void clearStations() {
	Session session = sessionFactory.getCurrentSession();
	List<Station> stations = session.createQuery("from Station").list();
	for (Station s: stations) {
		session.delete(s);
	}
}

public  Direction createDirection( Station stDep,  Station stArr,  long time,  double cost) {
	Session session = sessionFactory.getCurrentSession();
	
	Direction direction = new Direction();
	direction.setStArr(stArr);
	direction.setStDep(stDep);
	direction.setTime(time);
	direction.setCost(cost);
	session.save(direction);
	
	Direction directionReverse = new Direction();
	directionReverse.setStArr(stDep);
	directionReverse.setStDep(stArr);
	directionReverse.setTime(time);
	directionReverse.setCost(cost);
	session.save(directionReverse);
	
	return direction;
}

@SuppressWarnings("unchecked")
public  Direction getDirectionByStartFinish( int start,  int finish){

	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Direction as d where d.stDep.stationId=? and d.stArr.stationId=?");
	query.setParameter(0, start);
	query.setParameter(1, finish);
	List<Direction> directions = query.list();
	if (directions.isEmpty()) {
		return null;
	} else
	if (directions.size() > 1) {
		throw new RuntimeException();
	} else {
		return directions.get(0);
	}
}
@SuppressWarnings("unchecked")
public List<Route> getAllRoutes() {
	Session session = sessionFactory.getCurrentSession();
	return session.createQuery("from Route").list();
}
@SuppressWarnings("unchecked")
public  void initDirections() {
	clearDirections();
	Session session = sessionFactory.getCurrentSession();
	List<Station> stations = session.createQuery("from Station").list();
	if (stations.size() > 1) {
		for (int i = 0; i < stations.size(); i++) {
			Station stDep = stations.get(i);
			for (int j = i + 1; j < stations.size(); j++) {
				Station stArr = stations.get(j);
				int time = 1000 * 60 * 30 + random.nextInt(1000 * 60 * 60 * 2);
				double cost = 400d + random.nextInt(200);
				createDirection(stDep, stArr, time, cost);
			}
		}
	}
	List<Direction> result = session.createQuery("from Direction").list();
	System.out.println(result);
}

public  Direction getDirection(int dirId) {
	Session session = sessionFactory.getCurrentSession();
	return (Direction) session.get(Direction.class, dirId);
}


public  void deleteDirection(int directionId){
		Session session = sessionFactory.getCurrentSession();
		Direction d = (Direction) session.get(Direction.class, directionId);
		session.delete(d);
}
@SuppressWarnings("unchecked")
public  void clearDirections() {
	Session session = sessionFactory.getCurrentSession();
	List<Direction> directions = session.createQuery("from Direction").list();
	for (Direction d: directions) {
		session.delete(d);
	}
}

public  Route createRoute( String name) {
	Session session = sessionFactory.getCurrentSession();
	Route route = new Route();
	route.setRouteName(name);
	session.save(route);
	return route;
}

public  Route getRoute( int routeId) {
	Session session = sessionFactory.getCurrentSession();
	return (Route) session.get(Route.class, routeId);
}

@SuppressWarnings("unchecked")
public Route getRouteByName(String routeName) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Route as r where r.routeName=:name");
	query.setParameter("name", routeName);
	List<Route> routes = query.list();
	if (routes.isEmpty()) {
		return null;
	} else
	if (routes.size()>1) {
		throw new RuntimeException();
	} else {
		return routes.get(0);
	}
}

public List<Route> getRoutesContainStations(int stDep, int stArr) {
	List<Shedule> shedules1 = getShedulesContainStation(stDep);
	List<Shedule> shedules2 = getShedulesContainStation(stArr);
	
	Set<Route> routes = new HashSet<Route>();
	for (Shedule s : shedules1) {
		routes.add(s.getRoute());
	}
	for (Shedule s : shedules2) {
		routes.add(s.getRoute());
	}
	return new ArrayList<Route>(routes);
}
public List<Route> getRoutesContainStation(int station) {
	List<Shedule> shedulesContainStation = getShedulesContainStation(station);
	Set<Route> routes = new HashSet<Route>();
	for (Shedule s : shedulesContainStation) {
		routes.add(s.getRoute());
	}
	return new ArrayList<Route>(routes);
}

@SuppressWarnings("unchecked")
public List<Journey> getJourneysByRoutes(List<Route> routes) {
	List<Journey> journeys = new ArrayList<Journey>();
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	for (Route r : routes) {
		query = session.createQuery("from Journey as j where j.route.routeId=" + r.getRouteId());
		journeys.addAll(query.list());
	}
	return journeys;
}

@SuppressWarnings("unchecked")
public List<Journey> getAllJourneys() {
	Session session = sessionFactory.getCurrentSession();
	return session.createQuery("from Journey").list();
}

@SuppressWarnings("unchecked")
public List<Journey> getJourneysBetween(Date start, Date stop) {
	Session session = sessionFactory.getCurrentSession();
	Criteria crit = session.createCriteria(Journey.class);
	if (start != null && stop != null){
		crit.add(Restrictions.between("timeDep", start, stop));
	} else
	if (stop == null && start != null) {
		crit.add(Restrictions.ge("timeDep", start));
	} else
	if (stop != null && start == null) {
		crit.add(Restrictions.le("timeDep", stop));
	} 
	return crit.list();
}

@SuppressWarnings("unchecked")
public List<Journey> getJourneysInTimeInterval(Date start, Date stop) {
	Session session = sessionFactory.getCurrentSession();
	Criteria crit = session.createCriteria(Journey.class);
	crit.add(Restrictions.le("timeDep", stop));
	crit.add(Restrictions.ge("timeDep", start));
	return crit.list();
}
@SuppressWarnings("unchecked")
public List<Train> getAllTrains(){
	Session session = sessionFactory.getCurrentSession();
	return session.createQuery("from Train").list();
}
@SuppressWarnings("unchecked")
public List<Ticket> getTicketsOfPassenger(Passenger passenger) {
	Session session = sessionFactory.getCurrentSession();
	return session.createQuery("from Ticket as t where t.passenger.passengerId=" + passenger.getPassengerId()).list();
}
@SuppressWarnings("unchecked")
public List<Ticket> getTicketsOfJourney(Journey journey) {
	Session session = sessionFactory.getCurrentSession();
	return session.createQuery("from Ticket as t where t.journey.journeyId=" + journey.getJourneyId()).list();
}
@SuppressWarnings("unchecked")
public List<Station> getAllStations() {
	Session session = sessionFactory.getCurrentSession();
	return session.createQuery("from Station").list();
}
@SuppressWarnings("unchecked")
public List<Shedule> getShedulesContainStation(int station) {
	
	Session session = sessionFactory.getCurrentSession();
	Query query = session.
			createQuery("from Direction as d where d.stArr.stationId=? or d.stDep.stationId=?");
	query.setParameter(0, station);
	query.setParameter(1, station);
	List<Direction> directions = query.list();
	List<Shedule> shedulesContainStation = new ArrayList<Shedule>();
	for (Direction d : directions) {
		List<Shedule> shedules = session.
				createQuery("from Shedule as s where s.direction.directionId=" + d.getDirectionId()).list();
		shedulesContainStation.addAll(shedules);
	}
	Set<Shedule> uniqueShedules = new HashSet<Shedule>();
	for (Shedule s : shedulesContainStation) {
		uniqueShedules.add(s);
	}
	return new ArrayList<Shedule>(uniqueShedules);
}
public List<Shedule> getShedulesOfTicket(Ticket ticket) {
	
	List<Shedule> steps = getShedulesOfRoute(ticket.getJourney().getRoute().getRouteId());
	int start = 0;
	int finish = 0;
	for (Shedule s : steps) {
		if (s.getDirection().getStDep().equals(ticket.getStDep())) {
			start = s.getStep();
		}
		if (s.getDirection().getStArr().equals(ticket.getStArr())) {
			finish = s.getStep();
		}
	}
	
	List<Shedule> result = new ArrayList<Shedule>();
	for (Shedule s : steps) {
		if (s.getStep() >= start && s.getStep() <= finish) {
			result.add(s);
		}
	}
	return result;
	
}
public  void initRoutes(){
	clearRoutes();
	String alphabet = "abcdefghijklmnopqrstuvwxyz";
	for (int i = 0; i < 10; i++) {
		createRoute(String.valueOf(100 + i) + alphabet.charAt(random.nextInt(alphabet.length())));
	}
}
@SuppressWarnings("unchecked")
public  Route deleteRoute(int routeId){
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Route as r where r.routeId=?");
	query.setParameter(0, routeId);
	List<Route> routes = query.list();
	if (routes.isEmpty()) {
		return null;
	} else
	if (routes.size() > 1) {
		throw new RuntimeException();
	} else {
		session.delete(routes.get(0));
	}
	return routes.get(0);
}
@SuppressWarnings("unchecked")
public void clearRoutes(){
	Session session = sessionFactory.getCurrentSession();
	List<Route> routes = session.createQuery("from Route").list();
	for (Route r : routes) {
		deleteRoute(r.getRouteId());
	}
}
public   Shedule createShedule(Direction direction, Route route,  int step) {
	Session session = sessionFactory.getCurrentSession();
	Shedule shedule = new Shedule();
	shedule.setDirection(direction);
	shedule.setRoute(route);
	shedule.setStep(step);
	session.save(shedule);
	return shedule;
}
@SuppressWarnings("unchecked")
public Shedule getShedule(int routeId, int dirId, int step) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Shedule as s where s.route.routeId=?"
			+ " and s.direction.directionId=? and s.step=?");
	query.setParameter(0, routeId);
	query.setParameter(1, dirId);
	query.setParameter(2, step);
	List<Shedule> shedules = query.list();
	if (shedules.isEmpty()) {
		return null;
	} else
	if (shedules.size()>1) {
		throw new RuntimeException();
	} else {
		return shedules.get(0);
	}
	
}
@SuppressWarnings("unchecked")
public  void initShedules() {
	Session session = sessionFactory.getCurrentSession();
	clearShedules();
	List<Route> routes = session.createQuery("from Route").list();
	List<Station> stations = session.createQuery("from Station").list();
	for (Route r: routes) {
		Set<Integer> routeStations = new HashSet<Integer>();
		int steps = 4 + random.nextInt(3);
		int currentStep = 0;
		int stDepId = stations.get(random.nextInt(stations.size())).getStationId();
		routeStations.add(stDepId);
		
		
		while (currentStep<steps) {
			Query query = session.createQuery("from Direction as d where d.stDep.stationId=?");
			query.setParameter(0, stDepId);
			List<Direction> possibleDirections = query.list();
			Iterator<Direction> iterator = possibleDirections.iterator();
			while (iterator.hasNext()) {
				Direction d = iterator.next();
				if (routeStations.contains(d.getStArr().getStationId())) {
					iterator.remove();
				}
			}
			if (!possibleDirections.isEmpty()){
				
			Direction currentDirection = possibleDirections.get(random
					.nextInt(possibleDirections.size()));
			while (routeStations.contains(currentDirection.getStArr().getStationId())) {
				currentDirection = possibleDirections.get(random.nextInt(possibleDirections.size()));
			}
			createShedule(currentDirection, r, currentStep);
			currentStep++;
			stDepId = currentDirection.getStArr().getStationId();
			routeStations.add(stDepId);
			} else {
				break;
			}
		}
	}
}

@SuppressWarnings("unchecked")
public List<Shedule> getShedulesOfRoute(int routeId) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Shedule as s where s.route.routeId=?");
	query.setParameter(0, routeId);
	return query.list();
}


@SuppressWarnings("unchecked")
public void clearShedules() {
	Session session = sessionFactory.getCurrentSession();
	List<Shedule> shedules = session.createQuery("from Shedule").list();
	for (Shedule s: shedules) {
		session.delete(s);
	}
}

public   Journey createJourney( Train train,  Route route,  Date timeDep) {
	Session session = sessionFactory.getCurrentSession();
	Journey journey = new Journey();
	journey.setTrain(train);
	journey.setRoute(route);
	journey.setTimeDep(timeDep);
	session.save(journey);
	return journey;
}
@SuppressWarnings("unchecked")
public void initJourneys() {
	clearJourneys();
	Session session = sessionFactory.getCurrentSession();
	Date currentTime = new Date();
	long currentMillis = currentTime.getTime();
	long m = 1000 * 60l;
	long h = m * 60;
	List<Route> routes = session.createQuery("from Route").list();
	List<Train> trains = session.createQuery("from Train").list();
	for (int i = 0; i < routes.size(); i++) {
		Route r = routes.get(i);
		Train t = trains.get(i % trains.size());
		createJourney(t, r, new Date(currentMillis + (i + 1) * h));
	}
}

public Journey getJourney( int jourId) {
	Session session = sessionFactory.getCurrentSession();
	return (Journey) session.get(Journey.class, jourId);
}
@SuppressWarnings("unchecked")
public   List<Journey> getAllJourneysOfTrain( int trainId) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Journey as j where j.train.trainId=?");
	query.setParameter(0, trainId);
	return query.list();
}

@SuppressWarnings("unchecked")
public void clearJourneys() {
	Session session = sessionFactory.getCurrentSession();
	List<Journey> journeys = session.createQuery("from Journey").list();
	for (Journey j: journeys) {
		session.delete(j);
	}
}

public Passenger createPassenger( String name,  String surname,  Date birthday) {
	Session session = sessionFactory.getCurrentSession();
	Passenger p = new Passenger();
	p.setPassengerName(name);
	p.setPassengerSurname(surname);
	p.setPassengerBirthday(birthday);
	session.save(p);
	return p;
}

public void deletePassenger(int passengerId){
	Session session = sessionFactory.getCurrentSession();
	Passenger p = (Passenger) session.get(Passenger.class, passengerId);
	session.delete(p);
}
public Passenger getPassenger(int passId) {
	Session session = sessionFactory.getCurrentSession();
	return (Passenger) session.get(Passenger.class, passId);
}
@SuppressWarnings("unchecked")
public Passenger getPassenger(String name, String surname, Date birthday) {
	Session session = sessionFactory.getCurrentSession();
	Criteria crit = session.createCriteria(Passenger.class);
	crit.add(Restrictions.eq("passengerName", name));
	crit.add(Restrictions.eq("passengerSurname", surname));
	crit.add(Restrictions.eq("passengerBirthday", birthday));
	
	List<Passenger> passengers = crit.list();
	if (passengers.isEmpty()) {
		return null;
	} else 
	if (passengers.size()>1) {
		throw new RuntimeException();
	} else {
		return passengers.get(0);
	}
}
@SuppressWarnings("unchecked")
public void clearPassengers() {
	Session session = sessionFactory.getCurrentSession();
	List<Passenger> passengers = session.createQuery("from Passenger").list();
	for (Passenger p : passengers) {
		session.delete(p);
	}
}

public UserRole createUserRole(String role) {
	Session session = sessionFactory.getCurrentSession();
	UserRole userRole = new UserRole(); userRole.setRole(role);
	session.save(userRole);
	return userRole;
}

public void setUserAs(User user, String role) {
	Session session = sessionFactory.getCurrentSession();
	Set<UserRole> userRoles = new HashSet<UserRole>();
	UserRole userRole = getRoleByString(role);
	if (role.equals("ROLE_ADMIN")) {
		UserRole userRole1 = getRoleByString("ROLE_USER");
		userRoles.add(userRole1);
	}
	
	userRoles.add(userRole);
	user.setUserRole(userRoles);
	session.update(user);
}
public void debitFundsFromTheAccount(User user, double pay) {
	Session session = sessionFactory.getCurrentSession();
	double cash = user.getCash();
	double newCash = cash - pay;
	user.setCash(newCash);
	session.update(user);
}

public void addFundsToTheAccount(User user, double money) {
	Session session = sessionFactory.getCurrentSession();
	double cash = user.getCash();
	double newCash = cash + money;
	user.setCash(newCash);
	session.update(user);
}
public UserRole deleteUserRole(int userRoleId) {
	Session session = sessionFactory.getCurrentSession();
	UserRole userRole = (UserRole) session.get(UserRole.class, userRoleId);
	session.delete(userRole);
	return userRole;
}
@SuppressWarnings("unchecked")
public void clearUserRole() {
	Session session = sessionFactory.getCurrentSession();
	List<UserRole> roles = session.createQuery("from UserRole").list();;
	for (UserRole r : roles) {
		session.delete(r);
	}
}
public void initUserRole() {
	clearUserRole();
	createUserRole("ROLE_ADMIN");
	createUserRole("ROLE_USER");
}
@SuppressWarnings("unchecked")
public UserRole getRoleByString(String stringRole) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from UserRole as r where r.role=:param");
	query.setParameter("param", stringRole);
	List<UserRole> roles = query.list();
	
	if (roles.size() != 1) {
		throw new RuntimeException();
	}
	return roles.get(0);
}
public User createUser( String login,  String password,  boolean accountType, Set<UserRole> roles) {
	Session session = sessionFactory.getCurrentSession();
	User u = new User();
	u.setUserLogin(login);
	u.setUserPassword(password);
	u.setAccountType(accountType);
	u.setUserRole(roles);
	session.save(u);
	return u;
}
public void initUsers() {
	clearAccessCodes();
	clearUsers();
	initUserRole();
	UserRole adminRole = getRoleByString("ROLE_ADMIN");
	UserRole userRole = getRoleByString("ROLE_USER");
	Set<UserRole> rootRole = new HashSet<UserRole>(); rootRole.add(userRole); rootRole.add(adminRole);
	createUser("root@root.ru", "root", true, rootRole);
}

public User getUser( int userId) {
	Session session = sessionFactory.getCurrentSession();
	return (User) session.get(User.class, userId);
}


@SuppressWarnings("unchecked")
public User getUserByName(String name){
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from User as u where u.userLogin=:name");
	query.setParameter("name", name);
	List<User> foundUsers = query.list();
	if (foundUsers.isEmpty()) {
		return null;
	} else
	if (foundUsers.size() > 1) {
		throw new RuntimeException();
	} else {
		return foundUsers.get(0);
	}
}
@SuppressWarnings("unchecked")
public void clearUsers() {
	Session session = sessionFactory.getCurrentSession();
	List<User> users = session.createQuery("from User").list();;
	for (User u : users) {
		session.delete(u);
	}
}
public Ticket createTicket( Passenger passenger,  Journey journey,  Station stDep,  Station stArr, Date purchaseDate) {
	Session session = sessionFactory.getCurrentSession();
	Ticket t = new Ticket();
	t.setPassenger(passenger);
	t.setJourney(journey);
	t.setStDep(stDep);
	t.setStArr(stArr);
	t.setPurchaseDate(purchaseDate);
	session.save(t);
	return t;
}
public Ticket getTicket( int ticketId) {
	Session session = sessionFactory.getCurrentSession();
	return (Ticket) session.get(Ticket.class, ticketId);
}

@SuppressWarnings("unchecked")
public List<Ticket> getTicketOfUser( int userId) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from UserAndTicket as ut where ut.user.userId=?");
	query.setParameter(0, userId);
	List<UserAndTicket> userTickets = query.list();
	List<Ticket> tickets = new ArrayList<Ticket>();
	for (UserAndTicket ut: userTickets) {
		tickets.add(ut.getTicket());
	}
	return tickets;
}
@SuppressWarnings("unchecked")
public List<UserAndTicket> getTicketsBetweenDates(Date startDate, Date stopDate) {
	Criteria crit = sessionFactory.getCurrentSession().createCriteria(Ticket.class);
	crit.add(Restrictions.between("purchaseDate", startDate, stopDate));
	List<Ticket> tickets = crit.list();
	return getUserAndTickets(tickets);
}
public List<UserAndTicket> getUserAndTickets(List<Ticket> tickets) {
	List<UserAndTicket> result = new ArrayList<UserAndTicket>();
	for (Ticket ticket : tickets) {
		UserAndTicket ut = getUserAndTicket(ticket);
		result.add(ut);
	}
	return result;
}
@SuppressWarnings("unchecked")
public List<UserAndTicket> getTicketsBefore(Date stopDate) {
	Criteria crit = sessionFactory.getCurrentSession().createCriteria(Ticket.class);
	crit.add(Restrictions.le("purchaseDate", stopDate));
	return getUserAndTickets(crit.list());
}

@SuppressWarnings("unchecked")
public List<UserAndTicket> getTicketsAfter(Date startDate) {
	Criteria crit = sessionFactory.getCurrentSession().createCriteria(Ticket.class);
	crit.add(Restrictions.ge("purchaseDate", startDate));
	return getUserAndTickets(crit.list());
}
@SuppressWarnings("unchecked")
public List<UserAndTicket> getAllTickets() {
	return sessionFactory.getCurrentSession().createQuery("from UserAndTicket").list();
}


@SuppressWarnings("unchecked")
public void initTickets() throws ParseException {
	Session session = sessionFactory.getCurrentSession();
	clearPassengers();
	String[] names = {"Aaron", "Charles", "Beatrice", "Douglas", "Emma", "Fred", "Graham", "Luccile"};
	String[] surnames = {"Abramson", "Hoggarth", "Larkins", "Bootman", "Miller", "Cramer", "Parkinson", "Erickson"};
	String[] birthdays = {"12/03/1961", "07/06/2003", "30/05/1968", "22/12/1986", "31/01/1996", "15/03/2000", "02/10/1955", "19/11/2012"};
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	List<Journey> journeys = session.createQuery("from Journey").list();
	Query query = session.createQuery("from User as u where u.accountType=?");
	query.setParameter(0, true);
	User u = (User) query.list().get(0);
	for (int i = 0; i < names.length; i++) {
		Passenger p = createPassenger(names[i], surnames[i], sdf.parse(birthdays[i]));
		Journey j = journeys.get(i % journeys.size());
		int routeId = j.getRoute().getRouteId();
		query = session.createQuery("from Shedule as s where s.route.routeId=? and s.step=?");
		query.setParameter(0, routeId);
		query.setParameter(1, 0);
		List<Shedule> result = query.list();
		Shedule routeBeginning = result.get(0);
		query = session.createQuery("from Shedule as s where s.route.routeId=" + routeId);
		List<Shedule> steps = query.list();
		int lastStep = steps.size()-1;
		
		query = session.createQuery("from Shedule as s where s.route.routeId=? and s.step=?");
		query.setParameter(0, routeId);
		query.setParameter(1, lastStep);
		Shedule routeEnding = (Shedule) query.list().get(0);
		Direction routeBeginningD = (routeBeginning.getDirection());
		Direction routeEndingD = (routeEnding.getDirection());
		boolean trainHasEmptySeats = decrementEmptySeats(
				j.getJourneyId(), routeBeginningD.getStDep().getStationId(), routeEndingD.getStArr().getStationId());
		if (trainHasEmptySeats) {
		Ticket t = createTicket(p, j,
				routeBeginningD.getStDep(), routeEndingD.getStArr(), new Date());
		createUserAndTicket(t, u);
		}
	}
}
public Ticket deleteTicket( int ticketId) {
	Session session = sessionFactory.getCurrentSession();
	Ticket t = getTicket(ticketId);
	session.delete(t);
	return t;
}

@SuppressWarnings("unchecked")
public void clearTickets() {
	Session session = sessionFactory.getCurrentSession();
	List<Ticket> tickets = session.createQuery("from Ticket").list();;
	for (Ticket t:tickets) {
		session.delete(t);
	}
}

public UserAndTicket createUserAndTicket( Ticket ticket, User user) {
	Session session = sessionFactory.getCurrentSession();
	UserAndTicket ut = new UserAndTicket();
	ut.setTicket(ticket);
	ut.setUser(user);
	session.save(ut);
	return ut;
}

public UserAndTicket getUserAndTicket(Ticket ticket) {
	Session session = sessionFactory.getCurrentSession();
	return (UserAndTicket) session.get(UserAndTicket.class, ticket.getTicketId());
}
public   UserAndTicket deleteUserAndTicket( int ticketId) {
	Session session = sessionFactory.getCurrentSession();
	UserAndTicket ut = (UserAndTicket) session.get(UserAndTicket.class, ticketId);
	session.delete(ut);
	return ut;
}

public Seats createSeats( Journey journey,  int step,  int emptySeats) {
	Session session = sessionFactory.getCurrentSession();
	Seats s = new Seats();
	s.setJourney(journey);
	s.setRouteStep(step);
	s.setEmptySeats(emptySeats);
	session.save(s);
	return s;
}


@SuppressWarnings("unchecked")
public List<Seats> getSeatsOnJourney(int journeyId) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Seats as s where s.journey.journeyId=?");
	query.setParameter(0, journeyId);
	return query.list();
}
public Seats deleteSeats(int seatsId) {
	Session session = sessionFactory.getCurrentSession();
	Seats s = (Seats) session.get(Seats.class, seatsId);
	session.delete(s);
	return s;
}

@SuppressWarnings("unchecked")
public void initSeats() {
	Session session = sessionFactory.getCurrentSession();
	List<Journey> journeys = session.createQuery("from Journey").list();
	for (Journey j : journeys) {
		int trainId = j.getTrain().getTrainId();
		int routeId = j.getRoute().getRouteId();
		Train train = getTrain(trainId);
		List<Shedule> steps = getShedulesOfRoute(routeId);
		for (Shedule s : steps) {
			createSeats(j, s.getStep(), train.getTrainSeats());
		}
	}
}

public boolean decrementEmptySeats( int journeyId,  int stDep,  int stArr) {
	Journey j = getJourney(journeyId);
	List<Seats> seats = getSeatsOnJourney(journeyId);
	List<Shedule> steps = getShedulesOfRoute(j.getRoute().getRouteId());
	
	int startStep=0;
	int stopStep=0;
	for (Shedule s : steps) {
		Direction d = (s.getDirection());
		if (d.getStDep().getStationId() == stDep) {
			startStep=s.getStep();
		}
		if (d.getStArr().getStationId() == stArr) {
			stopStep=s.getStep();
		}
	}
	for (Seats s : seats) {
		if (s.getRouteStep()>=startStep && s.getRouteStep()<=stopStep && s.getEmptySeats()==0) {
			return false;
		}
	}
	
	for (Seats s : seats) {
		Session session = sessionFactory.getCurrentSession();
		int emptySeats = s.getEmptySeats();
		if (s.getRouteStep()>=startStep && s.getRouteStep()<=stopStep) {
		s.setEmptySeats(emptySeats - 1);
		session.merge("Seats", s);
		}
	}
	return true;
	
}

@SuppressWarnings("unchecked")
public void clearSeats() {
	Session session = sessionFactory.getCurrentSession();
	List<Seats> seats = session.createQuery("from Seats").list();
	for (Seats s : seats) {
		session.delete(s);
	}
	
}

public void initDatabase() throws ParseException{
	initStations();
	initTrains();
	initRoutes();
	initDirections();
	initShedules();
	initJourneys();
	initSeats();
	initUsers();
	initTickets();
}
public SessionFactory getSessionFactory() {
	return sessionFactory;
}
public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
}

}
