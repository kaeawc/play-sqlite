package controllers

import models.Widget
import play.api.mvc._

object Application extends Controller {

  def index = Action.async {

    Widget.countAll() map {
      case Some(count:Long) => Ok(views.html.index(count))
      case _ => Ok(views.html.index(0))
    }
  }

}