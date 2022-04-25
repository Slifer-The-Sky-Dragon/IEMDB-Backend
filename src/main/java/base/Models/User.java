package base.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class User implements BaseModel{
    private String email = null;
    private String password = null;
    private String nickname = null;
    private String name = null;
    private String birthDate = null;
    private final ArrayList<Integer> watchList = new ArrayList<>();

    public Boolean isFieldsOk(){
        if(email == null || password == null || nickname == null || name == null || birthDate == null)
            return false;
        return true;
    }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }   

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }      

    public String getNickname() { return this.nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }   

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }   

    public String getBirthDate() { return this.birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    @JsonIgnore
    public Object getKey(){
        return this.email;
    }

    @JsonIgnore
    public int getAge(){
        return Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears();
    }

    public void addToWatchList(int movieId){
        watchList.add(movieId);
    }

    public void removeFromWatchList(int movieId){
        watchList.remove(Integer.valueOf(movieId));
    }

    public Boolean findInWatchList(int movieId){
        return watchList.contains(movieId);
    }

    @JsonIgnore
    public ArrayList<Integer> getWatchList(){
        return watchList;
    }
}
