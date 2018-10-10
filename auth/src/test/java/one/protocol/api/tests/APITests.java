package one.protocol.api.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import one.protocol.api.helper.DataProperties;
import one.protocol.api.model.User;
import org.apache.http.client.fluent.Request;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class APITests extends TestBase{

    @DataProvider
    public Iterator<Object[]> registrationDataFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/auth/src/test/resources/CreateUserTestData.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null){
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<User> users = gson.fromJson(json, new TypeToken<List<User>>(){}.getType()); //List<User>.class
            return users.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(description = "list of social networks")
    public void testSocNets() throws IOException {
        String response = Request
                .Get(DataProperties.get("base.url") + "/oauth/sources")
                .execute().returnContent().asString();
        Assert.assertEquals(response, DataProperties.get("socials.networks.response"));
    }

    @Test(description = "Registration tests", dataProvider = "registrationDataFromJson")
    public void testCreateUser(User user) {
        if (user.getEmail().equals("new")) {
            user.setEmail(System.currentTimeMillis() + "@mail.du");
        }
        Response response = createUser(user.getEmail(), user.getPassword(), user.getReadEula());
        Assert.assertEquals(response.getStatusCode(), user.getCode());
        String json = response.asString();
        if(user.getResponce()!= null) Assert.assertEquals(json, user.getResponce());
//        User newuser = new Gson().fromJson(json, User.class);
//        JsonElement parsed = new JsonParser().parse(json);
//        String ips = parsed.getAsJsonObject().get("ips").toString().replaceAll("[\\[\\]]","");
//        Ip ip = new Gson().fromJson(ips, Ip.class);
//        Assert.assertEquals(email, user.getEmail());

    }


    private Response createUser(String email, String password, Boolean readEula) {
        Response response = RestAssured.
                given().
                    params("email", email,
                            "password", password,
                            "readEula", readEula).
                when().
                    post(DataProperties.get("base.url") + "/user/create");
        return response;
    }
}
