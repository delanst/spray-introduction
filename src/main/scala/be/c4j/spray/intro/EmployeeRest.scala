package be.c4j.spray.intro

import akka.actor.ActorSystem
import spray.http.MediaTypes
import spray.routing.{Route, SimpleRoutingApp}

/**
 * A simple spray routing application with basic GET and POST methods. For further example visit the spray.io
 * website.
 * Created by delans1 on 09-Apr-15 at 6:27 PM.
 */
object EmployeeRest extends App with SimpleRoutingApp {

  implicit val actorSystem = ActorSystem()

  var companyOneEmployees : List[Employee] = Employee.companyOneEmployees
  var companyTwoEmployees : List[Employee] = Employee.companyTwoEmployees

  /**
   * encapsulation of the route that will respond with a given media type.
   * @param route Route
   * @return Route
   */
  def json(route : Route) : Route = {
    get {
      respondWithMediaType(MediaTypes.`application/json`) {
        route
      }
    }
  }

  lazy val companyOneEmployeesRoute = json {
    path("employee" / "companyone" / "list") {
      complete { Employee.toJson(companyTwoEmployees) }
    }
  }

  lazy val companyTwoEmployeesRoute = json {
    path("employee" / "companytwo" / "list") {
      complete { Employee.toJson(companyOneEmployees) }
    }
  }
  /**
   * sample GET with a given property on an object. In  this case we filter the list for  a given name.
   */
  lazy val companyTwoEmployeeByIdRoute = json {
    path("employee" / "companytwo" / "get" ) {
      parameters("name") { (name) =>
        complete {
          Employee.toJson(companyTwoEmployees filter ( (em : Employee) => em.name == name  ) )
        }
      }
    }
  }

  /**
   * sample of a post route, with one optional parameter called name. This is indicate with the ?. In case the
   * parameter is optional a default value needs to specified. Here getOrElse method is on a string value.
   * The response defined in the complete we return a string "OK".
   */
  lazy val salesEmployeePostRoute = post {
    path("employee" / "companyone" / "sales" / "post") {
      parameters("name"?,"sold".as[Float]) { (name , sold) =>
        val newEmployee = SalesManager(name.getOrElse("Test Name"),sold)
        companyTwoEmployees = newEmployee :: companyTwoEmployees
        complete {
          "OK"
        }
      }
    }
  }
  /**
   * start of the local sever on port 8080. The akka server is used as fast way of demonstrating REST calls (ActorSystem).
   * A routing tree is constructed by chaining directives (get, post ...) with the ~
   * A better way of defining each directive is to define it seperatly in a function. Each function on it's turn
   * is encapsulated with Json mediatype on the route (@see json).
   */
  startServer(interface = "localhost" , port = 8080) {
    companyOneEmployeesRoute ~ companyTwoEmployeesRoute ~ salesEmployeePostRoute ~ companyTwoEmployeeByIdRoute
  }

}
