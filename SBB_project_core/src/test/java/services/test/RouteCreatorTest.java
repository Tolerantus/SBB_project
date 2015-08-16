package services.test;


import java.util.ArrayList;
import java.util.List;




import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.NewRouteStartAndFinish;
import com.dto.RouteStationList;
import com.entities.Station;
import com.service.RouteCreator;

public class RouteCreatorTest {
	RouteCreator creator;
	@Mock
	Dao dao = Mockito.mock(Dao.class);
	@Before
	public void init(){
		creator = new RouteCreator();
		creator.setDao(dao);
	}
	@Test
	public void test() {
		String typedDep = "station1";
		Station st1 = new Station();st1.setStationId(1);st1.setStationName(typedDep);
		String typedArr = "station2";
		Station st2 = new Station();st2.setStationId(2);st2.setStationName(typedArr);
		List<String> newStations = new ArrayList<String>();newStations.add(typedDep);newStations.add(typedArr);
		String selectDep = "select1";
		Station st3 = new Station();st3.setStationId(3);st3.setStationName(selectDep);
		String selectArr = "select2";
		Station st4 = new Station(); st4.setStationId(4);st4.setStationName(selectArr);
		List<Station> oldStations = new ArrayList<Station>();oldStations.add(st3);oldStations.add(st4);
		List<String> oldStationNamings = new ArrayList<String>();oldStationNamings.add(st3.getStationName());
		oldStationNamings.add(st4.getStationName());
		NewRouteStartAndFinish input = new NewRouteStartAndFinish(typedDep, typedArr, selectDep, selectArr);
		
		
		Mockito.when(dao.createStation(typedDep)).thenReturn(st1);
		Mockito.when(dao.createStation(typedArr)).thenReturn(st2);
		Mockito.when(dao.getStationByName(typedDep)).thenReturn(st1);
		Mockito.when(dao.getStationByName(typedArr)).thenReturn(st2);
		Mockito.when(dao.getStationByName(selectDep)).thenReturn(st3);
		Mockito.when(dao.getStationByName(selectArr)).thenReturn(st4);
		
		RouteStationList output = creator.append(input);
		Assert.assertTrue(newStations.equals(output.getRoute()));
		
		Mockito.when(dao.getAllStations()).thenReturn(oldStations);
		
		NewRouteStartAndFinish input2 = new NewRouteStartAndFinish("", "", selectDep, selectArr);
		
		RouteStationList output2 = creator.append(input2);
		Assert.assertTrue(oldStationNamings.equals(output2.getRoute()));
		
		NewRouteStartAndFinish input3 = new NewRouteStartAndFinish(typedDep, "", selectDep, selectArr);
		RouteStationList output3 = creator.append(input3);
		List<String> stNames = new ArrayList<String>(); stNames.add(typedDep);stNames.add(selectArr);
		
		Assert.assertTrue(stNames.equals(output3.getRoute()));
		
	}
	@Test(expected=NullPointerException.class)
	public void test2(){
		creator.append(null);
	}
	@Test(expected=NullPointerException.class)
	public void test3(){
		creator.append(new NewRouteStartAndFinish(null, null, null, null));
	}
	@Test(expected=NullPointerException.class)
	public void test4(){
		creator.append(new NewRouteStartAndFinish("", "", "", ""));
	}
}
