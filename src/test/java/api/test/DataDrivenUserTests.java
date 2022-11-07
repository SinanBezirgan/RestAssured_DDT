package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenUserTests {

    @Test(priority = 1,dataProvider = "Data",dataProviderClass = DataProviders.class)
    public void testPostUsers(String userID,String username,String fname,String lname,String useremail,String password,String phone){
        User userPayload = new User();

        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(username);
        userPayload.setFirstname(fname);
        userPayload.setLastname(lname);
        userPayload.setEmail(useremail);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);


        Response res= UserEndPoints.createUser(userPayload);
        Assert.assertEquals(res.getStatusCode(),200);
        res.then().log().all();
    }

    @Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
    public void testDeleteUserbyName(String username){
        Response res= UserEndPoints.deleteUser(username);
        Assert.assertEquals(res.getStatusCode(),200);
    }

}
