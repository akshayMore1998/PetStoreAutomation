package api.test;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;


public class UserTests {

	Faker faker;
	User userPayload;
	public Logger logger;

	@BeforeClass
	public void setUp() {

		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setEmail(faker.internet().password(5, 10));
		userPayload.setEmail(faker.phoneNumber().cellPhone());
		
		
		logger=LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testPostUser() {

		logger.info("****************Creating usuer******************");
		Response res = UserEndPoints.createUser(userPayload);
		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
		logger.info("***************user created*********************");
	}

	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("***************testGetUserByName()  starts*********************");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("***************testGetUserByName()   ends*********************");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("***************testUpdateUserByName()   starts*********************");
		//update data by usingpayload 
		
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		

		Response res = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		res.then().log().body();
		Assert.assertEquals(res.statusCode(), 200);
		
		//checking data after updation 
		
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("***************testUpdateUserByName()   ends*********************");
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		
		logger.info("***************testDeleteUserByName()   stars*********************");
		Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("***************testDeleteUserByName()   ends*********************");
	}
}
