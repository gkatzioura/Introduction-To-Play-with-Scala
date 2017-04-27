package controllers

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import play.api.libs.functional.syntax._

/**
  * Created by gkatzioura on 4/26/17.
  */
case class User(id:Option[Long],email:String,firstName:String,lastName:String)

@Singleton
class UserController @Inject() extends Controller {

  def all = Action { implicit request =>
    val users = Seq(
      User(Option(1L),"gkazoura@example.com","Emmanouil","Gkatziouras"),
      User(Option(2L),"john@doe.com","John","Doe"),
      User(Option(3L),"john2@doe.com","John2","Doe2")
    )
    Ok(Json.toJson(users))
  }

  def greet = Action

  def add = Action { implicit request =>

    val user  = Json.fromJson[User](request.body.asJson.get).get
    val newUser = User(Option(4L),user.email,user.firstName,user.lastName)
    Ok(Json.toJson(newUser))
  }

  implicit val userWrites = new Writes[User] {
    def writes(user: User) = Json.obj(
      "id" -> user.id,
      "email" -> user.email,
      "firstName" -> user.firstName,
      "lastName" -> user.lastName
    )
  }

  implicit val userReads: Reads[User] = (
    (__ \ "id").readNullable[Long] and
      (__ \ "email").read[String] and
      (__ \ "firstName").read[String] and
      (__ \ "lastName").read[String]
    )(User.apply _)

}
