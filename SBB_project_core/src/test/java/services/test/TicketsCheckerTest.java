package services.test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;





import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.ListOfTickets;
import com.dto.UserLoginContainer;
import com.entities.Direction;
import com.entities.Journey;
import com.entities.Passenger;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Station;
import com.entities.Ticket;
import com.entities.User;
import com.service.TicketsChecker;

public class TicketsCheckerTest {
TicketsChecker checker;
@Mock
Dao dao = Mockito.mock(Dao.class);
@Before
public void init(){
	checker = new TicketsChecker();
	checker.setDao(dao);
}
	@Test
	public void test() throws Exception {
		User user= new User();user.setUserId(1);user.setUserLogin("login");user.setUserPassword("password");
		UserLoginContainer input = new UserLoginContainer(user.getUserLogin());
		Station stDep = new Station();stDep.setStationId(3);stDep.setStationName("st_dep");
		Station stArr = new Station();stArr.setStationId(4);stArr.setStationName("st_arr");
		Route r = new Route();r.setRouteId(6);
		Direction d = new Direction();d.setDirectionId(8);d.setStDep(stDep);d.setStArr(stArr);
		d.setTime(1000*60*60);d.setCost(1000);
		Shedule s = new Shedule();s.setSheduleId(7);s.setRoute(r);s.setDirection(d);
		s.setStep(0);
		List<Shedule> steps = new ArrayList<Shedule>();steps.add(s);
		Date birthday = new Date();
		Journey j = new Journey();j.setJourneyId(5);j.setRoute(r);j.setTimeDep(birthday);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US);
		Passenger p = new Passenger();p.setPassengerId(6);p.setPassengerName("name");p.setPassengerSurname("surname");
		p.setPassengerBirthday(birthday);
		Ticket t = new Ticket();t.setTicketId(2);t.setJourney(j);t.setStDep(stDep);
		t.setStArr(stArr);t.setPassenger(p);
		List<Ticket> ticketsOfUser = new ArrayList<Ticket>();ticketsOfUser.add(t);
		
		Mockito.when(dao.getUserByName(user.getUserLogin())).thenReturn(user);
		Mockito.when(dao.getTicketOfUser(user.getUserId())).thenReturn(ticketsOfUser);
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps);
		
		
		ListOfTickets output = checker.check(input);
		String expectedResult = t.getTicketId()+","+p.getPassengerName()+" "+p.getPassengerSurname()+","+
		stDep.getStationName()+","+sdf.format(j.getTimeDep())+","+stArr.getStationName()+","+
		sdf.format(new Date(birthday.getTime()+d.getTime()))+","+d.getCost();
		Assert.assertTrue(output.getTicketsInfo().get(0).equals(expectedResult));
		
	}
	@Test(expected=NullPointerException.class)
	public void test2() throws Exception{
		checker.check(null);
	}
	@Test(expected=NullPointerException.class)
	public void test3() throws Exception{
		User user= new User();user.setUserId(1);user.setUserLogin("login");user.setUserPassword("password");
		UserLoginContainer input = new UserLoginContainer(user.getUserLogin());
		Station stDep = new Station();stDep.setStationId(3);stDep.setStationName("st_dep");
		Station stArr = new Station();stArr.setStationId(4);stArr.setStationName("st_arr");
		Route r = new Route();r.setRouteId(6);
		Direction d = new Direction();d.setDirectionId(8);d.setStDep(stDep);d.setStArr(stArr);
		d.setTime(1000*60*60);d.setCost(1000);
		Shedule s = new Shedule();s.setSheduleId(7);s.setRoute(r);s.setDirection(d);
		s.setStep(0);
		List<Shedule> steps = new ArrayList<Shedule>();steps.add(s);
		Date birthday = new Date();
		Journey j = new Journey();j.setJourneyId(5);j.setRoute(r);j.setTimeDep(birthday);
		Passenger p = new Passenger();p.setPassengerId(6);p.setPassengerName("name");p.setPassengerSurname("surname");
		p.setPassengerBirthday(birthday);
		Ticket t = new Ticket();t.setTicketId(2);t.setJourney(j);t.setStDep(stDep);
		t.setStArr(stArr);t.setPassenger(p);
		List<Ticket> ticketsOfUser = new ArrayList<Ticket>();ticketsOfUser.add(t);
		
		Mockito.when(dao.getUserByName(user.getUserLogin())).thenReturn(user);
		Mockito.when(dao.getTicketOfUser(user.getUserId())).thenReturn(new ArrayList<Ticket>());
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps);
		
		ListOfTickets output = checker.check(input);
		
		Assert.assertTrue(output.getTicketsInfo().size()==0);
	}

}
