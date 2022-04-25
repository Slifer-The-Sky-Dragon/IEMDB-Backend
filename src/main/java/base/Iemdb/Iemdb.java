package base.Iemdb;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import base.DataCollector;

import base.Exceptions.*;
import base.Filter.Filter;
import base.Filter.SearchFilter;
import base.Filter.SortByDateFilter;
import base.Filter.SortByImdbFilter;
import base.Models.*;
import base.Utilities.Pair;
import base.Utilities.Response;
import base.Utilities.Status;
import base.Utilities.WatchListArgs;

public class Iemdb {
    public static int SHORT_MODE = 0;
    public static int LONG_MODE = 1;

    private static Filter filter;
    private static DAO<Actor> actorDAO;
    private static DAO<Movie> movieDAO;
    private static DAO<User> userDAO;
    private static DAO<Comment> commentDAO;
    private static DAO<Rating> ratingDAO;
    private static DAO<CommentVote> commentVoteDAO;

    private static ExceptionHandler exceptionHandler;

    private static String loggedInInUserEmail = "";

    public static String getLoggedInInUserEmail() {
        return loggedInInUserEmail;
    }

    public static void setLoggedInInUserEmail(String userEmail) {loggedInInUserEmail = userEmail;}

    public static void logoutUser(){ loggedInInUserEmail = ""; }

    public Iemdb() {
        if(filter == null) filter = new SortByImdbFilter();
        if(actorDAO == null) actorDAO = new DAO<>();
        if(movieDAO == null) movieDAO = new DAO<>();
        if(userDAO == null)  userDAO = new DAO<>();
        if(commentDAO == null) commentDAO = new DAO<>();
        if(ratingDAO == null)  ratingDAO = new DAO<>();
        if(commentVoteDAO == null) commentVoteDAO = new DAO<>();
        if(exceptionHandler == null) exceptionHandler = new ExceptionHandler();
        if(!checkIfDataExists())
            new DataCollector(this).run();
    }

    public static void setSearchFilter(String field, String keyword){
        if(field.equals("search")) filter = new SearchFilter(keyword);
        else if(field.equals("clear")) filter = new SearchFilter("");
    }

    public static void setSortFilterField(String field){
        if(field.equals("sort_by_imdb")) filter = new SortByImdbFilter();
        else if(field.equals("sort_by_date")) filter = new SortByDateFilter();
    }

    private Boolean checkIfDataExists(){
        return actorDAO.getCount() != 0 ||
                movieDAO.getCount() != 0 ||
                userDAO.getCount() != 0 ||
                commentDAO.getCount() != 0;
    }

    public void addActor(Actor newActor){ //ph2
        actorDAO.update(newActor);
    }

    public void addMovie(Movie newMovie){ //ph2
        movieDAO.update(newMovie);
    }

    public void checkNewMovieData(Movie newMovie) throws ActorNotFoundException {
        for(int actorId : newMovie.getCast())
            if(!actorDAO.contains(actorId))
                exceptionHandler.throwActorNotFound();
    }

    public void addUser(User newUser){ //ph2
        userDAO.update(newUser);
    }

    public void addComment(Comment newComment){
        newComment.setId(commentDAO.getCount() + 1);
        newComment.setTime(LocalDateTime.now());
        commentDAO.add(newComment);
        movieDAO.findOne(newComment.getMovieId()).addComment(newComment.getId());
    }

    public Response addComment(String userEmail, int movieId, String text){
        Comment newComment = new Comment(userEmail, movieId, text);
        try{
            checkNewCommentData(newComment);
        }catch(MovieNotFoundException | UserNotFoundException nf){
            return new Response(Status.NOT_FOUND, null);
        }
        addComment(newComment);
        return new Response(Status.OK, null);
    }

    public void checkNewCommentData(Comment newComment)
            throws UserNotFoundException, MovieNotFoundException{
        if(!userDAO.contains(newComment.getUserEmail()))
            exceptionHandler.throwUserNotFound();
        if(!movieDAO.contains(newComment.getMovieId()))
            exceptionHandler.throwMovieNotFound();
    }

    public Response rateMovie(String userEmail, int movieId, int rate){
        Rating newRating = new Rating(userEmail, movieId, rate);
        try{
            checkNewRatingData(newRating);
        }catch(MovieNotFoundException | UserNotFoundException nf){
            return new Response(Status.NOT_FOUND, null);
        }catch(InvalidRateScoreException irs){
            return new Response(Status.INVALID_RATE, null);
        }
        ratingDAO.update(newRating);
        return new Response(Status.OK, null);
    }

    public void checkNewRatingData(Rating newRating)
                throws UserNotFoundException, MovieNotFoundException, InvalidRateScoreException{
        if(!userDAO.contains(newRating.getUserEmail()))
            exceptionHandler.throwUserNotFound();
        if(!movieDAO.contains(newRating.getMovieId()))
            exceptionHandler.throwMovieNotFound();
        if(newRating.getScore() > 10 || newRating.getScore() < 1)
            exceptionHandler.throwInvalidRateScore();
    }

    public Response voteComment(String userEmail, int commentId, int vote){
        CommentVote newCommentVote = new CommentVote(userEmail, commentId, vote);
        try{
            checkNewCommentVoteData(newCommentVote);
        }catch(UserNotFoundException | CommentNotFoundException nf){
            return new Response(Status.NOT_FOUND, null);
        }catch(InvalidVoteValueException irs){
            return new Response(Status.INVALID_RATE, null);
        }
        commentVoteDAO.update(newCommentVote);
        return new Response(Status.OK, null);
    }

    public void checkNewCommentVoteData(CommentVote newCommentVote)
            throws UserNotFoundException, CommentNotFoundException, InvalidVoteValueException{
        if(!userDAO.contains(newCommentVote.getUserEmail()))
            exceptionHandler.throwUserNotFound();
        if(!commentDAO.contains(newCommentVote.getCommentId()))
            exceptionHandler.throwCommentNotFound();
        if(newCommentVote.getVote() > 1 || newCommentVote.getVote() < -1)
            exceptionHandler.throwInvalidVoteValue();
    }

    public Response addToWatchList(String userEmail, int movieId){
        User user = userDAO.findOne(userEmail);
        Movie movie = movieDAO.findOne(movieId);
        try{
            checkAddToWatchList(user, movie);
        }catch(UserNotFoundException | MovieNotFoundException nf){
            return new Response(Status.NOT_FOUND, null);
        }catch(AgeLimitErrorException ae){
            return new Response(Status.AGE_LIMIT, null);
        }catch(MovieAlreadyExistsException ma){
            return new Response(Status.OK, null);
        }
        user.addToWatchList(movie.getId());
        return new Response(Status.OK, null);
    }

    public void checkAddToWatchList(User user, Movie movie) throws UserNotFoundException,
                MovieAlreadyExistsException, MovieNotFoundException, AgeLimitErrorException{
        if(user == null)
            exceptionHandler.throwUserNotFound();
        else if(movie == null)
            exceptionHandler.throwMovieNotFound();
        else if(user.getAge() < movie.getAgeLimit())
            exceptionHandler.throwAgeLimitError();
        else if(user.findInWatchList(movie.getId()))
            exceptionHandler.throwMovieAlreadyExists();        
    }

    public void checkNewWatchListArgsData(WatchListArgs newWatchListArgs) throws IemdbException{
        User user = userDAO.findOne(newWatchListArgs.getUserEmail());
        Movie movie = movieDAO.findOne(newWatchListArgs.getMovieId());

        if(user == null)
            exceptionHandler.throwUserNotFound();
        else if(movie == null)
            exceptionHandler.throwMovieNotFound();
        else if(user.getAge() < movie.getAgeLimit())
            exceptionHandler.throwAgeLimitError();
        else if(user.findInWatchList(movie.getId()))
            exceptionHandler.throwMovieAlreadyExists();        
    }

    public Response removeFromWatchList(String userEmail, int movieId){
        try{
            checkOldWatchListArgsData(userEmail, movieId);
        }catch(UserNotFoundException | MovieNotFoundException nf){
            return new Response(Status.NOT_FOUND, null);
        }
        User user = userDAO.findOne(userEmail);
        Movie movie = movieDAO.findOne(movieId);
        user.removeFromWatchList(movie.getId());
        return new Response(Status.OK, null);
    }

    public void checkOldWatchListArgsData(String userEmail, int movieId)
            throws UserNotFoundException, MovieNotFoundException{
        User user = userDAO.findOne(userEmail);
        Movie movie = movieDAO.findOne(movieId);

        if(user == null)
            exceptionHandler.throwUserNotFound();
        else if(movie == null || !user.findInWatchList(movie.getId()))
            exceptionHandler.throwMovieNotFound();
    }

    public ArrayList<Movie> getMoviesList(){
        TreeMap<Integer, Double> movieRatings = calculateAllRatings();
        ArrayList<Movie> moviesInfo = new ArrayList<>();

        for (Movie movie : movieDAO.all()){
            if(movieRatings.containsKey(movie.getId()))
                movie.setRating(movieRatings.get(movie.getId()));
            moviesInfo.add(movie);
        }
        System.out.println("movieDAO.getCount = " + movieDAO.getCount());
        return filter.filter(moviesInfo);
    }

    public ArrayList<Movie> getMoviesListByDate(String startYear, String endYear){
        ArrayList<Movie> allMovies = getMoviesList();
        ArrayList<Movie> moviesInfo = new ArrayList<>();
        for (Movie movie : allMovies){
            String releaseDate = movie.getReleaseDate();
            if(releaseDate.compareTo(startYear) >= 0 && releaseDate.compareTo(endYear) <= 0)
                moviesInfo.add(movie);
        }
        return moviesInfo;
    }

    public ArrayList<Movie> getMoviesListByGenre(String genre){
        ArrayList<Movie> allMovies = getMoviesList();
        ArrayList<Movie> moviesInfo = new ArrayList<>();

        for (Movie movie : allMovies){
            if(movie.getGenres().contains(genre))
                moviesInfo.add(movie);
        }
        return moviesInfo;
    }

    public TreeMap<Integer, Double> calculateAllRatings(){
        TreeMap<Integer, Double> mrSum = new TreeMap<>();
        TreeMap<Integer, Integer> mrCount = new TreeMap<>();
        TreeMap<Integer, Double> mrRating = new TreeMap<>();

        for(Rating rating : ratingDAO.all()) {
            int movieId = rating.getMovieId();
            if (mrSum.containsKey(movieId)) {
                mrSum.put(movieId, mrSum.get(movieId) + rating.getScore());
                mrCount.put(movieId, mrCount.get(movieId) + 1);
            }else{
                mrSum.put(movieId, (double) rating.getScore());
                mrCount.put(movieId, 1);
            }
            mrRating.put(movieId, mrSum.get(movieId) / mrCount.get(movieId));
        }
        return mrRating;
    }

    public Movie getMovieById(int movieId){
        ArrayList<Movie> allMovies = getMoviesList();
        for (Movie movie : allMovies)
            if(movie.getId() == movieId)
                return movie;
        return null;
    }

    public ArrayList<Actor> getActorsList(){
        return actorDAO.all();
    }

    public Actor getActorById(int actorId){
        Actor actor;
        try{
            checkActorId(actorId);
            actor = actorDAO.findOne(actorId);
        }catch(IemdbException ie){
            return null;
        }

        return actor;
    }

    public ArrayList<Movie> getActorMovies(int actorId){
        ArrayList<Movie> movies = getMoviesList();
        ArrayList<Movie> result = new ArrayList<>();
        for(Movie movie : movies)
            if (movie.getCast().contains(actorId))
                result.add(movie);

        return result;
    }

    public void checkActorId(int actorId) throws IemdbException{
        if(actorDAO.findOne(actorId) == null)
            exceptionHandler.throwActorNotFound();
    }

    public void checkMovieId(int movieId) throws IemdbException{
        if(movieDAO.findOne(movieId) == null)
            exceptionHandler.throwMovieNotFound();
    }

    public ArrayList<Object> findCastInfo(Movie movie, int mode){
        ArrayList<Object> movieCastInfo = new ArrayList<>();
        for (Integer actorId : movie.getCast()) {
            if(mode == SHORT_MODE)
                movieCastInfo.add(actorDAO.findOne(actorId).getName());
            else if(mode == LONG_MODE)
                movieCastInfo.add(actorDAO.findOne(actorId));
        }
        return movieCastInfo;
    }

    public ArrayList<Comment> findComments(Movie movie){
        TreeMap<Integer, Pair<Integer, Integer>> commentVotesInfo =
                calculateAllCommentVotes();
        ArrayList<Comment> comments = new ArrayList<>();

        for (Comment comment : commentDAO.all()) {
            Pair<Integer, Integer> commentVotes = commentVotesInfo.get(comment.getId());
            if(comment.getMovieId() == movie.getId()){
                if(commentVotes != null){
                    comment.setLikes(commentVotes.first);
                    comment.setDislikes(commentVotes.second);
                }
                String userNickname =
                        userDAO.findOne(comment.getUserEmail()).getNickname();
                comment.setUserNickname(userNickname);
                comments.add(comment);
            }
        }
        return comments;
    }

    public TreeMap<Integer, Pair<Integer, Integer>> calculateAllCommentVotes(){
        TreeMap<Integer, Integer> commentLikes = new TreeMap<>();
        TreeMap<Integer, Integer> commentDislikes = new TreeMap<>();
        TreeMap<Integer, Pair<Integer, Integer>> commentVotes = new TreeMap<>();

        for(CommentVote commentVote : commentVoteDAO.all()) {
            int commentId = commentVote.getCommentId();
            if (commentLikes.containsKey(commentId)){
                if(commentVote.getVote() == 1)
                    commentLikes.put(commentId, commentLikes.get(commentId) + 1);
                else if(commentVote.getVote() == -1)
                    commentDislikes.put(commentId, commentDislikes.get(commentId) + 1);
            }else{
                if(commentVote.getVote() == 1){
                    commentLikes.put(commentId, 1);
                    commentDislikes.put(commentId, 0);
                }
                else if(commentVote.getVote() == -1){
                    commentLikes.put(commentId, 0);
                    commentDislikes.put(commentId, 1);
                }                
            }
            commentVotes.put(commentId, new Pair<>(
                    commentLikes.get(commentId),
                    commentDislikes.get(commentId)
            ));
        }
        return commentVotes;
    }

    public ArrayList<Movie> getUserWatchList(String userEmail){
        ArrayList<Movie> allMovies = getMoviesList();
        ArrayList<Integer> userWatchList;
        ArrayList<Movie> result = new ArrayList<>();
        User user;
        try{
            checkUserData(userEmail);
            user = userDAO.findOne(userEmail);
            userWatchList = user.getWatchList();
        }catch(IemdbException ie){
            return null;
        }

        for (Movie movie : allMovies)
            if(userWatchList.contains(movie.getId()))
                result.add(movie);
        return result;
    }

    public void checkUserData(String userEmail) throws IemdbException{
        if(!userDAO.contains(userEmail))
            exceptionHandler.throwUserNotFound();
    }

    public User getUser(String userEmail){
        try{
            checkUserData(userEmail);
        }catch(IemdbException ie){
            return null;
        }
        return userDAO.findOne(userEmail);
    }

    public ArrayList<Movie> getRecommendedMovies(String userEmail){
        ArrayList<Movie> userWatchList;
        ArrayList<Movie> result;
        try{
            checkUserData(userEmail);
            userWatchList = getUserWatchList(userEmail);
        }catch(IemdbException ie){
            return null;
        }
        result = calculateMoviesScore(userWatchList);
        return new ArrayList<>(result.subList(0, Math.min(3, result.size())));
    }

    public ArrayList<Movie> calculateMoviesScore(ArrayList<Movie> userWatchList){
        ArrayList< Pair<Movie, Double> > movieScores = new ArrayList<>();
        for(Movie movie: getMoviesList()){
            if(userWatchList.contains(movie))
                continue;
            int genreSimilarity = calculateGenreSimilarity(movie, userWatchList);
            Double score = 3 * genreSimilarity + movie.getImdbRate() + movie.getRating();
            movieScores.add(new Pair<>(movie, score));
        }
        movieScores.sort(Comparator.comparing(Pair::getSecond));
        Collections.reverse(movieScores);

        return movieScores.stream().map(Pair::getFirst)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int calculateGenreSimilarity(Movie movie, ArrayList<Movie> userWatchList){
        int result = 0;
        for(Movie watchListMovie: userWatchList){
            result += movie.getSimilarities(watchListMovie);
        }
        return result;
    }


    public void printComments(){
        commentDAO.print();
    }
}
