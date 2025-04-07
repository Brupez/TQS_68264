Feature: Meal Reservation

  Scenario: Select a restaurant, a day, insert email and book a meal
    Given I navigate to "http://localhost"
    When In restaurant Castro I select the week 13 on day 2
    And I fill in with email "bruno@gmail.com"
    And I click on Book meal button
    Then I should see the message "Booking submitted successfully!"