package services.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;







import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.PassengerInfo;
import com.dto.TicketInfo;
import com.entities.Journey;
import com.entities.Passenger;
import com.entities.Station;
import com.entities.Ticket;
import com.entities.User;
import com.service.PassengerRegistrator;

public class PassengerRegistratorTest {
PassengerRegistrator registrator;
@Mock
Dao dao = Mockito.mock(Dao.class);
@Before
public void init(){
	registrator = new PassengerRegistrator();
	registrator.setDao(dao);
	
}

	@Test
	public void testRegister() throws Exception {
		Station s1 = new Station(); s1.setStationId(1);s1.setStationName("s1");
		Station s2 = new Station(); s2.setStationId(2);s2.setStationName("s2");
		Journey j = new Journey(); j.setJourneyId(3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Date passengerDep = new Date();
		Date passengerDest = new Date(new Date().getTime()+1000*60*60*24);
		String cost = "1000";
		String journeyData = j.getJourneyId()+";"+sdf2.format(passengerDep)+";"+sdf2.format(passengerDest)+";"+cost;
		List<String> allJourneysData = new ArrayList<String>();allJourneysData.add(journeyData);
		PassengerInfo info = new PassengerInfo
				("user",j.getJourneyId()+";0;0;"+s1.getStationId()+";"+s2.getStationId(),"name","surname","2000-11-11",allJourneysData);
		
		
		
		Passenger newPassenger = new Passenger();newPassenger.setPassengerName(info.getName());newPassenger.setPassengerSurname(info.getSurname());
		newPassenger.setPassengerBirthday(sdf.parse(info.getBirthday()));
		
		Date purchaseDate = new Date();
		Ticket ticket = new Ticket(); ticket.setJourney(j);ticket.setPassenger(newPassenger);
		ticket.setStArr(s2); ticket.setStDep(s1);ticket.setTicketId(3); ticket.setPurchaseDate(purchaseDate);
		
		User user = new User(); user.setUserLogin("user"); user.setUserId(4); user.setCash(1000d);
		
		
		Mockito.when(dao.createPassenger(info.getName(), info.getSurname(), sdf.parse(info.getBirthday()))).
		thenReturn(newPassenger);
		Mockito.when(dao.getJourney(j.getJourneyId())).thenReturn(j);
		Mockito.when(dao.getStation(s1.getStationId())).thenReturn(s1);
		Mockito.when(dao.getStation(s2.getStationId())).thenReturn(s2);
		Mockito.when(dao.getDate()).thenReturn(purchaseDate);
		Mockito.when(dao.createTicket(newPassenger, j, s1, s2, purchaseDate)).thenReturn(ticket);
		Mockito.when(dao.getUserByName(info.getCurrentUser())).thenReturn(user);
		
		StringBuilder ticketInfo = new StringBuilder(
				String.valueOf(ticket.getTicketId()));
		ticketInfo.append(";");
		ticketInfo.append(newPassenger.getPassengerName());
		ticketInfo.append(";");
		ticketInfo.append(newPassenger.getPassengerSurname());
		ticketInfo.append(";");
		ticketInfo.append(s1.getStationName());
		ticketInfo.append(";");
		ticketInfo.append(sdf2.format(passengerDep));
		ticketInfo.append(";");
		ticketInfo.append(s2.getStationName());
		ticketInfo.append(";");
		ticketInfo.append(sdf2.format(passengerDest));
		ticketInfo.append(";");
		ticketInfo.append(cost);
		String expectedResult = ticketInfo.toString();
		TicketInfo TI = registrator.register(info);
		Assert.assertTrue(TI.getTicketInfo().equals(expectedResult));
		
	}
	@Test(expected=NullPointerException.class)
	public void test2() throws Exception{
		registrator.register(null);
	}
	@Test(expected=NullPointerException.class)
	public void test3() throws Exception{
		registrator.register(new PassengerInfo(null, null, null, null, null, null));
	}
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void test4() throws Exception{
		Station s1 = new Station(); s1.setStationId(1);s1.setStationName("s1");Station s2 = new Station(); s2.setStationId(2);s2.setStationName("s2");
		Journey j = new Journey(); j.setJourneyId(3);
		
		Date passengerDep = new Date();
		Date passengerDest = new Date(new Date().getTime()+1000*60*60*24);
		String cost = "1000";
		List<String> allJourneysData = new ArrayList<String>();
		
		PassengerInfo info = new PassengerInfo
				("user",j.getJourneyId()+";0;0;"+s1.getStationId()+";"+s2.getStationId(),"name","surname","2000-11-11",allJourneysData);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		
		Passenger newPassenger = new Passenger();newPassenger.setPassengerName(info.getName());newPassenger.setPassengerSurname(info.getSurname());
		newPassenger.setPassengerBirthday(sdf.parse(info.getBirthday()));
				
		Date purchaseDate = new Date();
		Ticket ticket = new Ticket(); ticket.setJourney(j); ticket.setPassenger(newPassenger);
		ticket.setStArr(s2); ticket.setStDep(s1); ticket.setTicketId(3); ticket.setPurchaseDate(purchaseDate);
		
		User user = new User(); user.setUserLogin("user"); user.setUserId(4);
		
		
		Mockito.when(dao.createPassenger(info.getName(), info.getSurname(), sdf.parse(info.getBirthday()))).
		thenReturn(newPassenger);
		Mockito.when(dao.getStation(s1.getStationId())).thenReturn(s1);
		Mockito.when(dao.getStation(s2.getStationId())).thenReturn(s2);
		Mockito.when(dao.createTicket(newPassenger, j, s1, s2, purchaseDate)).thenReturn(ticket);
		Mockito.when(dao.getUserByName(info.getCurrentUser())).thenReturn(user);
		
		StringBuilder ticketInfo = new StringBuilder(
				String.valueOf(ticket.getTicketId()));
		ticketInfo.append(";");
		ticketInfo.append(newPassenger.getPassengerName());
		ticketInfo.append(";");
		ticketInfo.append(newPassenger.getPassengerSurname());
		ticketInfo.append(";");
		ticketInfo.append(s1.getStationName());
		ticketInfo.append(";");
		ticketInfo.append(sdf2.format(passengerDep));
		ticketInfo.append(";");
		ticketInfo.append(s2.getStationName());
		ticketInfo.append(";");
		ticketInfo.append(sdf2.format(passengerDest));
		ticketInfo.append(";");
		ticketInfo.append(cost);
		
		registrator.register(info);
		
	}
	
	@Test(expected=ParseException.class)
	public void test5() throws Exception {
		Station s1 = new Station(); s1.setStationId(1);s1.setStationName("s1");Station s2 = new Station(); s2.setStationId(2);s2.setStationName("s2");
		Journey j = new Journey(); j.setJourneyId(3);
		
		Date passengerDep = new Date();
		Date passengerDest = new Date(new Date().getTime()+1000*60*60*24);
		String cost = "1000";
		String journeyData = j.getJourneyId()+";"+passengerDep+";"+passengerDest+";"+cost;
		List<String> allJourneysData = new ArrayList<String>();allJourneysData.add(journeyData);
		PassengerInfo info = new PassengerInfo
				("user",j.getJourneyId()+";0;0;"+s1.getStationId()+";"+s2.getStationId(),"name","surname"," ",allJourneysData);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		
		Passenger newPassenger = new Passenger();newPassenger.setPassengerName(info.getName());newPassenger.setPassengerSurname(info.getSurname());
		newPassenger.setPassengerBirthday(sdf.parse(info.getBirthday()));
				
		Date purchaseDate = new Date();
		Ticket ticket = new Ticket();ticket.setJourney(j);ticket.setPassenger(newPassenger);
		ticket.setStArr(s2);ticket.setStDep(s1);ticket.setTicketId(3); ticket.setPurchaseDate(purchaseDate);
		
		User user = new User();user.setUserLogin("user");user.setUserId(4);
		
		
		Mockito.when(dao.createPassenger(info.getName(), info.getSurname(), sdf.parse(info.getBirthday()))).
		thenReturn(newPassenger);
		Mockito.when(dao.getStation(s1.getStationId())).thenReturn(s1);
		Mockito.when(dao.getStation(s2.getStationId())).thenReturn(s2);
		Mockito.when(dao.createTicket(newPassenger, j,
				s1, s2, purchaseDate)).thenReturn(ticket);
		Mockito.when(dao.getUserByName(info.getCurrentUser())).thenReturn(user);
		
		StringBuilder ticketInfo = new StringBuilder(
				String.valueOf(ticket.getTicketId()));
		ticketInfo.append(";");
		ticketInfo.append(newPassenger.getPassengerName());
		ticketInfo.append(";");
		ticketInfo.append(newPassenger.getPassengerSurname());
		ticketInfo.append(";");
		ticketInfo.append(s1.getStationName());
		ticketInfo.append(";");
		ticketInfo.append(sdf2.format(passengerDep));
		ticketInfo.append(";");
		ticketInfo.append(s2.getStationName());
		ticketInfo.append(";");
		ticketInfo.append(sdf2.format(passengerDest));
		ticketInfo.append(";");
		ticketInfo.append(cost);
		
		TicketInfo TI = registrator.register(info);
		Assert.assertTrue(TI.getTicketInfo().equals(ticketInfo.toString()));
		
	}
	
	

}
