package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features ="src/main/java/feature",
                 glue = {"stepDefinitions"},
                 plugin = {"pretty","html:target/cucumber-reports"},
                 monochrome = false)
public class TestRunner {
}
