package api

import common.{CommonService, ConfigService}
import io.circe.generic.auto._
import io.circe.parser.decode
import models.RetrieveBacklogIssueModel
import models.RetrieveBacklogProjectModel
import scalaj.http.Http

// 呼出元から参照できるメソッドを制限したいのでServiceをtraitで宣言してImplでextendsする
trait BacklogService extends CommonService with ConfigService {
  def retrieveProject(): Int
  def retrieveIssues(projectId: Int): List[RetrieveBacklogIssueModel]
}

class BacklogServiceImpl extends BacklogService {

  // BacklogAPIを使用するのに必要なデータを取得
  val BACKLOG_API_KEY: String = splitResource(line(0))
  val BACKLOG_BASE_URL: String = splitResource(line(1))

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
  def parseToProject(jsonString: String): List[RetrieveBacklogProjectModel] = {

    val decodedProject =  decode[List[RetrieveBacklogProjectModel]](jsonString)
    decodedProject match {
      case Right(backlogProject) =>
        return backlogProject

      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

  // 課題一覧の取得
  override def retrieveIssues(projectId: Int): List[RetrieveBacklogIssueModel]= {

    val request = BACKLOG_BASE_URL + "/api/v2/issues" + "?" + "apiKey=" + BACKLOG_API_KEY + "&" + "projectId[]=" + projectId
    val response = Http(request).asString

    if (response.code != 200) {
      throw new RuntimeException("HTTP Request Error.")
    }

    println("Backlog:課題を取得しました。")
    parseToIssues(response.body).map(value => value)

  }

  // retrieveIssuesのレスポンスをモデルクラスへ出力
  def parseToIssues(jsonString: String): List[RetrieveBacklogIssueModel] = {

    val decodedIssues =  decode[List[RetrieveBacklogIssueModel]](jsonString)
    decodedIssues match {
      case Right(backlogTickets) =>
        return backlogTickets

      case Left(error) =>
        throw new RuntimeException(error)
    }
  }
}
