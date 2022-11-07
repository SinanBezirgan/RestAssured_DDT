package api.test;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {
    Faker faker ;
    User userPayload;



    @BeforeClass
    public void setup(){
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstname(faker.name().firstName());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        //logs

    }

    @Test(priority = 1)
    public void testPostUser(){
      Response response= UserEndPoints2.createUser(userPayload);
      response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

    }
    @Test(priority = 2)
    public void testGetUserByName(){
      Response response =  UserEndPoints2.readUser(this.userPayload.getUsername());
      response.then().log().all();
      Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 3)
    public void updateUserByName(){
        //update data using payload
        userPayload.setFirstname(faker.name().firstName());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        Response response =  UserEndPoints2.updateUser(userPayload,this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        //checking data after update
        Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
    }

    @Test(priority = 4)
    public void testDeleteUserByName(){
       Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
       Assert.assertEquals(response.getStatusCode(),200);
    }

}
