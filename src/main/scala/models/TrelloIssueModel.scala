package models

import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._

trait TrelloModel

// Trelloから取得したBoardデータを入れるクラス
case class TrelloBoardModel(id: String, name: String) extends TrelloModel

// Trelloから取得したListデータを入れるクラス
case class TrelloListModel(id: String, name: String, isBoard: String) extends TrelloModel

object DecodeTrelloModel {
  implicit val decodeBacklogModel: Decoder[TrelloModel] =
    List[Decoder[TrelloModel]](
      Decoder[TrelloBoardModel].widen,
      Decoder[TrelloListModel].widen
    ).reduceLeft(_ or _)
}