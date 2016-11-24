package com.sc.csl.retail.crm.test;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.sc.csl.retail.crm.LeadsSvcApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadsSvcApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
public class HealthIndicatorTests {

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context).build());
    }

    @Test
    public void context_loads() {
    }


    @Test
    public void healthcheck_shows_buildinfo() {
        // @formatter:off
        given()
        .when()
            .get("/health")
        .then()
            .statusCode(200)
            .body(containsString("git.commit.id"))
        .log()
            .all();
        // @formatter:on
    }

}
