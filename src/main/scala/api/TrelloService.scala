package api

import common.CommonService
import io.circe.generic.auto._
import io.circe.parser.{decode, parse}
import models.{RetrieveBacklogIssueModel, RetrieveTrelloBoardModel, RetrieveTrelloListModel}
import scalaj.http.{Http, HttpResponse}

trait TrelloService extends CommonService {
  def retrieveBoard(): String
  def retrieveList(boardId: String): String
  def createCards(listId: String, issueList: List[RetrieveBacklogIssueModel])
}

class TrelloServiceImpl extends TrelloService {

  val TRELLO_KEY = retrieveAPIKey("TRELLO_KEY")
  val TRELLO_TOKEN = retrieveAPIKey("TRELLO_TOKEN")
  val TRELLO_BASE_URL = retrieveAPIKey("TRELLO_BASE_URL")
  val TRELLO_USER_ID = retrieveAPIKey("TRELLO_USER_ID")

  // TrelloからBoard一覧を取得
  override def retrieveBoard(): String = {
    val request = TRELLO_BASE_URL + "/members/" + TRELLO_USER_ID +  "/boards" + "?" + "key=" + TRELLO_KEY + "&" + "token=" + TRELLO_TOKEN + "&" + "fields=name"
    val response = Http(request).asString

    if (response.code != 200) {
      throw new RuntimeException("HTTP Request Error.")
    }

    val parsedBoard = parseToBoard(response.body)
    println("Trello:ボード名「" + parsedBoard.map(value => value.name)(0) + "」からリストを取得します。")

    // 取得したBoard一覧をparseして配列の最初のデータのみ取得
    parsedBoard.map(value => value.id)(0)

  }
  def parseToBoard(jsonString: String): List[RetrieveTrelloBoardModel] = {

    val decodedBoard = decode[List[RetrieveTrelloBoardModel]](jsonString)
    decodedBoard match {
      case Right(trelloBoard) =>
        return trelloBoard

      case Left(error) =>
        throw new RuntimeException(error)

    }
  }

  override def retrieveList(boardId: String): String = {
    val request = TRELLO_BASE_URL + "/boards/" + boardId + "/lists" + "?" + "key=" + TRELLO_KEY + "&" + "token=" + TRELLO_TOKEN + "&" + "fields=name"
    val response = Http(request).asString

    if (response.code != 200) {
      throw new RuntimeException("HTTP Request Error.")
    }

    val parsedList = parseToList(response.body)
    println("Trello:リスト名「" + parsedList.map(value => value.name)(0) + "」にカードを追加します。")
    parsedList.map(value => value.id)(0)
  }

  def parseToList(jsonString: String): List[RetrieveTrelloListModel] = {

    val decodedBoard = decode[List[RetrieveTrelloListModel]](jsonString)
    decodedBoard match {
      case Right(trelloList) =>
        return trelloList

      case Left(error) =>
        throw new RuntimeException(error)

    }
  }

  override def createCards(listId: String, issueData: List[RetrieveBacklogIssueModel]): Unit = {

    val request = TRELLO_BASE_URL + "/cards"

    issueData.foreach(value => {
      val response = Http(request)
          .postForm(Seq("key" -> TRELLO_KEY, "token" -> TRELLO_TOKEN, "idList" -> listId, "name" -> value.summary, "desc" -> "Backlogからの登録")).asString

      if (response.code != 200) {
        throw new RuntimeException("HTTP Request Error.")
      }
      println("Trello:課題「" + value.summary + "」を追加しました。")
    })
  }
}
