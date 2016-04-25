package Models;

public class Task {
    private int id;
    private String name;
    private String description;
    private String user;
    private String supervisor;

    public Task(int id, String name, String desc, String supervisor, String user) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.supervisor = supervisor;
        this.user = user;
    }
    
    public int getId() {
        return this.id;
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
