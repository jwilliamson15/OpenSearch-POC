Feature: User searches by Room Type

  Scenario Outline: User searches by room type
    Given a user has searched for hotels with multiple responses
    When a user filter for room type "<roomTypeId>"
    Then the user gets the previous list of results filtered by room type
    Examples:
      | roomTypeId      |
      | DOUBLE_STANDARD |
      | JUNIOR_SUITE    |

  Scenario Outline: User searches by multiple room types
    Given a user has searched for hotels with multiple responses
    When a user filter for room types "<roomTypeId1>" and "<roomTypeId2>"
    Then the user gets the previous list of results filtered by multiple room types
    Examples:
      | roomTypeId1     | roomTypeId2  |
      | DOUBLE_STANDARD | JUNIOR_SUITE |