package common

import com.typesafe.config.ConfigFactory


trait ConfigService {
  // application.confを読込
  val message = ConfigFactory.load()

  // リソースファイルから取得した値を分割する
  def splitResource(resourceData: String): String = {
    val checkedData = resourceCheck(resourceData)
    checkedData match {
      case Right(data) => data
      case Left(data) => data
    }
  }

  // リソースファイルから取得した値のnullチェック
  def isNull(resourceData: String): String = Option(resourceData) match {
    case Some(data) => data
    case None => message.getString("error.notExistResourceData")
  }

  def resourceCheck(resourceData: String): Either[String, String] = {
    val data = isNull(resourceData)

    if(data.equals(message.getString("error.notExistResourceData"))) {
      // リソースファイルから取得した値が存在しない場合
      Left(message.getString("error.notExistResourceData"))

    } else if(data.isEmpty){
      // リソースファイルから取得した値が空の場合
      Left(message.getString("error.emptyAPIKeyAndValue"))

    } else if(!data.contains("=")){
      // リソースファイルから取得した値に「=」が含まれていない場合
      Left(message.getString("error.notContainEqual"))

    } else if(data.endsWith("=")) {
      // リソースファイルから取得した値が「X=」の形の場合
      Left(data.split("=")(0) + message.getString("error.emptyAPIValue"))

    } else if(data.startsWith("=")) {
      // リソースファイルから取得した値が「=Y」の形の場合
      Left(message.getString("error.emptyAPIKey"))

    } else {
      // リソースファイルから取得した値が「X=Y」の場合
      Right(data.split("=")(1))
    }
  }
}

class ConfigServiceForUnitTest extends ConfigService {
  override def splitResource(resourceData: String): String = super.splitResource(resourceData)

  override def isNull(resourceData: String): String = super.isNull(resourceData)

  override def resourceCheck(resourceData: String): Either[String, String] = super.resourceCheck(resourceData)

}