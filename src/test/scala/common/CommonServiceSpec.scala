package common

import com.typesafe.config.{ConfigException, ConfigFactory}
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec


class CommonServiceForUnitTest extends CommonService {
  override def retrieveAPIValue(data: String): String = super.retrieveAPIValue(data)
}

class CommonServiceSpec extends AnyFlatSpec {

  // application.confを読込
  val applicationConf = ConfigFactory.load()

  val commonService = new CommonServiceForUnitTest()

  "CommonService.retrieveAPIKey" should "applicationConf.APIConfigのAPI設定値が空ではない場合" in {
    val checkDataList: List[String] = List(
      "BACKLOG_API_KEY",
      "BACKLOG_BASE_URL",
      "TRELLO_KEY",
      "TRELLO_TOKEN",
      "TRELLO_BASE_URL",
      "TRELLO_USER_ID"
    )

    checkDataList
      .map(value => commonService.retrieveAPIValue(value))
      .foreach(result => assert(result!=""))
  }

  "CommonService.retrieveAPIKey" should "applicationConf.APIConfigにAPIキーが存在しない場合" in {
    val checkData: String = "NOT_EXIST_API_KEY"
    assertThrows[ConfigException] {
      commonService.retrieveAPIValue(checkData)
    }
  }
}
