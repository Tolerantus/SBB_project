package services.test; 


import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.ArrayList; 
import java.util.Date; 
import java.util.HashSet; 
import java.util.LinkedList;
import java.util.List; 
import java.util.Locale; 
import java.util.Set; 

import org.junit.Assert; 
import org.junit.Before; 
import org.junit.Test; 
import org.mockito.Mock; 
import org.mockito.Mockito; 

import com.dao.Dao;
import com.dto.JourneysInfo; 
import com.dto.StationsForSheduling; 
import com.entities.Direction; 
import com.entities.Journey; 
import com.entities.Route; 
import com.entities.Seats;
import com.entities.Shedule; 
import com.entities.Station; 
import com.entities.Train;
import com.service.GetAppropriateJourneys; 

public class GetAppropriateJourneysTest {
	
	GetAppropriateJourneys getter; 
	@Mock
	private Dao dao = Mockito.mock(Dao.class); 
	
	@Before
	public void init(){
		getter = new GetAppropriateJourneys(); 
		getter.setDao(dao); 
	}
	
	@Test(expected=NullPointerException.class)
	public void test() throws ParseException {
		getter.getJourneys(null); 
	}
	
	@Test
	public void testSimpleSheduling() throws ParseException {
		Station start = new Station(); 
		start.setStationName("Start"); 
		start.setStationId(1); 
		Station finish = new Station(); 
		finish.setStationName("Finish"); 
		finish.setStationId(1); 
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm  dd MMM", Locale.US); 
		
		Route r = new Route(); r.setRouteId(2); 
		List<Route> routes= new ArrayList<Route>(); routes.add(r); 
		Set<Station> stOnRoute = new HashSet<Station>(); stOnRoute.add(start); 
		
		Journey journey = new Journey();  journey.setRoute(r);  journey.setTimeDep(new Date(new Date().getTime()+100*60*1000)); 
		List<Journey> journeys = new ArrayList<Journey>();  journeys.add(journey); journey.setJourneyId(3); 
		Direction direction = new Direction();  direction.setStArr(finish);  direction.setStDep(start); 
		Shedule shedule = new Shedule();  shedule.setStep(0); shedule.setDirection(direction); 
		List<Shedule> steps = new ArrayList<Shedule>();  steps.add(shedule); 
		
		Direction d = new Direction(); d.setStDep(start); 
		d.setTime((new Date()).getTime()+1); 
		StringBuilder journeyData = new StringBuilder(String.valueOf(journey.getJourneyId())); 
		journeyData.append(";"); 
		journeyData.append(sdf1.format(journey.getTimeDep())); 
		journeyData.append(";"); 
		journeyData.append(start.getStationName()); 
		journeyData.append(";"); 
		journeyData.append(finish.getStationName()); 
		
		Mockito.when(dao.getStationByName(start.getStationName())).thenReturn(start); 
		Mockito.when(dao.getRoutesContainStation(start.getStationId())).thenReturn(routes); 
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps); 
		Mockito.when(dao.getJourneysByRoutes(routes)).thenReturn(journeys); 
		
		
		String result = getter.simpleSheduling(start).getJourneyStringData().get(0); 
		String expectedresult = journeyData.toString(); 
		Assert.assertTrue(result.equals(expectedresult)); 
	}
	
	@Test
	public void testGetAppropriateJourneys() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm dd MMM", Locale.US); 

		Station stDep = new Station(); stDep.setStationId(0); 
		Station stArr = new Station(); stArr.setStationId(1); 
		String date = sdf.format(new Date()); 
				
		Route r = new Route();  r.setRouteId(2); 
		List<Route> routes= new ArrayList<Route>(); routes.add(r); 
		Set<Station> stOnRoute = new HashSet<Station>(); stOnRoute.add(stDep); stOnRoute.add(stArr); 
		
		Train train = new Train(); train.setTrainSeats(1);
		
		Journey journey = new Journey(); journey.setRoute(r); journey.setTrain(train);
		journey.setTimeDep(new Date(new Date().getTime() + 100*60*1000)); 
		List<Journey> journeys = new ArrayList<Journey>(); journeys.add(journey); journey.setJourneyId(3); 
		
		Direction d = new Direction(); d.setStDep(stDep); d.setStArr(stArr); 
		d.setTime((new Date()).getTime()); d.setCost(1000); 
		Shedule shedule = new Shedule(); shedule.setStep(0); shedule.setDirection(d);
		List<Shedule> steps = new ArrayList<Shedule>(); steps.add(shedule); 
		
		Seats seats = new Seats(); seats.setEmptySeats(1); seats.setJourney(journey); seats.setRouteStep(shedule.getStep());
		List<Seats> list = new LinkedList<Seats>(); list.add(seats);
		
		
		StringBuilder journeyData = new StringBuilder(String.valueOf(journey.getJourneyId())); 
		journeyData.append(";"); 
		journeyData.append(sdf1.format(journey.getTimeDep())); 
		journeyData.append(";"); 
		journeyData.append(sdf1.format(new Date(journey.getTimeDep().getTime()+d.getTime()))); 
		journeyData.append(";"); 
		journeyData.append(d.getCost()); 
		journeyData.append(";");
		journeyData.append(seats.getEmptySeats());
		
		
		StringBuilder info = new StringBuilder(String.valueOf(journey.getJourneyId())); 
		info.append(";"); 
		info.append(String.valueOf(shedule.getStep())); 
		info.append(";"); 
		info.append(String.valueOf(shedule.getStep())); 
		info.append(";"); 
		info.append(stDep.getStationId()); 
		info.append(";"); 
		info.append(stArr.getStationId()); 
		
		
		Mockito.when(dao.getRoutesContainStations(stDep.getStationId(), stArr.getStationId())).thenReturn(routes); 
		Mockito.when(dao.getJourneysByRoutes(routes)).thenReturn(journeys); 
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps); 
		Mockito.when(dao.getSeatsOnJourney(journey.getJourneyId())).thenReturn(list);
		
		JourneysInfo result = getter.getAppropriateJourneys(stDep, stArr, date); 
		String expectedResult1 = journeyData.toString(); 
		String expectedResult2 = info.toString();
		
		Assert.assertTrue(result.getJourneyStringData().get(0).equals(expectedResult1)); 
		Assert.assertTrue(result.getJourneyHelpInfo().get(0).equals(expectedResult2)); 
	}
	@Test(expected=NullPointerException.class)
	public void test4() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		Station stDep = new Station(); stDep.setStationId(0); 
		Station stArr = new Station(); stArr.setStationId(1); 
		String date = sdf.format(new Date()); 
				
		Route r = new Route(); r.setRouteId(2); 
		List<Route> routes= new ArrayList<Route>(); routes.add(r); 
		Set<Station> stOnRoute = new HashSet<Station>(); stOnRoute.add(stDep); stOnRoute.add(stArr); 
		
		Journey journey = new Journey(); journey.setRoute(r); 
		journey.setTimeDep(new Date(new Date().getTime()+100*60*1000)); 
		List<Journey> journeys = new ArrayList<Journey>(); journeys.add(journey); journey.setJourneyId(3); 
		
		Shedule shedule = new Shedule(); shedule.setStep(0); 
		List<Shedule> steps = new ArrayList<Shedule>(); steps.add(shedule); 
		
		Direction d = new Direction(); d.setStDep(stDep); d.setStArr(stArr); 
		d.setTime((new Date()).getTime()); d.setCost(1000); 
		
		
		Mockito.when(dao.getAllRoutes()).thenReturn(routes); 
		Mockito.when(dao.getAllJourneys()).thenReturn(journeys); 
		Mockito.when(dao.getRoute(Mockito.anyInt())).thenReturn(r); 
		Mockito.when(dao.getAllStationsOnRoute(Mockito.anyInt())).thenReturn(stOnRoute); 
		Mockito.when(dao.getDirection(Mockito.anyInt())).thenReturn(d); 
		Mockito.when(dao.getShedulesOfRoute(Mockito.anyInt())).thenReturn(steps); 
		
		JourneysInfo result = getter.getAppropriateJourneys(stDep, null, date); 
		Assert.assertTrue(result.getJourneyStringData().isEmpty()); 
		Assert.assertTrue(result.getJourneyHelpInfo().isEmpty()); 
		
		result = getter.getAppropriateJourneys(null, stArr, date); 
		Assert.assertTrue(result.getJourneyStringData().isEmpty()); 
		Assert.assertTrue(result.getJourneyHelpInfo().isEmpty()); 
	}
	@Test(expected = NullPointerException.class)
	public void test5() throws ParseException{
		getter.getJourneys(new StationsForSheduling(null, null, null, null)); 
	}
}
