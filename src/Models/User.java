package Models;

public class User {
    private int user_id;
    private String firstname;
    private String lastname;
    private int id_group;
 
    public User(int user_id, String first, String last, int group) {
        this.user_id = user_id;
        this.lastname = last;
        this.firstname = first;
        this.id_group = group;
    }
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public String getGroupname() {
        String[] groups = {"", "Użytkownik", "Kierownik", "Administrator"};
        return groups[this.id_group];
    }
            
    public void setId(int id) {
        this.user_id = id;
    }
    
    public int getId() {
        return this.user_id;
    }
}