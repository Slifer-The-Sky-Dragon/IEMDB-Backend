package base.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.Period;

public class Actor implements BaseModel{
    private int id = -1;
    private String name = null;
    private String birthDate = null;
    private String nationality = null;
    private String image = null;

    public Boolean isFieldsOk(){
        return id != -1 && name != null && birthDate != null && nationality != null;
    }

    public Object getKey(){
        return this.id;
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id;}

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }   

    public String getBirthDate() { return this.birthDate; }

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; } 

    public String getNationality() { return this.nationality; }

    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
