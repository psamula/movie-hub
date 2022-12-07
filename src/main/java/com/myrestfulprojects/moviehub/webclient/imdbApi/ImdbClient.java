package com.myrestfulprojects.moviehub.webclient.imdbApi;

import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.*;


@Component
public class ImdbClient {
    private static final String IMDB_URL = "https://imdb-api.com/en/API/";
    private static final String API_KEY = "k_dag49j41";
    private RestTemplate restTemplate = new RestTemplate();

    public MovieDto getMovie(String id) {
        return callGetRequest("Title/{apiKey}/{id}", MovieDto.class, API_KEY, id);

    }

    public <T> T callGetRequest (String url, Class<T> responseType, Object... objects) {
        var x = objects;
        var s1 = "k_dag49j41";
        var s2 = "tt0816692";
        return restTemplate.getForObject(IMDB_URL + url, responseType, objects);
    }
}
