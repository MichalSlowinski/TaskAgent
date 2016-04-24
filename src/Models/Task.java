package Models;

public class Task {
    private int id;
    private String name;
    private String description;

    public Task(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.description = desc;
    }
    
    public int getId() {
        return this.id;
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
