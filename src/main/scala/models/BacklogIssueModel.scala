package models

// Backlogから取得したProjectを入れるクラス
case class RetrieveBacklogProjectModel(id: Int, name: String)

// Backlogから取得したIssueを入れるクラス
case class RetrieveBacklogIssueModel(id: Int, summary: String)