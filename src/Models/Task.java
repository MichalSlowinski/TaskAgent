package Models;

import TaskAgent.TaskAgent;

public class Task {
    private int id;
    private String name;
    private String description;
    private String user;
    private String supervisor;
    private String start;
    private String end;
    private int status;
    private String comment;

    public Task(int id, String name, String desc, String supervisor, String user, int status, String comm, String start, String end) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.supervisor = supervisor;
        this.user = user;
        this.status = status;
        this.comment = comm;
        this.end = end;
        this.start = start;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getStart() {
        return this.start;
    }
    
    public String getEnd() {
        return this.end;
    }

    public String getComment() {
        return this.comment;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public String getStatusname() {
        return TaskAgent.task_state[this.status - 1];
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
    //model obiektu zadania
}
