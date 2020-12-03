import api._


object BacklogToTrello {

  val backlogService: BacklogService =  new BacklogServiceImpl()
  val trelloService: TrelloService = new TrelloServiceImpl()

  def main(args: Array[String]): Unit = {
    // Backlogからプロジェクトを取得
    val backlogProject = backlogService.retrieveProject()

    // Backlogから課題を取得
    val backlogIssues = backlogService.retrieveIssues(backlogProject)

    // Trelloからボードを取得
    val trelloBoard = trelloService.retrieveBoard()

    // Trelloからリスト取得
    val trelloList = trelloService.retrieveList(trelloBoard)

    // Backlogから取得した課題をTrelloに追加
    val trelloCards = trelloService.createCards(trelloList, backlogIssues)
  }
}