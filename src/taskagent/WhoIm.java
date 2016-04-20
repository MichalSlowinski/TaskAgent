/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskagent;

import TaskAgent.User;
 
import java.util.Observable;


public class WhoIm extends Observable {
    private static WhoIm instance = null;
    private User user;

    public static WhoIm getInstance()

    {
        if (instance == null) {
            instance = new WhoIm();
        }
        return instance;
    }

    public User getUserid() {
        return user;
    }

    public void setUserid(User user) {
        this.user = user;
    }

    
}