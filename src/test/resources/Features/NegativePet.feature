Feature: Negative scenarios for pet endpoints

  Scenario: create a pet
    Given user creates new pet providing invalid body
    Then user gets "400" error message


