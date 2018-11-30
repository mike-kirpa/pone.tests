package one.protocol.storefront.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import one.protocol.storefront.helpers.ApplicationManager;
import one.protocol.storefront.models.Browsers;
import one.protocol.storefront.models.Landings;
import one.protocol.storefront.models.Resolutions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        List<Browsers> list = new Gson().fromJson(readJsonFile("browsers.json"), new TypeToken<List<Browsers>>(){}.getType());
        return list.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }


    public String readJsonFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/src/test/resources/JSONData/" + fileName)))) {
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
        List<Resolutions> ResolutionsList = new Gson().fromJson(readJsonFile("resolution.json"), new TypeToken<List<Resolutions>>(){}.getType());
        List<Landings> LandingsList = new Gson().fromJson(readJsonFile("landings.json"), new TypeToken<List<Landings>>(){}.getType());
        for (Landings landing : LandingsList){
            app.goTo().Url(landing.getLandingUrl());
            String [] mode = {"onetime"};
            if (landing.getLandingName() != null) {app.startSetParameters(landing.getLandingName());}
            if (landing.getModeView() != null) {mode = landing.getModeView().split(", ");}
            for (Resolutions resolutions : ResolutionsList){
                for(int i = 0; i < mode.length; i++) {
                    if (landing.getModeView() != null) {app.goTo().modeSwitcher(mode[i]);}
                    app.changeResolution(resolutions.getWidth(), resolutions.getHeight());
                    Thread.sleep(1000);
                    app.takeScreenshot().Screenshot(browsers.getBrowser(), resolutions.getWidth(), resolutions.getHeight(), mode[i], landing.getHeaderSelector());
                }
            }
        }
        app.stop();
    }


}
