package services.test;


import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.NewUserInfo;
import com.dto.UserExist;
import com.entities.UserRole;
import com.mail.MailService;
import com.service.UserRegistrator;

public class UserRegistratorTest {
UserRegistrator registrator;
@Mock
Dao dao = Mockito.mock(Dao.class);
@Mock 
MailService mailService = Mockito.mock(MailService.class);
@Before
public void init(){
registrator = new UserRegistrator();
registrator.setDao(dao);
registrator.setMailService(mailService);
}

	@Test
	public void test() throws Exception {
		NewUserInfo info = new NewUserInfo("user", "password", false);
		Mockito.when(dao.getUserByName("login")).thenReturn(null);
		UserRole role = new UserRole();  Set<UserRole> roles = new HashSet<UserRole>(); roles.add(role);
		Mockito.when(dao.createUser(info.getLogin(), info.getPassword(), info.isAdmin(), roles)).thenReturn(null);
		UserExist expectedResult = new UserExist(false,false);
		UserExist realResult = registrator.register(info);
		
		Assert.assertEquals(expectedResult.isExist(), realResult.isExist());
		Assert.assertEquals(expectedResult.isAdmin(), realResult.isAdmin());

	}

	@Test(expected=NullPointerException.class)
	public void test2() throws Exception {
		
		registrator.register(null);
	}
}
