package be.c4j.spray.intro

import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.writePretty

/**
 * Definition of the employee concept which has three types.
 * - Consultant
 * - Project manager
 * - Sales manager
 * Two company list are created with employees by the means of a companion object.
 * Two functions are defined to convert the lists into json format
 * A value name is defined on a trait level in order to filter by this property.
 * Created by delans1 on 08-Apr-15 at 4:16 PM.
 */
sealed trait Employee {
  val name : String
}

case class Consultant(name : String , customerSite : String) extends Employee
case class ProjectManager(name : String , price : Float)  extends Employee
case class SalesManager(name : String, sold : Float) extends Employee

/**
 * Companion object of the trait employee
 */
object Employee {
  val companyOneEmployees = List[Employee](
     Consultant( name = "Jacob Kelmendi" , customerSite = "Mazda") ,
     ProjectManager(name = "William Leka" , price = 800)
  )

  val companyTwoEmployees = List[Employee] (
    ProjectManager(name = "Sophia Janssens", price = 950) ,
    SalesManager(name = "Jayden Lambert" , sold = 150000)
  )

  private implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Employee])))

  def toJson(employees : List[Employee]) : String = writePretty(employees)
  def toJson(employee : Employee) : String = writePretty(employee)
}