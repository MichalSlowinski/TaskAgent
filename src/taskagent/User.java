package TaskAgent;

public class User {
    
    public int user_id;
    
 
    public User( int user_id) {
        
        this.user_id = user_id;
        
    }
            
    public void setUserId(int id) {
        this.user_id = id;
    }
    
    public int getUserId() {
        return this.user_id;
    }
}