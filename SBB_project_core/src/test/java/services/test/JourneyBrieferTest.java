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
import com.dto.FilteredJourneysInfo;
import com.entities.Journey;
import com.entities.Route;
import com.entities.Train;
import com.service.JourneyBriefer;

public class JourneyBrieferTest {
JourneyBriefer briefer;
@Mock
Dao dao = Mockito.mock(Dao.class);
@Before
public void init(){
	briefer = new JourneyBriefer();
	briefer.setDao(dao);
}
	@Test
	public void test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		List<Journey> jours = new ArrayList<Journey>();
		Train t = new Train(); t.setTrainId(2);
		Route r = new Route();r.setRouteId(1); r.setRouteName("name");
		Journey j = new Journey(); j.setRoute(r); j.setTimeDep(sdf.parse("2015-01-01")); j.setTrain(t); jours.add(j);
		FilteredJourneysInfo info = new FilteredJourneysInfo(null, null, null);
		
		
		Mockito.when(dao.getJourneysBetween(null, null)).thenReturn(jours);
		String expected = "#" + j.getJourneyId() + "; route "
				+ (j.getRoute()).getRouteName() + "; department " + sdf1.format(j.getTimeDep()) + "; train #" + j.getTrain().getTrainId();
		
		info = briefer.getInfo(info);
		
		Assert.assertTrue(info.getJourneys().get(0).equals(expected));
	}
	
	@Test(expected=NullPointerException.class)
	public void test2() throws ParseException{
		List<Journey> jours = new ArrayList<Journey>();
		Route r = new Route(); r.setRouteId(1); r.setRouteName("name");
		Journey j = new Journey(); j.setRoute(r); jours.add(j);
		
		Mockito.when(dao.getAllJourneys()).thenReturn(jours);
		briefer.getInfo(null);
	}

}
