@rest
Feature: REST - Podcast Search Feature
    Background:
        Given the user has all the podcasts
        And the user has the podcast id to "Something Was Wrong"

    Scenario: Validate that season 1 had 10 episodes
        When the user gets the podcast
        Then season "1" has "10" eposodes


    Scenario: Validate the season 6 episode 5 title
        When the user gets the podcast
        Then the title for season "6", episode "5" is "Massacre at the Tree of Life Synagogue | JE"

    Scenario: Validate that it has 14 seasons
        When the user gets the podcast
        Then it has "14" seasons