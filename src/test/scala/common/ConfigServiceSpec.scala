import com.typesafe.config.ConfigFactory
import common.ConfigServiceForUnitTest
import org.scalatest._
import flatspec._
import matchers._

class ConfigServiceSpec extends AnyFlatSpec with should.Matchers  {

  val configService = new ConfigServiceForUnitTest()
  // application.confを読込
  val message = ConfigFactory.load()

  "ConfigService.splitResource" should "リソースファイルから取得した値が存在しない場合" in {
    val checkData: String  = null
    val testCase = configService.splitResource(checkData)
    assert(testCase===message.getString("error.notExistResourceData"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が空の場合" in {
    val checkData: String  = ""
    val testCase = configService.splitResource(checkData)
    assert(testCase===message.getString("error.emptyAPIKeyAndValue"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値に「=」が含まれていない場合" in {
    val checkData: String  = "APIKeyABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase===message.getString("error.notContainEqual"))
  }


  "ConfigService.splitResource" should "リソースファイルから取得した値が「X=」の形の場合" in {
    val checkData: String  = "APIKey="
    val testCase = configService.splitResource(checkData)
    assert(testCase===checkData.split("=")(0) + message.getString("error.emptyAPIValue"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「=Y」の形の場合" in {
    val checkData: String  = "=ABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase===message.getString("error.emptyAPIKey"))
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「X=Y」の形の場合" in {
    val checkData: String  = "APIKey=ABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase===checkData.split("=")(1))
  }

}