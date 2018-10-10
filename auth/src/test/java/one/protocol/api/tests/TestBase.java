package one.protocol.api.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod
    public void logTestStart(Method m, Object[] p){
        logger.info("Start test" + m.getName() + "with parameters" + Arrays.asList(p));

    }

    @AfterMethod(alwaysRun = true)
    public void losTestStop(Method m, Object[] p){
        logger.info("Stop test" + m.getName() + "with parameters" + Arrays.asList(p));
    }
}
