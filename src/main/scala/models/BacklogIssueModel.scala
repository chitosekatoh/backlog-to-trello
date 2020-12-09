package models

import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._

sealed trait BacklogModel

// Backlogから取得したProjectを入れるクラス
case class BacklogProjectModel(id: Int, name: String) extends BacklogModel

// Backlogから取得したIssueを入れるクラス
case class BacklogIssueModel(id: Int, summary: String) extends BacklogModel

object DecodeBacklogModel {
  implicit val decodeBacklogModel: Decoder[BacklogModel] =
    List[Decoder[BacklogModel]](
      Decoder[BacklogProjectModel].widen,
      Decoder[BacklogIssueModel].widen
    ).reduceLeft(_ or _)
}