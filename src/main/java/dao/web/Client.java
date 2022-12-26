package dao.web;

import com.google.gson.Gson;
import exceptions.ClientFailedException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    private final Gson gson;

    public Client() {
        this.gson = new Gson();
    }

    public <T> T get(String uri, Class<T> classType) {
        HttpRequest request = requestGet(uri);
        HttpResponse<String> response = response(request);
        return gson.fromJson(response.body(), classType);
    }

    private HttpRequest requestGet(String uri) {
        try {
          return HttpRequest.newBuilder()
                  .uri(new URI(uri))
                  .GET()
                  .build();
        } catch (URISyntaxException e) {
            throw new ClientFailedException(e);
        }
    }

    private HttpResponse<String> response(HttpRequest request) {
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ClientFailedException(e);
        } catch (InterruptedException e) {
            throw new ClientFailedException(e);
        }
    }
}
