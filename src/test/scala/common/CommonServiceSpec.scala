package common

import com.typesafe.config.ConfigFactory
import org.scalatest.flatspec.AnyFlatSpec

class CommonServiceSpec extends AnyFlatSpec {

  // application.confを読込
  val applicationConf = ConfigFactory.load()

  val commonService = new CommonServiceForUnitTest()

  "CommonService.retrieveAPIKey" should "applicationConf.APIConfigにAPI設定値が存在する場合" in {
    val checkData: String  = "BACKLOG_API_KEY"
    val testCase = commonService.retrieveAPIKey(checkData)
    assert(testCase===applicationConf.getString("APIConfig."+checkData))
  }

  "CommonService.retrieveAPIKey" should "applicationConf.APIConfigにAPI設定値が存在しない場合" in {
    val checkData: String  = "BACKLOG_BASE_URL"
    val testCase = commonService.retrieveAPIKey(checkData)
    println(testCase)
    assert(testCase===sys.error(checkData+applicationConf.getString("error.EMPTY_API_VALUE")))
  }

/*
  "CommonService.retrieveAPIKey" should "applicationConf.APIConfigにAPIキーが存在しない場合" in {
    val checkData: String  = "NOT_EXIST_API_KEY"
    val testCase = commonService.retrieveAPIKey(checkData)
    assert(testCase===null)
  }
*/

}
