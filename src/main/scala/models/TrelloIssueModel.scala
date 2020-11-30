package models

// Trelloから取得したBoardデータを入れるクラス
case class RetrieveTrelloBoardModel(id: String, name:String)

// Trelloから取得したListデータを入れるクラス
case class RetrieveTrelloListModel(id: String, name:String)
