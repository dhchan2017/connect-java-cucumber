package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cd.connect.samples.slackapp.api.MessagesService;
import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.service.ApiService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;


public class MyStepdefs {

//	private static Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

	private ApiService apiService;

	Messagelist messagelist;

	public MyStepdefs(ApiService apiService) {
		this.apiService = apiService;
	}



	@Given("^I call get messages api for user id (.*) from date (.*) to date (.*)$")
	public void iCallGetMessagesApiForUserUser_idFromTo(String userId, BigDecimal fromDate, BigDecimal toDate) throws Throwable {

				messagelist = apiService.messagesApi().gETMessages(userId, fromDate, toDate);
	}

	@Then("^I should get a list of messages$")
	public void iShouldGetAListOfMessages() throws Throwable {
		assertThat(messagelist).isNotEmpty();
	}

	@Given("^a (.*) is sent through slack api$")
	public void aMessageIsSentThroughSlackApi() throws Throwable {




	}
}
