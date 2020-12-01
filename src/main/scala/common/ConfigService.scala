package common

trait ConfigService {
  // resourceファイルから取得した値を分割する
  def splitResource(resourceData: String): String =  {
    /*
      「=」の位置が文字列の最後の場合は
      リソースファイルに設定値が記載されていないと判断してエラーを返す
     */
    if(resourceData.endsWith("=")) {
      println( resourceData.split("=")(0) + "の設定値が不足しています。")
      throw new RuntimeException()
    } else {
      resourceData.split("=")(1)
    }
  }
}
