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
        Query("update tasks set status='"+Status+"' where id="+id+"");
    }
}
