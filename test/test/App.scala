package test

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.Logger
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext}
import ExecutionContext.Implicits.global

import org.specs2.execute.{Result, AsResult}

abstract class App(app:FakeApplication = App.app) extends WithApplication(app) {
  override def around[T: AsResult](t: => T): Result = super.around {
    wipeData()
    val result = t
    result
  }

  def wipeData() {
    val wiped = sync { models.Widget.wipeTable() }

    val countStart = sync { models.Widget.countAll() }

    countStart match {
      case Some(count:Long) if count > 0 => throw new Exception("Didn't wipe widget table properly")
      case _ => {}
    }
  }
}

object App {

  def app = FakeApplication(additionalConfiguration =
    Map(
      "db.default.driver" -> "org.sqlite.JDBC",
      "db.default.url" -> "jdbc:sqlite:db/test.db?mode=memory&cache=shared",
      "evolutionplugin" -> "enabled",
      "applyEvolutions.default" -> "true",
      "applyDownEvolutions.default" -> "true"
    )
  )

}
