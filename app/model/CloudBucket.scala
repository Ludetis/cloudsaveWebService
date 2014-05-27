package model

import org.joda.time.DateTime
import reactivemongo.bson._

/**
 *
 */

case class CloudBucket(
  id: Option[BSONObjectID],
  user: String,
  description: String,
  dueDate: Option[DateTime])

/*
   *case class for a generic json object retrieved by the http request
   */
case class CloudGenericBucket(
  id: Option[BSONObjectID],
  key1: String,
  key2: Either[String, Map[String, String]])

/**
 * Persistence object for various db types
 *
 */

/**
 * the bucket object
 */

object CloudBucket {
  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import play.modules.reactivemongo.json.BSONFormats._

  def findBucketById(id: Long) = {

  }

  def saveBucketId(id: Long, cloudObjectAsJson: JsObject) = {

  }

  def deleteBucketId(id: Long) = {

  }

  //  implicit val cloudJsonReads: Reads[CloudGenericBucket] =
  //    {
  //      val key2Reads: Reads[Either[String, Map[String, String]]] =
  //            (__ \ "id").readNullable[BSONObjectID].map(_.getOrElse(BSONObjectID.generate)).map(Some(_)) and
  //        (__ \ "key2").read[String].map(Left(_)) or
  //          (__ \ "key2").read[Map[String, String]].map(Right(_))
  //      ((__ \ "key1").read[String] and key2Reads)(CloudGenericBucket(_, _))
  //    }
  //
  //  implicit val jsonWrites: Writes[CloudBucket] = (
  //          (__ \ "id").writeNullable[BSONObjectID] and
  //(__ \ "key1").write[String] or 
  //(__ \ "key2"..write[String] and)

  implicit val jsonReads: Reads[CloudBucket] = (
    (__ \ "id").readNullable[BSONObjectID].map(_.getOrElse(BSONObjectID.generate)).map(Some(_)) and
    (__ \ "user").read[String] and
    (__ \ "description").read[String] and
    (__ \ "dueDate").readNullable[DateTime])(CloudBucket.apply _)

  implicit val jsonWrites: Writes[CloudBucket] = (
    (__ \ "id").writeNullable[BSONObjectID] and
    (__ \ "user").write[String] and
    (__ \ "description").write[String] and
    (__ \ "dueDate").writeNullable[DateTime])(unlift(CloudBucket.unapply))

}