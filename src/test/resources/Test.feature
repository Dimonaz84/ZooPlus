Feature: Pets

  Scenario Outline: pet endpoint
    Given user creates new pet <name> with pet endpoint

    Examples:
    |name     |
    |"TestDog"|