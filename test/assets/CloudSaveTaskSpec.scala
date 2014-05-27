package assets


import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable.Specification
import org.specs2.execute.AsResult
import org.specs2.mutable._
import org.specs2.runner._

import play.api.libs.json._
import play.api.libs.iteratee.Enumerator
import scala.concurrent.Future
import play.api.libs.functional.syntax._



class CloudSaveTaskSpec extends Specification with Controller  {

  /**
   * test preparation
   */

  implicit val rds = (
    (__ \ 'id).read[String] and
    (__ \ 'value).read[String]) tupled
  
  "A cloudSave request Action " should {
    
    "support an action with parameters" in {
      def hello(parameter: String) = Action {
        Ok("Hello " + parameter)
      }
      
      assertAction(hello("world")) { result =>
        contentAsString(result) must_== "Hello world"
      }
    }
    
    "request with id as parameter " in {
      running(FakeApplication()) {
      val testRequest = route(FakeRequest(GET,"/cloudsave/getCloudObject/?id=1232")).get
      status(testRequest) must equalTo (OK)
      }
    }
    
  }
  
  /**
   * helper methods to obtain Controller tests
   */
  
  def testAction[A](action: Action[A], expectedResponse: Int = OK, request: Request[A] = FakeRequest()) = {
    assertAction(action, expectedResponse, request) { result => success }
  }

  def assertAction[A, T: AsResult](action: Action[A], expectedResponse: Int = OK, request: Request[A] = FakeRequest())(assertions: Future[SimpleResult] => T) = {
    running(FakeApplication()) {
      val result = action(request)
      status(result) must_== expectedResponse
      assertions(result)
    }
  }
}