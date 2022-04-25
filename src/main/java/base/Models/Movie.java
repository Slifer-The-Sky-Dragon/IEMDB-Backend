package base.Models;

import java.util.ArrayList;

public class Movie implements BaseModel {
    private int id;
    private String name = null;
    private String summary = null;
    private String releaseDate = null;
    private String director = null;
    private ArrayList<String> writers = null;
    private ArrayList<String> genres = null;
    private ArrayList<Integer> cast = null;
    private double imdbRate = -1;
    private int duration = -1;
    private int ageLimit = -1;
    private final ArrayList<Integer> commentIds = new ArrayList<>();
    private double rating = 0;
    private String image = null;
    private String coverImage = null;

    public Boolean isFieldsOk(){
        if(id == -1 || name == null || summary == null || releaseDate == null || director == null ||
            writers == null || genres == null || cast == null || imdbRate == -1 || duration == -1 || ageLimit == -1)
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<String> getWriters() {
        return writers;
    }

    public void setWriters(ArrayList<String> writers) {
        this.writers = writers;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Integer> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Integer> cast) {
        this.cast = cast;
    }

    public double getImdbRate() {
        return imdbRate;
    }

    public void setImdbRate(double imdbRate) {
        this.imdbRate = imdbRate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Object getKey() {
        return id;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public double getRating(){
        return rating;
    }

    public void addComment(int commentId){
        commentIds.add(commentId);
    }

    public void printComments(){
        System.out.println(commentIds);
    }

    public int getSimilarities(Movie otherMovie){
        int result = 0;
        for(String genre: otherMovie.getGenres()){
            if(genres.contains(genre))
                result += 1;
        }
        return result;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
