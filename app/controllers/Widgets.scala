package controllers

import models.Widget

import play.api.Logger
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future

object Widgets extends Controller {

  def get(id:Int) = Action.async {

    Widget.getById(id) map {
      case Some(widget:Widget) => Ok(Json.toJson(widget))
      case _ => NoContent
    }
  }

  def find(name:String) = Action.async {

    Widget.findByName(name) map {
      case widgets:List[Widget] => Ok(Json.toJson(widgets))
      case _ => NoContent
    }
  }

  def post = Action.async {

    implicit request =>

    val form = Form(
      "name" -> text
    )

    form.bindFromRequest match {
      case form:Form[String] if !form.hasErrors => {

        val name = form.get

        Widget.create(name) map {
          case Some(id:Long) => Created(Json.obj("created" -> id))
          case _ => InternalServerError(Json.obj("created" -> false))
        }
      }
      case _ => Future { BadRequest(Json.obj("reason" -> "Could not bind POST data to form.")) }
    }
  }

  def put(id:Int) = Action.async {

    implicit request =>

    val form = Form(
      "name" -> text
    )

    form.bindFromRequest match {
      case form:Form[String] if !form.hasErrors => {

        val name = form.get

        Widget.update(id,name) map {
          case rows:Int if rows > 0 => Accepted(Json.obj("updated" -> true))
          case _ => InternalServerError(Json.obj("updated" -> false))
        }
      }
      case _ => Future { BadRequest(Json.obj("reason" -> "Could not bind POST data to form.")) }
    }
  }

  def delete(id:Int) = Action.async {

    Widget.delete(id) map {
      case rows:Int if rows > 0 => Accepted(Json.obj("deleted" -> true))
      case _ => InternalServerError(Json.obj("deleted" -> false))
    }
  }

}