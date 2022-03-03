package org.com.biomob.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.com.biomob.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
