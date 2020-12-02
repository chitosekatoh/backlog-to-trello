import common.ConfigServiceForUnitTest
import org.scalatest._
import flatspec._
import matchers._

class ConfigServiceSpec extends AnyFlatSpec with should.Matchers  {

  val configService = new ConfigServiceForUnitTest()
  val notExistResourceData = configService.notExistResourceData

  "ConfigService.splitResource" should "リソースファイルから取得した値が存在しない場合" in {
    val checkData: String  = null
    val testCase = configService.splitResource(checkData)
    assert(testCase===notExistResourceData)
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が空の場合" in {
    val checkData: String  = ""
    val testCase = configService.splitResource(checkData)
    assert(testCase==="設定値が空です。")
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値に「=」が含まれていない場合" in {
    val checkData: String  = "APIKeyABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase==="「=」が設定されていません。")
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「X=Y」の形の場合" in {
    val checkData: String  = "APIKey=ABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase==="ABCDE")
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「X=」の形の場合" in {
    val checkData: String  = "APIKey="
    val testCase = configService.splitResource(checkData)
    assert(testCase==="APIKeyの値が設定されていません。")
  }

  "ConfigService.splitResource" should "リソースファイルから取得した値が「=Y」の形の場合" in {
    val checkData: String  = "=ABCDE"
    val testCase = configService.splitResource(checkData)
    assert(testCase==="APIのキー名が設定されていません。")
  }

  "ConfigService.isNull" should "リソースファイルから取得した値が存在しない場合" in {
    val checkData = null
    val testCase = configService.splitResource(checkData)
    assert(testCase==="リソースファイルから取得した値が存在しません。")
  }

}