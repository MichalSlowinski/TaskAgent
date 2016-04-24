/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import static TaskAgent.DBConnection.Query;

/**
 *
 * @author slowi
 */
public class Querys {
    
    public static void addTask(String name, String desc, int SupervisorId){
        Query("insert into tasks(name,task,id_supervisor)values('"+name+"','"+desc+"',"+SupervisorId+");");
    }
    public static void FinishTask(String Status, int id){
        Query("update tasks set status='"+Status+"' where id="+id+";");
    }
    public static void AddUser(String firstname,String lastname,String login, String password, String email, int id_groups, int id_supervisor){
        Query("Insert into users(firstname,lastname,login,password,email,id_groups,id_supervisor) values('"+firstname+"','"+lastname+"','"+login+"','"+password+"','"+email+"',"+id_groups+","+id_supervisor+");");
}
}