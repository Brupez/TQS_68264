Feature: Meal Reservation

  Background:
    Given I navigate to "http://localhost"

  @Createbooking
  Scenario: Select a restaurant, a day, insert email,book a meal and check confirmation
    When In restaurant Castro I select the week 13 on day 2
    And I fill in with email "bruno@gmail.com"
    And I click on Book meal button
    Then I should see the message "Booking submitted successfully!"

  @CancelBooking
  Scenario: Cancel an existing booking
    When I fill email "bruno@gmail.com"
    And I click on my last booking
    And I click the cancel button
    Then I should see the cancel message "Booking cancelled successfully"
