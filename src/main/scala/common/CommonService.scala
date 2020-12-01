package common

import scala.io.Source

// 各種サービスのAPIキーを一度だけ呼び出したいためtraitを使用
trait CommonService {

  // resourceファイルからデータを取得
  val source = Source.fromFile("src/main/resources/resources.txt", "UTF-8")
  val line = source.getLines.toArray
  source.close
}
