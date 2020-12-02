package common

import java.io.File
import scala.io.Source

// 各種サービスのAPIキーを一度だけ呼び出したいためtraitを使用
trait CommonService {

  // リソースファイルのパスを定義
  val filePath: String = "src\\main\\resources\\resources.txt"

  // リソースファイルの存在チェックを実施
  def existenceCheck(fileName: String): Option[String] = {
    val file = new File(fileName)
    if(file.exists) Some(file.toString)
    else None
  }

  // リソースファイルが存在する場合String型でファイルパスを保持
  val existFilePath : String = existenceCheck(filePath) match {
    case Some(fileName) => fileName
    case None => "リソースファイルのパスが存在しません。[" + filePath + "]"
  }

  // resourceファイルからデータを取得
  val resourceData = Source.fromFile(existFilePath, "UTF-8")
  val line = resourceData.getLines.toArray

  resourceData.close
}

class CommonServiceForUnitTest extends CommonService {
  override def existenceCheck(fileName: String): Option[String] = super.existenceCheck(fileName)
}