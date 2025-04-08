Feature: Meal Reservation

  @booking
  Scenario: Select a restaurant, a day, insert email,book a meal and check confirmation
    Given I navigate to "http://localhost"
    When In restaurant Castro I select the week 13 on day 2
    And I fill in with email "bruno@gmail.com"
    And I click on Book meal button
    Then I should see the message "Booking submitted successfully!"

  @booking2
  Scenario: Cancel an existing booking
    Given I navigate to "http://localhost"
    When I fill email "bruno@gmail.com"
    And I click on my last booking
    And I click the cancel button
    Then I should see the message "Booking cancelled successfully"
