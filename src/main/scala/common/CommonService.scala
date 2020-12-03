package common

import com.typesafe.config.ConfigFactory

// 各種サービスのAPIキーを一度だけ呼び出したいためtraitを使用
trait CommonService {
  // application.confを読込
  val applicationConf = ConfigFactory.load()

   // application.confからAPI情報を取得する
  def retrieveAPIKey(data: String): String = {
    val checkedData = isEmptyAPIKey(data)

    checkedData match {
      case Some(data) => data
      case None =>  data + applicationConf.getString("error.EMPTY_API_VALUE")
    }
  }

  def isEmptyAPIKey(data: String): Option[String] = {
    val apiValue = applicationConf.getString("APIConfig."+data)

    // API設定値が空の場合
    if(apiValue.isEmpty) None
    // API設定値が空ではない場合
    else Some(apiValue)
  }
}

class CommonServiceForUnitTest extends CommonService {
  override def retrieveAPIKey(data: String): String = super.retrieveAPIKey(data)
}