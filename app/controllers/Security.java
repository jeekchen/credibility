package controllers;

import models.account.User;

public class Security extends Secure.Security{
	static boolean authenticate(String username, String password) {
		User user = User.connect(username, password);
        return user != null && user.password.equals(password);
    }
	
	
}
