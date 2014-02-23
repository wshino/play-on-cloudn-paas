import models.Users
import play.Logger
import play.api._

/**
 * Created by wshino on 2014/02/23.
 */
object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    Users.createTable
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
    Users.dropTable
  }
}
