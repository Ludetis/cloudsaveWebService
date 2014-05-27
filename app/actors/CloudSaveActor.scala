package actors

import akka.actor._
import play.api.libs.json._
import play.api.mvc.WebSocket.FrameFormatter
import akka.event.Logging

//import play.Logger

/**
 * CloudSaveActor
/**
 * CloudAuthConfigImpl.scala based on the idea of https://github.com/t2v/play2-auth
 *
 * author marcus on 27.05.2014
 *
 */
 */
object CloudSaveActor {

  def props(out: ActorRef) = Props(new CloudSaveActor(out))

  /**
   * @TODO explain
   */
  // case class CloudSave(transferObject: Map[String, Any])

  case class CloudSave(values: Seq[String])

  object CloudSave {
    implicit val cloudSaveFormat = Json.format[CloudSave]
    implicit val cloudSaveFrameFormatter = FrameFormatter.jsonFrame[CloudSave]

  }

  /**
   * @TODO explain
   */
  case class CloudSaveTransfer(transferObject: Seq[String])

  object CloudSaveTransfer {

    /**
     * @TODO explain this implicit
     */
    implicit val transferObjectFormat = Json.format[CloudSaveTransfer]

    /**
     * @TODO explain this implicit
     */
    implicit val transferObjectFrameFormatter = FrameFormatter.jsonFrame[CloudSaveTransfer]

  }

  case class CloudSaveTransferMap(cloudMap: Map[String, String])

  object CloudSaveTransferMap {
    /**
     * @TODO explain this implicit
     */
    implicit val transferObjectFormat = Json.format[CloudSaveTransferMap]

    /**
     * @TODO explain this implicit
     */
    implicit val transferObjectFrameFormatter = FrameFormatter.jsonFrame[CloudSaveTransferMap]

  }

}

/**
 * @TODO explain this actor
 */
class CloudSaveActor(out: ActorRef) extends Actor {

  import CloudSaveActor._
  
  def receive = {

    case CloudSave(persistTransferObject) => out ! CloudSaveTransfer(persistTransferObject)

  }

}