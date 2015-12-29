package controllers

import play.api.libs.json._
import play.api.mvc._

object Application extends Controller {

    /**
     * Returns echo for the service.
     * @return json document
     */
    def echo = Action {
        Ok(Json.obj
            (
                "name" -> Json.toJson("play-framework-war"),
                "author" -> Json.toJson("Vlad Khazin"),
                "email" -> Json.toJson("vladimir.khazin@icssolutions.ca")
            )
        )
    }
}