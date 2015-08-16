package services.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.HoursMinutesCost;
import com.dto.NewRouteSummary;
import com.dto.RequiredDataForNewRoute;
import com.entities.Direction;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Station;
import com.service.FinalRouteBuilder;

public class FinalRouteBuilderTest {
	FinalRouteBuilder builder;
@Mock
Dao dao = Mockito.mock(Dao.class);
@Before
public void init(){
builder = new FinalRouteBuilder();
builder.setDao(dao);
}
	@Test
	public void test() {
		HoursMinutesCost data = new HoursMinutesCost("1","1","1000");
		List<HoursMinutesCost> hdc = new ArrayList<HoursMinutesCost>();hdc.add(data);
		Route new_r = new Route();new_r.setRouteName("new");
		Station s1 = new Station();s1.setStationName("s1");s1.setStationId(1);
		Station s2 = new Station();s2.setStationName("s2");s2.setStationId(2);
		Direction d = new Direction(3, s1, s2, (1*60+1)*60*1000, 1000);
	
	Mockito.when(dao.getAllRoutes()).thenReturn(new ArrayList<Route>());
	Mockito.when(dao.getStationByName("s1")).thenReturn(s1);
	Mockito.when(dao.getStationByName("s2")).thenReturn(s2);
	Mockito.when(dao.createDirection(s1, s2, d.getTime(), d.getCost())).thenReturn(d);
	Mockito.when(dao.createRoute(new_r.getRouteName())).thenReturn(new_r);
	Mockito.when(dao.getDirectionByStartFinish(s1.getStationId(), s2.getStationId())).thenReturn(d);
	Mockito.when(dao.createShedule(d, new_r, 0)).thenReturn(new Shedule(3,d, new_r, 0));
		
	List<String> newRoute = new ArrayList<String>();
	newRoute.add(s1.getStationName());
	newRoute.add(s2.getStationName());
	
	List<String> newDirections = new ArrayList<String>();
	newDirections.add(s1.getStationName()+"/"+s2.getStationName());
	RequiredDataForNewRoute reqData = new RequiredDataForNewRoute(newRoute, newDirections, hdc, "new");

	
	
	List<String> routeInfo = new ArrayList<String>();
	routeInfo.add(new_r.getRouteName());
	routeInfo.add(String.valueOf(data.getHours()));
	routeInfo.add(String.valueOf(data.getMinutes()));
	routeInfo.add(String.valueOf(Double.parseDouble(data.getCost())));
	for (String station : newRoute) {
		routeInfo.add(station);
	}
	NewRouteSummary summary = builder.build(reqData);
	Assert.assertTrue(summary.getSummary().equals(routeInfo));
	
	}
	
	@Test(expected=NullPointerException.class)
	public void test2(){
		builder.build(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void test3(){
		builder.build(new RequiredDataForNewRoute(null, null, null, null));
	}
	
	@Test(expected=NullPointerException.class)
	public void test4(){
		builder.build(new RequiredDataForNewRoute(new ArrayList<String>(), null, null, null));
	}
	
	@Test(expected=NullPointerException.class)
	public void test5(){
		builder.build(new RequiredDataForNewRoute(new ArrayList<String>(), new ArrayList<String>(), null, null));
	}
	
	@Test(expected=NullPointerException.class)
	public void test6(){
		builder.build(new RequiredDataForNewRoute(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<HoursMinutesCost>(), null));
	}
	
	@Test(expected=NullPointerException.class)
	public void test7(){
		builder.build(new RequiredDataForNewRoute(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<HoursMinutesCost>(), ""));
	}
	
	@Test
	public void test8() {
		Route r = new Route();
		r.setRouteName("old");
		Mockito.when(dao.getRouteByName(r.getRouteName())).thenReturn(r);
		NewRouteSummary info=builder.build(new RequiredDataForNewRoute(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<HoursMinutesCost>(), "old"));
		Assert.assertTrue(info.getSummary() == null);
	}

}
