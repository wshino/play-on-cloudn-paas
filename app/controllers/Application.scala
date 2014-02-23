package controllers

import play.api._
import play.api.mvc._
import models.{Users, User}

object Application extends Controller {



  def index = Action {
    val u = User.apply(0, "play")
    Users.insert(u)
    val res = Users.findAll
    Ok(res.size.toString)
  }


}
