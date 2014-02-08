package controllers

import test._

import play.api.Logger
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

import play.api.libs.json._
import play.api.libs.json.Json._

import scala.concurrent.{Await,ExecutionContext}

class WidgetsSpec extends Specification {

  def create(names:String *):List[Int] = {

    val header = FakeRequest(POST, "/widget")

    names.foldLeft(List[Int]()) {
      (list,name) =>
      val body = Json.obj(
        "name" -> name
      )

      val response = route(header,body).get

      status(response) mustEqual 201

      val json = Json.parse(contentAsString(response))

      json \ "created" match {
        case JsNumber(id) => list :+ id.toInt
        case JsBoolean(false) => {
          failure("Could not create this Widget")
          Nil
        }
        case _ => {
          failure("Unknown failure")
          Nil
        }
      }
    }
  }

  "GET /widget/:id" should {

    "get a widget if it exists" in new App {

      val id = create("asdf","asdf","fdas","dsda","asdf").head

      val request = FakeRequest(GET, "/widget/" + id)

      val response = route(request).get

      status(response) mustEqual OK
    }

    "return no content because there are no widgets with this id" in new App {

      val request = FakeRequest(GET, "/widget/1")

      val response = route(request).get

      status(response) mustEqual 204
    }
  }

  "GET /widget/:name" should {

    "find any widgets that match this name exactly" in new App {

      val id = create("asdf","asdf","fdas","dsda","asdf").head

      val request = FakeRequest(GET, "/widget/find/asdf")

      val response = route(request).get

      status(response) mustEqual OK
    }
  }

  "POST /widget" should {

    "create a new widget" in new App {

      create("asdf")
    }
  }

  "PUT /widget/:id" should {

    "update an existing widget" in new App {

      create("asdf")

      val header = FakeRequest(PUT, "/widget/1")

      val body = Json.obj(
        "name" -> "asdf"
      )

      val response = route(header,body).get

      status(response) mustEqual 202
    }
  }

  "DELETE /widget/:id" should {

    "delete an existing widget" in new App {

      create("asdf")

      val request = FakeRequest(DELETE, "/widget/1")

      val response = route(request).get

      status(response) mustEqual 202
    }
  }
}