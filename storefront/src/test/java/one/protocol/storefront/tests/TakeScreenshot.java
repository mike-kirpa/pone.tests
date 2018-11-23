package one.protocol.storefront.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import one.protocol.storefront.helpers.ApplicationManager;
import one.protocol.storefront.models.Browsers;
import one.protocol.storefront.models.Resolutions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sun.plugin2.util.BrowserType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TakeScreenshot  {

    @DataProvider
    public Iterator<Object[]> browsersFromJson() throws IOException {
        String json = readJsonFile("browsers.json");
        Gson gson = new Gson();
        List<Browsers> list = gson.fromJson(json, new TypeToken<List<Browsers>>(){}.getType()); //List<User>.class
        return list.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }


    public String readJsonFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/src/test/resources/" + fileName)))) {
            String json = "";
            String line = reader.readLine();
            while (line != null){
                json += line;
                line = reader.readLine();
            }
            return json;
        }
    }


    @Test(dataProvider = "browsersFromJson")
    public void testTakeScreenshot(Browsers browsers) throws IOException, InterruptedException {
        ApplicationManager app = new ApplicationManager(browsers.getBrowser());
        app.init();
        app.goTo().gotoEditorPage();
        Thread.sleep(5000);
        String json = readJsonFile("resolution.json");
        Gson gson = new Gson();
        List<Resolutions> list = gson.fromJson(json, new TypeToken<List<Resolutions>>(){}.getType()); //List<User>.class
        for (Resolutions resolutions : list){
            app.changeResolution(resolutions.getWidth(),resolutions.getHeight());
            app.takeScreenshot().Screenshot(browsers.getBrowser(), resolutions.getWidth(), resolutions.getHeight());
        }
        app.stop();
    }
}
