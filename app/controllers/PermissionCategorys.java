package controllers;

import models.account.PermissionCategory;
import play.mvc.With;

@CRUD.For(PermissionCategory.class)
@With(Secure.class)
public class PermissionCategorys extends CRUD{

}
