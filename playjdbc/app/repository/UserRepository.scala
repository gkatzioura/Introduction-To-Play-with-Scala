package repository

import java.sql.{ResultSet, Statement}
import javax.inject.Inject

import play.api.db.Database

import scala.collection.mutable.ListBuffer

/**
  * Created by gkatzioura on 4/23/17.
  */
case class User(id: Option[Long],email:String,firstName:String,lastName:String)

class UserRepository @Inject()(db:Database) {

  def fetchUsers(): List[User] = {

    db.withConnection { conn =>

      val stmt = conn.createStatement
      var rs = stmt.executeQuery("SELECT*FROM users");
      val listBuffer = ListBuffer[User]()

      while(rs.next()) {

        listBuffer.append(User(Option(rs.getLong("id")),rs.getString("email"),rs.getString("first_name"),rs.getString("last_name")))
      }

      listBuffer.toList
    }
  }

  def addUser(user:User): User = {

    db.withTransaction { conn =>
      val stmt = conn.createStatement

      val insertQuery = "INSERT INTO users ( email, first_name, last_name) VALUES( '"+user.email+"', '"+user.firstName+"','"+user.lastName+"') "
      stmt.executeUpdate(insertQuery,Statement.RETURN_GENERATED_KEYS)
      val resultSet = stmt.getGeneratedKeys;
      if(resultSet.next()) {
        val id = resultSet.getLong(1);
        new User(Option(id),user.email,user.firstName,user.lastName)
      } else {
        throw new Exception("User not persisted properly")
      }
    }
  }


}
