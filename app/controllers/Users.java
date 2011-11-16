package controllers;

import models.account.User;
import play.mvc.With;

@CRUD.For(User.class)
@With(Secure.class)
public class Users extends CRUD{

}
