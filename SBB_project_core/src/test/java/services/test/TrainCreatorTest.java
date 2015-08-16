package services.test;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.NewTrainInfo;
import com.service.TrainCreator;

public class TrainCreatorTest {

	TrainCreator creator;
	@Mock
	private Dao dao = Mockito.mock(Dao.class);
	@Before
	public void createChecker(){
		creator = new TrainCreator();
		creator.setDao(dao);
	}
	
	@Test
	public void createTest() {
		NewTrainInfo info = new NewTrainInfo("100");
		creator.create(info);
	}

}
