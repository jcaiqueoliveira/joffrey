package medsolution.medsolutionmed.model;

public class SchedulePacient {
    private String time;
    private String ocurrenceType;
    private String action;
    private String idSchedule;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOcurrenceType() {
        return ocurrenceType;
    }

    public void setOcurrenceType(String ocurrenceType) {
        this.ocurrenceType = ocurrenceType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }
}
