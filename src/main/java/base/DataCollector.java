package base;

import base.Iemdb.Iemdb;

import base.Models.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataCollector {
    private final String BASE_PATH = "http://138.197.181.131:5000/api";
    Iemdb iemdb;

    public DataCollector(Iemdb iemdb){
        this.iemdb = iemdb;
    }

    public void getActors(){
        String response = sendGetRequest(BASE_PATH + "/v2/actors");
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            Actor[] actors = objectMapper.readValue(response, Actor[].class);
            for (Actor actor : actors) {
                iemdb.addActor(actor);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getMovies(){
        String response = sendGetRequest(BASE_PATH + "/v2/movies");
        //System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            System.out.println("Movies");
            Movie[] movies = objectMapper.readValue(response, Movie[].class);
            for (Movie movie : movies) {
                iemdb.addMovie(movie);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getComments(){
        String response = sendGetRequest(BASE_PATH + "/comments");
        //System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Comment[] comments = objectMapper.readValue(response, Comment[].class);
            for (Comment comment : comments) {
                iemdb.addComment(comment);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getUsers(){
        String response = sendGetRequest(BASE_PATH + "/users");
        //System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            User[] users = objectMapper.readValue(response, User[].class);
            for (User user : users) {
                iemdb.addUser(user);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String sendGetRequest(String path){
        try{
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
            return "Bad Response...";
        }catch(Exception e){
            return "Bad connection...";
        }
    }

    public void run(){
        getActors();
        getMovies();
        getComments();
        getUsers();
    }
}
