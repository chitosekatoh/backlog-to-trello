package common

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class CommonServiceSpec extends AnyFlatSpec with should.Matchers  {

  val commonService = new CommonServiceForUnitTest()

  "CommonService.existenceCheck" should "存在するパスの場合Some型でパス名を返却する" in {
    val checkData: String  = "src\\main\\resources\\resources.txt"
    val testCase = commonService.existenceCheck(checkData)
    assert(testCase===Some(checkData))
  }

  "CommonService.existenceCheck" should "存在しないパスの場合Noneを返却する" in {
    val checkData: String  = "src\\main\\resources\\notexistence.txt"
    val testCase = commonService.existenceCheck(checkData)
    assert(testCase===None)
  }

  "CommonService.existenceCheck" should "パスがブランクの場合Noneを返却する" in {
    val checkData: String  = ""
    val testCase = commonService.existenceCheck(checkData)
    assert(testCase===None)
  }
}
