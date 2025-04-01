package AP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private String JSONRESULT;
    private ArrayList<News> newsList;

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&andfrom" + LocalDate.now().minusDays(1) + "sortBy=publishedAt&apiKey=";
        this.newsList = new ArrayList<>();
        this.JSONRESULT = getInformation();
        parseInformation();
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    private String getInformation() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + APIKEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //condition of HTTP
            System.out.println("HTTP Status Code: " + response.statusCode());

            if (response.statusCode() == 200) {
                //display result
                System.out.println("JSON Response: " + response.body());
                return response.body();
            } else {
                //display Error
                System.out.println("HTTP Error: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }


    public void parseInformation() {
        if (JSONRESULT == null) {
            System.out.println("An Error Occurred While Retrieving The Information.");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(JSONRESULT);
            JSONArray articles = jsonObject.getJSONArray("Articles");

            // Parse first 20 news items
            for (int i = 0; i < Math.min(20, articles.length()); i++) {
                JSONObject article = articles.getJSONObject(i);

                String title = article.optString("Title", "Untitle");
                String description = article.optString("Description", "Without Explanation");
                String sourceName = article.getJSONObject("Source").optString("Name", "Unknown");
                String author = article.optString("Author", "Unknown");
                String url = article.optString("url", " ");
                String publishedAt = article.optString("PublishedAt", " ");

                News news = new News(title, description, sourceName, author, url, publishedAt);
                newsList.add(news);
            }
        } catch (Exception e) {
            System.out.println("An Issue In Processing The News" + e.getMessage());
        }
    }

    public void displayNewsList() {
        if (newsList.isEmpty()) {
            System.out.println("Nothing To Show..");
            return;
        }

        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }
    }
}
