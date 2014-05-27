package controllers.util

import jp.t2v.lab.play2.auth.AuthConfig
import play.api._
import play.api.mvc._

/**
 * CloudAuthConfigImpl.scala based on the idea of https://github.com/t2v/play2-auth
 *
 * author marcus on 27.05.2014
 *
 */

trait CloudAuthConfigImpl extends AuthConfig {

  type Id = String

  type User = Map[String, String]

  val sessionTimeoutInSeconds: Int = 3600
  
  	
 // def resolveUserId(id : Id) (implicit ctx:ExecutionContext) : Future[Result] = Future.successful(Redirect(routes.CloudSaveTask.main))

  def authenticate = ???
  
  
}