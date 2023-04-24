package cucumber.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/Feature_Tests.feature",
    glue = {"stepDefinitions"},
    monochrome = true
)
public class TestRunner {

}
