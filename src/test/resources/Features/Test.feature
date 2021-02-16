Feature: Pets

  Scenario Outline: pet endpoint
    Given user creates new pet <name> with pet endpoint
    When user uploads a picture for the pet

    Examples:
    |name   |
    |"Lemmy"|