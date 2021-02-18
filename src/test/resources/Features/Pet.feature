Feature: Pets

  Scenario: create a pet
    Given user creates new pet "Lemmy"
    And user uploads a picture for the pet
    Then user verifies if pet with the given "Lemmy" is available

  Scenario Outline: update a pet
    Given user creates new pet <name>
    And user updates the pet name to <new_name>
    Then user verifies if pet with the given <new_name> is available

    Examples:
      |name   | new_name |
      |"Lemmy"|  "Ozzy"  |

  Scenario: find a pet by status
    Given user creates new pet "Brian" with status "available"
    And user creates new pet "Mal" with status "pending"
    And user creates new pet "Angus" with status "sold"
    Then pet "Brian" is available with status "available"
    And pet "Mal" is available with status "pending"
    And pet "Angus" is available with status "sold"

  Scenario: update the pet with form data
    Given user creates new pet "Bruce" with status "available"
    When user updates the pet with form data and sets name "Nikko" and status "sold"
    Then pet "Nikko" is available with status "sold"

    Scenario: delete the pet
      Given user creates new pet "Bruce" with status "available"
      When user deletes the pet
      Then pet is not available

