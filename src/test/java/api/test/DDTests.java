package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority = 1,dataProvider = "Data",dataProviderClass = DataProviders.class)
	public void testPostUser(String userId,String userName,String fName,String lName,String email,String pwd,String no) {
		
		User userPayload= new User();
		
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(email);
		userPayload.setPassword(pwd);
		userPayload.setPhone(no);
		
		
		Response res = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(res.statusCode(), 200);
	}
	
	@Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {
		
		if(userName==null) {
			System.out.println("Wrong user name ");
		}else
		{
			Response res =UserEndPoints.deleteUser(userName);
			Assert.assertEquals(res.statusCode(), 200);
		}
		
	}
}
