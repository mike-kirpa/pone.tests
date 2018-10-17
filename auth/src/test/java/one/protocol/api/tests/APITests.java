package one.protocol.api.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import one.protocol.api.helper.DataProperties;
import one.protocol.api.model.AccessToken;
import one.protocol.api.model.RefreshToken;
import one.protocol.api.model.User;
import org.apache.http.client.fluent.Request;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class APITests extends TestBase{

    @DataProvider
    public Iterator<Object[]> registrationDataFromJson() throws IOException {
        return readJsonFile("CreateUserTestData.json");
    }

    @DataProvider
    public Iterator<Object[]> authDataFromJson() throws IOException {
        return readJsonFile("AuthUserTestData.json");
    }

    public Iterator<Object[]> readJsonFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/src/test/resources/" + fileName)))) {
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


    /*
    Тесты для проверки возвращаемого списка соц сетей через который возможно авторизоваться. Сверяется с ожидаемым списком.
     */
    @Test(enabled = true, description = "list of social networks")
    public void testSocNets() throws IOException {
        String response = Request
                .Get(DataProperties.get("base.url") + "/oauth/sources")
                .execute().returnContent().asString();
        Assert.assertEquals(response, DataProperties.get("socials.networks.response"));
    }

    /*
    Тесты для проверки регистрации пользователя. Сверяется возращаемый код и тело ответа с ожидаемыми
     */
    @Test(enabled = true, description = "Registration tests", dataProvider = "registrationDataFromJson")
    public void testCreateUser(User user) {
        Response response = createUser(user);
        Assert.assertEquals(response.getStatusCode(), user.getCode());
        String json = response.asString();
        if(user.getResponce()!= null) Assert.assertEquals(json, user.getResponce());
//        User newuser = new Gson().fromJson(json, User.class);
//        JsonElement parsed = new JsonParser().parse(json);
//        String ips = parsed.getAsJsonObject().get("ips").toString().replaceAll("[\\[\\]]","");
//        Ip ip = new Gson().fromJson(ips, Ip.class);
//        Assert.assertEquals(email, user.getEmail());

    }

    @Test(enabled = true, description = "Authentication  tests", dataProvider = "authDataFromJson")
    public void testAuthUser(User user) {
        Response response;
        //Проверяем что это кейсы с получением капчи (429) и блокированием пользователя (423)
        if(user.getCode() == 429 || user.getCode() == 423) {
            //создаем пользователя
            Assert.assertEquals(createUser(user).getStatusCode(), 200);
            //изменяем пароль для авторизации на неверный
            user.setPassword("wrong");
            //n попыток авторизаций с неверным паролем с проверкой возврщаемого кода
            for(int i = 0; i < 3; i++) {
                Assert.assertEquals(authUser(user).getStatusCode(), 400);
            }
            //дополнительная попытка авторизации с проверкой получения кода запроса капчи
            Assert.assertEquals(authUser(user).getStatusCode(), 429);
            //m попыток авторизаций с неверным паролем после получения капчи
            if(user.getCode() == 423) {
                for(int i = 0; i < 6; i++) {
                    Assert.assertEquals(authUser(user).getStatusCode(), 429);
                }
                //дополнительная попытка авторизации с проверкой получения кода блокировки пользователя на 10 минут
                Assert.assertEquals(authUser(user).getStatusCode(), 423);
            }
        }
        //авторизация для кейсов с успешной авторизацией
        response = authUser(user);
        String json = response.asString();
        //сверяем код от сервера и ожидаемый для данного кейса
        Assert.assertEquals(response.getStatusCode(), user.getCode());
        //сверяем тело ответа и ожидаемый ответ для данного кейса
        if(user.getResponce() != null) Assert.assertEquals(json, user.getResponce());
    }

    //авторизация пользователя
    private Response authUser(User user) {
        Response response = RestAssured.
                given().
                params("email", user.getEmail(),
                        "password", user.getPassword(),
                        "captcha", user.getCaptcha()).
                when().
                post(DataProperties.get("base.url") + "/api/v1/user/login");
        return response;
    }

    //регистрация пользователя
    private Response createUser(User user) {
        if (user.getEmail().equals("new")) {
            long random = System.currentTimeMillis();
            user.setEmail(Long.toString(random) + "@mail.du");
            if(user.getPassword() == null) user.setPassword(Long.toString(random));
        }
        Response response = RestAssured.
                given().
                    params("email", user.getEmail(),
                            "password", user.getPassword(),
                            "readEula", user.getReadEula()).
                when().
                    post(DataProperties.get("base.url") + "/api/v1/user/create");
        return response;
    }

    private String toParse(String json, String token) {
        JsonElement parsed = new JsonParser().parse(json);
        String parse = parsed.getAsJsonObject().get(token).toString();
        return parse;
    }

    private void getTokenFromResponce(String json){
        String accessTokenFromResponce = toParse(json, "accessToken");
        AccessToken accessToken = new Gson().fromJson(accessTokenFromResponce, AccessToken.class);
        String refreshTokenFromResponce = toParse(json, "refreshToken");
        RefreshToken refreshToken = new Gson().fromJson(refreshTokenFromResponce, RefreshToken.class);
    }
}
