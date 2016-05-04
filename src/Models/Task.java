package Models;

public class Task {
    private int id;
    private String name;
    private String description;
    private String user;
    private String supervisor;
    private int status;
    private String comment;

    public Task(int id, String name, String desc, String supervisor, String user, int status, String comm) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.supervisor = supervisor;
        this.user = user;
        this.status = status;
        this.comment = comm;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getStart() {
        return "01.01.2016";
    }
    
    public String getEnd() {
        return "01.01.2017";
    }

    public String getComment() {
        return this.comment;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public String getSupervisor() {
        return this.supervisor;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String desc) {
        this.description = desc;
    }
}
