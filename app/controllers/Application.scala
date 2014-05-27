package controllers

import play.api._
import play.api.Logger
import play.api.libs.json.Reads._
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.util.Random

object Application extends Controller {

  implicit val rds = (
    (__ \ 'login).read[String] and
    (__ \ 'passwd).read[String](minLength[String](5))) tupled //(Reads.minLength(5)

 
  def auth = Action(Ok(Json.toJson(Json.obj(
    "login" -> Random.nextInt("null".size)))))

}