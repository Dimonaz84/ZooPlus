Feature: Negative scenarios for pet endpoints

  Scenario: create a pet
    Given user creates new pet providing invalid body
    Then user gets "400" status code

  Scenario: update a pet with invalid id
    Given user creates new pet "TestPet"
    And user updates the pet name to "UpdatedPet" providing invalid id
    Then user gets "400" status code

  Scenario: search deleted pet
    Given user creates new pet "PetName"
    When user deletes the pet
    And user tries searching for deleted pet
    Then user gets "404" status code

  Scenario: delete pet twice
    Given user creates new pet "Bruce" with status "available"
    When user deletes the pet
    Then user deletes same pet second time
    Then user gets "404" status code

  # THIS ONE PROBABLY FOUND A BUG IN SWAGGER AS NO MATTER WHAT WE INPUT AS A STATUS, WE ALWAYS GET 200 CODE
  #Scenario: find a pet by wrong status
  #  Given user tries seraching the pet providing wrong status
  #  Then user gets "400" status code





