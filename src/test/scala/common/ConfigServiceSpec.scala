import com.typesafe.config.ConfigFactory
import common.ConfigServiceForUnitTest
import org.scalatest._
import flatspec._

class ConfigServiceSpec extends AnyFlatSpec {

  // application.confを読込
  val applicationConf = ConfigFactory.load()

  val configService = new ConfigServiceForUnitTest()

  "ConfigService.splitResource" should "リソースファイルから取得した値が存在しない場合" in {
    val checkData: String  = null
    val testCase = configService.splitResource(checkData)
    assert(testCase===applicationConf.getString("error.NOT_EXIST_RESOURCE_DATA"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が空の場合" in {
    val checkData: String  = ""
    val testCase = configService.splitResource(checkData)
    assert(testCase===applicationConf.getString("error.EMPTY_API_KEY_AND_VALUE"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値に「=」が含まれていない場合" in {
    val checkData: String  = "APIKeyABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase===applicationConf.getString("error.NOT_CONTAIN_EQUAL"))
  }


  "ConfigService.splitResource" should "リソースファイルから取得した値が「X=」の形の場合" in {
    val checkData: String  = "APIKey="
    val testCase = configService.splitResource(checkData)
    assert(testCase===checkData.split("=")(0) + applicationConf.getString("error.EMPTY_API_VALUE"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「=Y」の形の場合" in {
    val checkData: String  = "=ABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase===applicationConf.getString("error.EMPTY_API_KEY"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「X=Y」の形の場合" in {
    val checkData: String  = "APIKey=ABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase===checkData.split("=")(1))
  }

}