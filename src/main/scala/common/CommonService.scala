package common

import com.typesafe.config.ConfigFactory

trait CommonService {
  // application.confを読込
  val applicationConf = ConfigFactory.load()

   // application.confからAPI情報を取得する
  def retrieveAPIValue(data: String): String = {
    val checkedData = applicationConf.getString("APIConfig."+data)

    if(!checkedData.isEmpty) checkedData
    else sys.error(data + applicationConf.getString("error.EMPTY_API_VALUE"))
  }
}
