package org.planit;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith( Cucumber.class )
@CucumberOptions(monochrome = false, plugin = { "html:target/cucumber-html-report", "json:target/cucumber.json",
        "pretty:target/cucumber-pretty.txt", "usage:target/cucumber-usage.json",
        "junit:target/cucumber-results.xml" }, features = "src/test/resources/feature", glue = { "org.planit.steps" })
public class RunCucumberTest
{

}