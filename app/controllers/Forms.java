package controllers;

import models.form.Form;
import play.mvc.With;

@CRUD.For(Form.class)
@With(Secure.class)
public class Forms extends CRUD{

}
