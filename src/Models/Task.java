package Models;

public class Task {
    
    private int id;
    private String name;
    private String desc;
 
    public Task( int id, String name,String desc) {
        
        this.id = id;
        
    }
            
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void getdesc(String desc){
      this.desc=desc;
    }
    public String desc(){
        return this.desc;
    }
    
          
}