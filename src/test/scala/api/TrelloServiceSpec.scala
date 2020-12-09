package api

import models.{TrelloBoardModel, TrelloListModel}
import org.scalatest.flatspec.AnyFlatSpec

class TrelloServiceSpec extends AnyFlatSpec{

  "TrelloService.parseToTrelloModel(TrelloBoardModel)" should "id=Int, name=Stringを投げると、TrelloBoardModelのListを返却する" in {
    val checkData: String = "{\"id\": \"12345\", \"name\": \"Name\"}"
     val result = new TrelloServiceImpl().parseToTrelloModel(checkData)

    assert(result===List(TrelloBoardModel("12345", "Name")))
  }

  "TrelloService.parseToTrelloModel(TrelloListModel)" should "id=Int, name=String, isBoard=Stringを投げると、TrelloListModelのListを返却する" in {
    val checkData: String = "{\"id\": \"12345\", \"name\": \"Name\", \"isBoard\": \"1234567890ABC\"}"
    val result = new TrelloServiceImpl().parseToTrelloModel(checkData)

    assert(result===List(TrelloListModel("12345", "Name", "1234567890ABC")))
  }
}
