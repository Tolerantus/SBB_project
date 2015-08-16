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
import com.dto.JourneyAndPassengers;
import com.entities.Journey;
import com.entities.Passenger;
import com.entities.Station;
import com.entities.Ticket;
import com.service.PassengersInformator;

public class PassengerInformatorTest {
	PassengersInformator informator;
	@Mock
	Dao dao = Mockito.mock(Dao.class);
	@Before
	public void init(){
		informator = new PassengersInformator();
		informator.setDao(dao);
	}
	@Test
	public void testGetInfo() throws ParseException {
		String journeyInfo = "";
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		Journey j = new Journey(); j.setJourneyId(1); journeyInfo += "#" + String.valueOf(j.getJourneyId());
		Date birthday = new Date(); SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Passenger p = new Passenger(); p.setPassengerId(2); p.setPassengerName("name");
		p.setPassengerSurname("surname"); p.setPassengerBirthday(birthday);
		
		Station start = new Station(); start.setStationName("start");
		Station finish = new Station(); finish.setStationName("finish");
		
		
		Ticket ticket = new Ticket(); ticket.setPassenger(p); ticket.setJourney(j);
		ticket.setStDep(start); ticket.setStArr(finish); ticket.setPurchaseDate(sdf.parse("01-01-2015"));
		List<Ticket> tickets = new ArrayList<Ticket>(); tickets.add(ticket);
		
		JourneyAndPassengers input = new JourneyAndPassengers(journeyInfo, null);
		
		Mockito.when(dao.getTicketsOfJourney(j)).thenReturn(tickets);
		Mockito.when(dao.getJourney(j.getJourneyId())).thenReturn(j);
		
		String expectedResult = "";
		
		for (Ticket t : tickets) {
			StringBuilder sb = new StringBuilder();
			sb.append(t.getTicketId()); sb.append(";");
			sb.append(t.getStDep().getStationName()); sb.append(";");
			sb.append(t.getStArr().getStationName()); sb.append(";");
			sb.append(sdf1.format(t.getPurchaseDate())); sb.append(";");
			sb.append(t.getPassenger().getPassengerName()); sb.append(";");
			sb.append(t.getPassenger().getPassengerSurname()); sb.append(";");
			sb.append(sdf.format(t.getPassenger().getPassengerBirthday()));
			expectedResult = sb.toString();
		}
		
		
		JourneyAndPassengers output = informator.getInfo(input);
		
		Assert.assertTrue(expectedResult.equals(output.getPassInfo().get(0)));
		
	}
	@Test(expected=NullPointerException.class)
	public void test2(){
		informator.getInfo(null);
	}
	@Test(expected = NullPointerException.class)
	public void test3(){
		informator.getInfo(new JourneyAndPassengers(null, null));
	}

}
