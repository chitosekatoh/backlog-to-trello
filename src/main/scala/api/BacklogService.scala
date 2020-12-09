package api

import common.CommonService
import io.circe.generic.auto._
import io.circe.parser.decode
import models.{BacklogModel, BacklogIssueModel, BacklogProjectModel}
import scalaj.http.Http
import models.DecodeBacklogModel._

trait BacklogService extends CommonService {
  def retrieveProject(): Int
  def retrieveIssues(projectId: Int): List[BacklogIssueModel]
}

class BacklogServiceImpl extends BacklogService {

  // BacklogAPIを使用するのに必要なデータを取得
  val BACKLOG_API_KEY = retrieveAPIValue("BACKLOG_API_KEY")
  val BACKLOG_BASE_URL = retrieveAPIValue("BACKLOG_BASE_URL")

  // プロジェクト一覧の取得
  override def retrieveProject(): Int = {
    val request = BACKLOG_BASE_URL + "/api/v2/projects" + "?" + "apiKey=" + BACKLOG_API_KEY

    val response = Http(request).asString

    if (response.code != 200) {
      throw new RuntimeException("HTTP Request Error.")
    }

    val parsedProject = parseToProject(response.body)

    println("Backlog:プロジェクト名「" + parsedProject.map(value => value.name)(0) + "」から課題を取得します。")
    parsedProject.map(value => value.id)(0)

  }

  // retrieveIssuesのレスポンスをモデルクラスへ出力
  def parseToProject(jsonString: String): List[BacklogProjectModel] = {

    val decodedProject =  decode[List[BacklogProjectModel]](jsonString)
    decodedProject match {
      case Right(backlogProject) => backlogProject
      case Left(error) => throw new RuntimeException(error)
    }
  }

  // 課題一覧の取得
  override def retrieveIssues(projectId: Int): List[BacklogIssueModel]= {

    val request = BACKLOG_BASE_URL + "/api/v2/issues" + "?" + "apiKey=" + BACKLOG_API_KEY + "&" + "projectId[]=" + projectId
    val response = Http(request).asString

    if (response.code != 200) {
      throw new RuntimeException("HTTP Request Error.")
    }

    println("Backlog:課題を取得しました。")
    parseToIssues(response.body).map(value => value)

  }

  // retrieveIssuesのレスポンスをモデルクラスへ出力
  def parseToIssues(jsonString: String): List[BacklogIssueModel] = {

    val decodedIssues =  decode[List[BacklogIssueModel]](jsonString)
    decodedIssues match {
      case Right(backlogTickets) => backlogTickets
      case Left(error) => throw new RuntimeException(error)
    }
  }

  // JSONをパースしてcase classに落とし込む
  def parseToBacklogModel(jsonString: String): List[BacklogModel] = {
    decode[BacklogModel](jsonString) match {
      case Right(decodedList) => List[BacklogModel](decodedList)
      case Left(error) => throw new RuntimeException(error)
    }
  }
}

