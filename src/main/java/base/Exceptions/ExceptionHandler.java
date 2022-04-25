package base.Exceptions;

public class ExceptionHandler {
    public void throwActorNotFound() throws ActorNotFoundException {
        String ACTOR_NOT_FOUND = "ActorNotFound";
        throw new ActorNotFoundException(ACTOR_NOT_FOUND);
    }
    public void throwMovieNotFound() throws MovieNotFoundException {
        String MOVIE_NOT_FOUND = "MovieNotFound";
        throw new MovieNotFoundException(MOVIE_NOT_FOUND);
    }
    public void throwUserNotFound() throws UserNotFoundException {
        String USER_NOT_FOUND = "UserNotFound";
        throw new UserNotFoundException(USER_NOT_FOUND);
    }
    public void throwInvalidRateScore() throws InvalidRateScoreException {
        String INVALID_RATE_SCORE = "InvalidRateScore";
        throw new InvalidRateScoreException(INVALID_RATE_SCORE);
    }
    public void throwInvalidVoteValue() throws InvalidVoteValueException {
        String INVALID_VOTE_VALUE = "InvalidVoteValue";
        throw new InvalidVoteValueException(INVALID_VOTE_VALUE);
    }
    public void throwCommentNotFound() throws CommentNotFoundException {
        String COMMENT_NOT_FOUND = "CommentNotFound";
        throw new CommentNotFoundException(COMMENT_NOT_FOUND);
    }
    public void throwAgeLimitError() throws AgeLimitErrorException {
        String AGE_LIMIT_ERROR = "AgeLimitError";
        throw new AgeLimitErrorException(AGE_LIMIT_ERROR);
    }
    public void throwMovieAlreadyExists() throws MovieAlreadyExistsException {
        String MOVIE_ALREADY_EXISTS = "MovieAlreadyExists";
        throw new MovieAlreadyExistsException(MOVIE_ALREADY_EXISTS);
    }   
}
