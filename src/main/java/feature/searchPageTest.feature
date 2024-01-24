Feature: Wikimedia Search Endpoint
  Description: The purpose of these tests to cover the functionality in search endpoint


  Scenario Outline: Verify the 'Sesame Street' is found in the search result
    Given a client of the API
    When a search for pages containing for <searchKeyword> is executed
    Then a page with the title <pageTitle> is found

    Examples:
      |searchKeyword|pageTitle|
      |furry rabbits|Sesame Street|


  Scenario Outline: Verify the latest Time Stamp for the given page
    #Given the result for ‘furry rabbits’ search contains ‘Sesame Street’
    When the page details for <pageTitle> are requested
    Then it has a latest timestamp > <timeStamp>

    Examples:
      |pageTitle|timeStamp|
      |Sesame_Street|2023-08-17T00:00:00|