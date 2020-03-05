//package com.moran.coupons.users;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
////import org.junit.Test;
////import org.junit.jupiter.api.AfterEach;
////import org.junit.platform.runner.JUnitPlatform;
////import org.junit.runner.RunWith;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
//import org.junit.jupiter.api.RepetitionInfo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInfo;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//
////
//import com.moran.coupons.dao.users.UsersDao;
//import com.moran.coupons.data.beans.User;
//import com.moran.coupons.data.beans.UserLoginDetails;
//import com.moran.coupons.data.internal.UserAfterLoginData;
//import com.moran.coupons.enums.UserType;
//
//
//@RunWith(JUnitPlatform.class)
//public class UsersDaoTest {
//
//	private UsersDao usersDao;
//	private UserAfterLoginData userAfterLoginData;
//
//	public UsersDaoTest() {
//		this.usersDao = new UsersDao();
//		this.userAfterLoginData = new UserAfterLoginData();
//	}
//
//
//	@Test
//	public void testCreateCustomerTypeUser() throws Exception {
//		User user = new User("Yossi", "123456Aa", "mmm@gmail.com", UserType.CUSTOMER, null);
//		long id = this.usersDao.createUser(user);
//		UserLoginDetails userLoginDetails = new UserLoginDetails("mmm@gmail.com", "123456Aa");
//		userAfterLoginData = this.usersDao.login(userLoginDetails);
//		assertNotNull(userAfterLoginData);
//		assertEquals(userAfterLoginData.getUserType(), UserType.CUSTOMER);
//		assertEquals(userAfterLoginData.getId(), id);
//		assertNull(userAfterLoginData.getCompanyId());
//		//	return userAfterLoginData;
//	}
//
//
//	//	@Test
//	//	public UserAfterLoginData testLogin() throws Exception {
//	//		return userAfterLoginData;
//	//	}
//
//	//	@Test
//	//	public void testDeleteUser() throws Exception {
//	//		User user = new User("Yossi", "123456Aa", "x@y.com", UserType.CUSTOMER, null);
//	//		long id = this.usersDao.createUser(user);
//	//		
//	//		this.usersDao.removeUser(id);
//	//		User createdUser = this.usersDao.getUserByUserId(id);
//	//		assertNull(createdUser);
//	//	}
//
//	//	@RepeatedTest(5)
//	//    public void addNumber(TestInfo testInfo, RepetitionInfo repetitionInfo) 
//	//    {
//	//        System.out.println("Running test -> " + repetitionInfo.getCurrentRepetition());
//	//       // Assertions.assertEquals(2, Calculator.add(1, 1), "1 + 1 should equal 2");
//	//    }
//
//	@AfterEach
//	public void cleanUpEach() throws Exception{
//		System.out.println("After Each cleanUpEach() method called");
//		this.usersDao.removeUser(userAfterLoginData.getId());
//	}
//
//	//	@AfterEach
//	//	public void after() {
//	//this.usersDao.removeUser(userAfterLoginData.getId());
//	//		System.out.println("123");
//	//	}
//
//}
