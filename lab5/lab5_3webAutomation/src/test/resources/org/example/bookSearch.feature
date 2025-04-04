Feature: Book Search
  As a user,
  I want to search for books by title or author,
  So that I can find the books I'm interested in.

  Scenario: Search for a book by title
    Given I am on the library's home page
    When I search for "Harry Potter and the Sorcerer's Stone"
    Then I should see search results containing "Harry Potter and the Sorcerer's Stone"

  Scenario: Search for a book by author
    Given I am on the library's home page
    When I search for "Isaac Asimov"
    Then I should see search results containing books by "Isaac Asimov"

  Scenario: Search with no results
    Given I am on the library's home page
    When I search for "Nonexistent Book"
    Then I should see a message indicating no results found
