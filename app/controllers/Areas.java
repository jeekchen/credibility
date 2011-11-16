package controllers;

import models.system.Area;
import play.mvc.With;

@CRUD.For(Area.class)
@With(Secure.class)
public class Areas extends CRUD{

}
