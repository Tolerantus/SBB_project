package services.test;



import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.dao.Dao;
import com.dto.UserExist;
import com.dto.UserInfo;
import com.entities.User;
import com.entities.UserRole;
import com.service.CheckUser;


public class CheckUserTest {
	CheckUser checkuser;
	@Mock
	private Dao dao = Mockito.mock(Dao.class);
	
	@Before
	public void init() {
		checkuser = new CheckUser();
		checkuser.setDao(dao);
	}
	
	@Test
	public void test() throws Exception {
		UserInfo info = new UserInfo("login", "password");
		UserExist exist = new UserExist(true, false);
		UserRole role = new UserRole(); Set<UserRole> roles = new HashSet<UserRole>(); roles.add(role);
		User user = new User(0,"login", "password", false, roles);
		
		Mockito.when(dao.getUserByName(info.getLogin())).thenReturn(user);
		Assert.assertEquals(exist.isAdmin(), checkuser.check(info).isAdmin());
		Assert.assertEquals(exist.isExist(), checkuser.check(info).isExist());
	}
	
	@Test(expected=NullPointerException.class)
	public void test2() throws Exception{
		UserInfo info = new UserInfo("login", "password");
		UserRole role = new UserRole(); Set<UserRole> roles = new HashSet<UserRole>(); roles.add(role);
		User user = new User(0,"login", "password", false, roles);
		
		Mockito.when(dao.getUserByName(info.getLogin())).thenReturn(user);
		checkuser.check(null);
	}
	
	@Test
	public void test3() throws Exception {
		UserInfo info = new UserInfo("login", "password");
		Mockito.when(dao.getUserByName(info.getLogin())).thenReturn(null);
		Assert.assertTrue(!checkuser.check(info).isExist());
		Assert.assertTrue(!checkuser.check(info).isAdmin());
	}
	
	

}
