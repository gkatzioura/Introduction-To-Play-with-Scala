package controllers

import javax.inject.Singleton

import com.google.inject.Inject
import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.{Action, Controller}
import repository.{User, UserRepository}
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by gkatzioura on 4/26/17.
  */
@Singleton
class UserController @Inject()(userRepository: UserRepository) extends Controller{

  def all = Action { implicit request =>
    Ok(Json.toJson(userRepository.fetchUsers()))
  }

  def add = Action { implicit request =>

    val user  = Json.fromJson[User](request.body.asJson.get).get
    //val user  = Json.fromJson[User](request.body.asJson.get).get
    val newUser = userRepository.addUser(user)
    Ok(Json.toJson(newUser))

//    val user  = Json.fromJson[User](request.body.asJson.get).get
//    val newUser = userRepository.addUser(user)
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
