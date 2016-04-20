/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskagent;

import TaskAgent.Users;
 
import java.util.Observable;


public class WhoIm extends Observable {
    private static WhoIm instance = null;
    private Users userid;

    public static WhoIm getInstance()

    {
        if (instance == null) {
            instance = new WhoIm();
        }
        return instance;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users user) {
        this.userid = user;
    }

    
}