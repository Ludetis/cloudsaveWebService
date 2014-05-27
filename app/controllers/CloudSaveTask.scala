package controllers

import play.api._
import play.api.Logger
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo.MongoController

import reactivemongo.api._
import play.modules.reactivemongo.json.collection.JSONCollection

import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

import model._

case class CloudGenericBucket(key1: String, key2: Either[String, Map[String, String]])

/**
 * Transfer Task ,
 * retrieving a JsonObject from the client mapping to a Map[String,Any]
 * for final persisting to subsequent layer
 */

object CloudSaveTask extends Controller with MongoController{

  /**
   * generic json object
   */
  implicit val cloudJsonReads: Reads[CloudGenericBucket] =
    {
      val key2Reads: Reads[Either[String, Map[String, String]]] =
        (__ \ "key2").read[String].map(Left(_)) or
          (__ \ "key2").read[Map[String, String]].map(Right(_))
      ((__ \ "key1").read[String] and key2Reads)(CloudGenericBucket(_, _))
    }

  implicit val rdsString = (__ \ 'id).read[String]

  implicit val rds = (
    (__ \ 'id).read[String] and
    (__ \ 'value).read[String]) tupled

  implicit val rdsLong = (
    (__ \ 'name).read[String] and
    (__ \ 'age).read[Long])tupled

  implicit val readInt = { (__ \ 'id).read[Long] }

  /**
   * controller method to map a
   * json object retrieved from the client
   * @TODO mapping it to a generic Map Object for subsequent processing
   * this method persists a map instead of persisting a json object directly
   */
  def postMapBucket = Action(parse.json) {
    request =>
      request.body.validate[(String, String)].map {
        case (name, age) =>
          Logger.info("Request" + request)
          Ok("Hello " + name + ", you're " + age)
        //activate an actor for persisting it without blocking 
      }.recoverTotal {
        e => BadRequest("Detected error:" + JsError.toFlatJson(e))
      }
  }

  /**
   * method in case the json object needs to be persisted directly into a
   * nosql based db.
   *
   */


  def collection = db.collection[JSONCollection]("cloudbucket")

  def postJsonBucket = Action.async(parse.json) {
    request =>
      Logger.info("Request in postJsonBucket " + request.body)
      request.body.validate[CloudBucket].map {
        case cloudBucket => {
          val futureResult = collection.insert(cloudBucket)
          futureResult.map {
            case result => result.inError match {
              case true => InternalServerError("%s".format(result))
              case false => Ok(Json.toJson(cloudBucket))
            }
          }
        }
      }.recoverTotal {
        e => Future { BadRequest(JsError.toFlatJson(e)) }
      }
  }

  def getCloudBucketbyId(id: Option[Long]) = Action(parse.anyContent) { request =>
    {
      Logger.info("Id " + id)
      Ok("Id" + id)
    }
  }

  /**
   * get cloud Object by id parsed from the json object from the cloud
   */
  def getCloudObject(id: Option[String]) = Action { request =>

    Logger.info("request body to String $id")
    Ok(s"something  $id")

  }

  /**
   * delete a bucket by give id
   */
  def deleteCloudObject = Action {
    Ok("Delete Object")
  }
}