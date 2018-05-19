package medsolution.medsolutionmed.model;

import java.io.Serializable;

public class Pacient1 implements Serializable {

    public Pacient1() {
    }

    private String name;
    private String bed;
    private String diagnost;
    private String id;
    private String gender;
    private String date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getDiagnost() {
        return diagnost;
    }

    public void setDiagnost(String diagnost) {
        this.diagnost = diagnost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
