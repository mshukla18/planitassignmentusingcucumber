Feature: Jupiter

  Scenario: Validate error messages on contact page
    Given I navigate to page "contact" from home page
    When I click on submit button
    Then I validate all the error messages
    And in the field "forename" enter "John" and validate that error is gone
    And in the field "email" enter "John@example.com" and validate that error is gone
    And in the field "message" enter "this is feedback" and validate that error is gone

  Scenario Outline: Validate success message when all mandatory values are filled
    Given I navigate to page "contact" from home page
    And in the field "<forename>", "<email>" and "<message>" enter respective values
    When I click on submit button
    Then validate success message
    Examples:
      |forename   | email            | message          |
      | John      | John@example.com | this is feedback |
      | Andy      | Andy@example.com | this is feedback |
      | Will      | Will@example.com | this is feedback |
      | Boby      | Boby@example.com | this is feedback |
      | Keli      | Keli@example.com | this is feedback |

  Scenario: Validate cart values when multiple shopping items are added
    Given I navigate to page "shopping" from home page
    And add 2 "Stuffed Frog" to the cart
    And add 5 "Fluffy Bunny" to the cart
    And add 3 "Valentine Bear" to the cart
    When navigate to "cart" page from shopping page
    Then validate the price, quantity and subtotal of each item on the cart page