package models

import play.api.db.DB
import play.api.Play.current
import scala.slick.ast.BaseTypedType
import scala.slick.jdbc._
import scala.slick.driver.MySQLDriver.simple._
import scala.language.postfixOps
import scala.slick.lifted.Tag

/**
 * Created by wshino on 2014/02/23.
 */
case class User(id: Long, name: String)

object Users {

  lazy val database = Database.forDataSource(DB.getDataSource())

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id      = column[Long]("id", O.PrimaryKey, O AutoInc)
    def name    = column[String]("name")
    def * = (id, name) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]

  def createTable= database.withTransaction{
    implicit s: Session =>
      users.ddl.create
  }

  def dropTable= database.withTransaction{
    implicit s: Session =>
      users.ddl.drop
  }

  def insert(u: User) = database.withTransaction{
    implicit s: Session =>
      users.insert(u)
  }

  def findAll = database.withTransaction {
    implicit s: Session =>
      users.list()
  }

}
