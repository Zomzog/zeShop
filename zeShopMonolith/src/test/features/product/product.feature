Feature: Product management

    Scenario: Retrieve product by id
        When I search product by id '1'
        Then the request is successful
        And his title is 'product1'

    Scenario: Retrive all products
        When I search all products
        Then the request is successful