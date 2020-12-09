### 概要
Backlogの課題を取得しTrelloへチケット作成するプログラムです

### 起動前準備
以下のAPIConfigにAPIリクエストに必要な設定値を書き込みます

/src/main/resources/application.conf  

必要な設定値 
- Backlog
  - APIキー(BACKLOG_API_KEY)
  - APIリクエスト先URL(BACKLOG_BASE_URL)
- Trello
  - APIキー(TRELLO_KEY)
  - APIトークン(TRELLO_TOKEN)
  - APIリクエスト先URL(TRELLO_BASE_URL)
  - ユーザID(TRELLO_USER_ID)

URLの末尾にスラッシュは不要です

### 起動方法
起動コマンド：stb run


### 仕様
- Backlogから以下条件で課題を取得します
  - 取得対象プロジェクト：Backlogへプロジェクト取得APIを投げて返却されたデータにおける最初のプロジェクト
- Trelloには以下条件でカードを作成します
  - 作成対象ボード：Trelloへボード取得APIを投げて返却されたデータにおける最初のボード
  - 作成対象リスト：Trelloへリスト取得APIを投げて変約されたデータにおける最初のリスト
