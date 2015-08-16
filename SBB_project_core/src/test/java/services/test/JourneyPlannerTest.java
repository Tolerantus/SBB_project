package services.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;




import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.NewJourneyInfo;
import com.entities.Direction;
import com.entities.Journey;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Train;
import com.service.JourneyPlanner;

public class JourneyPlannerTest {
private JourneyPlanner planner;
@Mock
Dao dao = Mockito.mock(Dao.class);
@Before
public void init(){
	planner = new JourneyPlanner();
	planner.setDao(dao);
}
	@Test
	public void test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		String routeInfo = "1";
		String date = "2015-12-01";
		String time = "00:00";
		Route r = new Route();r.setRouteId(1);r.setRouteName("route");
		Direction d = new Direction();d.setDirectionId(2);
		Shedule s = new Shedule();s.setDirection(d);s.setStep(0);
		List<Shedule> steps = new ArrayList<Shedule>();steps.add(s);
		Train train = new Train();train.setTrainId(3);
		List<Train> trains = new ArrayList<Train>();trains.add(train);
		Journey j = new Journey(4, r, train, sdf.parse(date));
		
		Mockito.when(dao.getRoute(Integer.parseInt(routeInfo))).thenReturn(r);
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps);
		Mockito.when(dao.getAllTrains()).thenReturn(trains);
		Mockito.when(dao.getAllJourneysOfTrain(train.getTrainId())).thenReturn(new ArrayList<Journey>());
		Mockito.when(dao.createJourney(train, r, sdf.parse(date))).thenReturn(j);
		
		NewJourneyInfo info1 = new NewJourneyInfo(routeInfo, date, time, null, null, null, false);
		NewJourneyInfo info2 = new NewJourneyInfo(routeInfo, date, time, String.valueOf(j.getJourneyId()), r.getRouteName(), String.valueOf(train.getTrainId()), false);
		NewJourneyInfo info3 = planner.plan(info1);
		
		Assert.assertTrue(info3.getJourneyId().equals(info2.getJourneyId()));
		Assert.assertTrue(info3.getRouteInfo().equals(info2.getRouteInfo()));
		Assert.assertTrue(info3.getRouteName().equals(info2.getRouteName()));
		Assert.assertTrue(info3.getTime().equals(info2.getTime()));
		Assert.assertTrue(info3.getTrain().equals(info2.getTrain()));
		
	}
	
	@Test
	public void test2() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		String routeInfo = "1";
		String date = "2015-01-01";
		String time = "00:00";
		Route r = new Route();r.setRouteId(1);r.setRouteName("route");
		Direction d = new Direction();d.setDirectionId(2);
		Shedule s = new Shedule();s.setDirection(d);s.setStep(0);
		List<Shedule> steps = new ArrayList<Shedule>();steps.add(s);
		Train train = new Train();train.setTrainId(3);
		List<Train> trains = new ArrayList<Train>();trains.add(train);
		Journey j = new Journey(4, r, train, sdf.parse(date));
		
		Mockito.when(dao.getRoute(Integer.parseInt(routeInfo))).thenReturn(r);
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps);
		Mockito.when(dao.getAllTrains()).thenReturn(trains);
		Mockito.when(dao.getAllJourneysOfTrain(train.getTrainId())).thenReturn(new ArrayList<Journey>());
		Mockito.when(dao.createJourney(train, r, sdf.parse(date))).thenReturn(j);
		
		NewJourneyInfo input = new NewJourneyInfo(routeInfo, date, time, null, null, null, false);
		NewJourneyInfo expected = new NewJourneyInfo(routeInfo, "", "", null, null, null, false);
		NewJourneyInfo result = planner.plan(input);
		
		Assert.assertTrue(result.getJourneyId() == expected.getJourneyId());
		Assert.assertTrue(result.getRouteInfo().equals(expected.getRouteInfo()));
		Assert.assertTrue(result.getRouteName() == expected.getRouteName());
		Assert.assertTrue(result.getTime().equals(expected.getTime()));
		Assert.assertTrue(result.getTrain() == expected.getTrain());
		
	}
	
	@Test(expected=NullPointerException.class)
	public void test3() throws ParseException {
		planner.plan(null);
	}
	@Test(expected=NullPointerException.class)
	public void test4() throws ParseException {
		planner.plan(new NewJourneyInfo(null,null,null,null,null,null,false));
	}
	@Test(expected=NullPointerException.class)
	public void test5() throws ParseException {
		planner.plan(new NewJourneyInfo("1",null,null,null,null,null,false));
	}
	@Test(expected=NullPointerException.class)
	public void test6() throws ParseException {
		planner.plan(new NewJourneyInfo("1"," ",null,null,null,null,false));
	}
	@Test(expected=NumberFormatException.class)
	public void test7() throws ParseException {
		planner.plan(new NewJourneyInfo("1"," "," ",null,null,null,false));
	}
	@Test
	public void test8() throws ParseException {

		String routeInfo = "1";
		String date = "2015-12-01";
		String time = "00:00";
		Route r = new Route();r.setRouteId(1);r.setRouteName("route");
		Direction d = new Direction();d.setDirectionId(2);
		Shedule s = new Shedule();s.setDirection(d);s.setStep(0);
		List<Shedule> steps = new ArrayList<Shedule>();steps.add(s);
		
		List<Train> trains = new ArrayList<Train>();
		
		Mockito.when(dao.getRoute(Integer.parseInt(routeInfo))).thenReturn(r);
		Mockito.when(dao.getShedulesOfRoute(r.getRouteId())).thenReturn(steps);
		Mockito.when(dao.getAllTrains()).thenReturn(trains);
		
		
		NewJourneyInfo info1 = new NewJourneyInfo(routeInfo, date, time, null, null, null, false);
		NewJourneyInfo info3 = planner.plan(info1);
		
		Assert.assertTrue(info3.getJourneyId()==null);
		Assert.assertTrue(info3.getRouteName()==null);
		Assert.assertTrue(info3.getTrain()==null);
		Assert.assertTrue(info3.isTrainsLack());
		
	}
	
}
