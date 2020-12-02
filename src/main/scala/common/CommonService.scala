package common

import com.typesafe.config.ConfigFactory

// 各種サービスのAPIキーを一度だけ呼び出したいためtraitを使用
trait CommonService {
  // application.confを読込
  val applicationConf = ConfigFactory.load()

   // application.confからAPI情報を取得する
  def retrieveAPIKey(data: String): String = {
    val checkedData = isEmptyAPILey(data)
    checkedData match {
      case Right(data) => data
      case Left(data) => sys.error(data)
    }
  }

  def isEmptyAPILey(data: String): Either[String, String] = {
    val apiValue = applicationConf.getString("APIConfig."+data)

    if(apiValue.isEmpty){
      // API設定値が空の場合
      Left(data+applicationConf.getString("error.EMPTY_API_VALUE"))
    } else {
      // API設定が空ではない場合
      Right(apiValue)
    }
  }
}

class CommonServiceForUnitTest extends CommonService {
  override def retrieveAPIKey(data: String): String = super.retrieveAPIKey(data)
}