package controllers;

import models.account.Role;
import play.mvc.With;

@CRUD.For(Role.class)
@With(Secure.class)
public class Roles extends CRUD{

}
