/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskagent;

import TaskAgent.User;



public class WhoIm {
    private static WhoIm instance = null;
    private User user;

    public static WhoIm getInstance()

    {
        if (instance == null) {
            instance = new WhoIm();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}