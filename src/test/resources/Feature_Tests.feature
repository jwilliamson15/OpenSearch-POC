Feature: Users search by hotel names

  Scenario Outline: Users search by an existing hotel name
    Given a user is active
    When a user searches for an existing hotel with name "<hotelName>"
    Then a list of matching hotels is returned
    Examples:
      |hotelName|
      |Bergstrom|
      |Johnston Group|
      |Schroeder|

  Scenario Outline: Users search by a non-existing hotel name
    Given a user is active
    When a user searches for an existing hotel with name "<hotelName>"
    Then an empty list of hotels is returned
    Examples:
      |hotelName|
      |Null|
      |Not Exists Hotel|
      |Unavailable|