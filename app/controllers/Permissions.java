package controllers;

import models.account.Permission;
import play.mvc.With;

@CRUD.For(Permission.class)
@With(Secure.class)
public class Permissions extends CRUD{

}
