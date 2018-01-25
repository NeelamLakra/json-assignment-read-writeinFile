package edu.knoldus

import java.io.{File, FileNotFoundException, PrintWriter}

import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Json
import org.json4s.jackson.Serialization.write

import scala.io.Source

@SerialVersionUID(1234)
class Person(val name: String, val age: Int, @transient val day : String = "none", val address: Address,
             val salary: Float, @transient val luckyNumber: Int = 5) extends Serializable {
  override def toString: String = s"$name $age $day $address $salary $luckyNumber"
}

@SerialVersionUID(21)
class Address(street: String, houseNo: Int) extends Serializable {
  override def toString: String = s"$street $houseNo"
}

object JsonImplementation extends App {
  val log = Logger.getLogger(this.getClass)
  implicit val formats = DefaultFormats

  try {

    val name = "neel"
    val age = 21
    val day = "thursday"
    val street = "Rohini"
    val houseNo = 244
    val salary = 21000
    val luckyNumber = 22
    val detailsofPerson = new Person(name, age, day, new Address(street, houseNo), salary, luckyNumber)
    val JSONfordetailsOfPerson = write(detailsofPerson)
    val writeOnFile = new PrintWriter(new File("/home/knoldus/Desktop/file.txt"))
    writeOnFile.write(JSONfordetailsOfPerson)
    writeOnFile.close()
   // Source.fromFile("/home/knoldus/Desktop/file.txt").getLines().take(1).foreach { x => log.info(x) };

  }
  catch {
    case file  : FileNotFoundException => "missing file exception"
    case e: Exception => "exception occured"
  }

  val readData = Source.fromFile("/home/knoldus/Desktop/file.txt").mkString
  val map = Json.apply(formats).read[Person](readData.toString)
  log.debug(map)


}
