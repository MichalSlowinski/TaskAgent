package TaskAgent;

public class Users {
    public String login;
    public int user_id;
    public String password;
    public String name;
    public String surname;
    public int group_id;
    public int status;
 
    public Users( int user_id,String login, String name, String pswd, String surname, int id_groups) {
        this.login = login;
        this.user_id = user_id;
        this.surname = surname;
        this.name = name;
        this.password = pswd;
        this.group_id = id_groups;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSurname() {
        return this.surname;
    }
    
    public int getGroup() {
        return this.group_id;
    }
        
    public String getPassword() {
        return this.password;
    }
 
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setId(int id) {
        this.user_id = id;
    }
    
    public int getId() {
        return this.user_id;
    }
}