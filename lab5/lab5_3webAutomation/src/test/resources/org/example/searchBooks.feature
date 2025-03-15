Feature: Online Library Book Search

  Scenario: Search for books by title
    Given I am on the library homepage
    When I enter "One good book" in the search bar
    And I click the "Search" button
    Then I should see "One good book" in the search results

  Scenario: Search for books by author
    Given I am on the library homepage
    When I enter "Anonymous" in the search bar
    And I click the "Search" button
    Then I should see "One good book" in the search results

  Scenario: No search results found
    Given I am on the library homepage
    When I enter "Nonexistent Book" in the search bar
    And I click the "Search" button
    Then I should see "No books found"