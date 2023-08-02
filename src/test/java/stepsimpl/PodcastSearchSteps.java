package stepsimpl;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.Services;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PodcastSearchSteps {
    private String baseURL = "https://podcast-api.netlify.app/";
    private Services services;
    private Response response;
    private int podcastId;

    @Before
    public void setUp(){
        this.services = new Services(baseURL);
    }

    @Given("the user has all the podcasts")
    public void theUserHasAllThePodcats(){
        services.setEndpoint("shows");
        services.setHTTPMethod("GET");
        services.build();
        response = services.send();
    }

    @Given("the user has the podcast id to {string}")
    public void theUserHasThePodcastIdTo(String podcastName) {
        JsonPath jsonPath = new JsonPath(response.body().asString());
        podcastId = jsonPath.getInt("find { podcast -> podcast.title == '" + podcastName +"' }.id");
    }

    @When("the user gets the podcast")
    public void theUserGetsThePodcast() {
        services.setEndpoint("id/" + podcastId);
        services.setHTTPMethod("GET");
        services.build();
        response = services.send();
    }

    @Then("season {string} has {string} eposodes")
    public void seasonHasEposodes(String season, String episodes) {
        int season_ = Integer.parseInt(season) - 1;
        List<JsonObject> episodesList = response.body().jsonPath().getList("$.seasons[" + season_ +"].episodes", JsonObject.class);
        assertEquals(episodesList.size(), Integer.parseInt(episodes));
    }

    @Then("the title for season {string}, episode {string} is {string}")
    public void theTitleForSeasonEpisodeIs(String season, String eposode, String title) {
        int season_ = Integer.parseInt(season) - 1;
        int episode_ = Integer.parseInt(eposode) - 1;
        String actual = response.body().jsonPath().getString("$.seasons[" + season_ +"].episodes[" + episode_ +"]");
        assertEquals(title, actual);
    }

    @Then("it has {string} seasons")
    public void itHasSeasons(String seasons) {
        List<JsonObject> seasonsList = response.body().jsonPath().getList("$.seasons", JsonObject.class);
        assertEquals(Integer.parseInt(seasons), seasonsList.size());
    }
}
