Feature: Testing listener service
         AS a listener service that can monitor a given slack channel
         I WANT to be able to extract events from that channel
         SO THAT messages from those events can be analysed


  Scenario Outline: Get messages sent through slack channel
	Given a <message> is sent through slack api
    When listener api is called
	Then the response should contain <message> that is sent through slack api

    Examples:
    |message           |
    |Hello Connect     |
