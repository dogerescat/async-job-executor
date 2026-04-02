# curl コマンド集

タスク作成 API と LocalStack SQS の状態確認用メモ。SQS は `awslocal` が使えるので、基本は CLI で確認し、必要に応じて curl + SigV4 も載せています。

## 前提

- API: `http://localhost:8080`
- LocalStack (SQS): `http://localhost:4566`
- デフォルトリージョン: `ap-northeast-1`
- SQS キュー名: `task-queue` (`app.sqs.queue.task` に設定)
- 署名付きリクエスト用の認証情報（LocalStack なのでダミーで OK）
  - `AWS_ACCESS_KEY_ID=test`
  - `AWS_SECRET_ACCESS_KEY=test`

## まずキューを作る（初回のみ必須）

LocalStack は SQS キューを自動生成しないため、`task-queue` が存在しないと以降の操作は `AWS.SimpleQueueService.NonExistentQueue` で失敗します。`awslocal` での作成が最も簡単です。

```sh
awslocal sqs create-queue --queue-name task-queue
```

curl で作りたい場合は以下を利用してください。

```sh
AWS_ACCESS_KEY_ID=test \
AWS_SECRET_ACCESS_KEY=test \
curl --aws-sigv4 "aws:amz:ap-northeast-1:sqs" \
  -X POST "http://localhost:4566" \
  -d "Action=CreateQueue&QueueName=task-queue&Version=2012-11-05"
```

キュー URL は LocalStack のデフォルトアカウント ID `000000000000` を用いて組み立てます。

## タスクを作成する

```sh
curl -X POST "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "sample title",
    "description": "sample description",
    "inputContext": "optional context",
    "outputType": "EXCEL"
  }'
```

レスポンス例（201 Created）

```json
{
  "id": 1
}
```

## SQS を確認する（awslocal 推奨）

```sh
# キュー一覧
awslocal sqs list-queues

# メッセージ受信（最大 10 件、待ち時間 2 秒）
awslocal sqs receive-message \
  --queue-url "http://localhost:4566/000000000000/task-queue" \
  --max-number-of-messages 10 \
  --wait-time-seconds 2

# メッセージ数の概算
awslocal sqs get-queue-attributes \
  --queue-url "http://localhost:4566/000000000000/task-queue" \
  --attribute-names ApproximateNumberOfMessages
```

## SQS を確認する（curl + SigV4 版, 参考）

`curl` の `--aws-sigv4` オプションを使って署名する。環境変数にダミー認証情報を渡し、エンドポイントは LocalStack を指定する。

```sh
# キュー一覧
AWS_ACCESS_KEY_ID=test AWS_SECRET_ACCESS_KEY=test \
curl --aws-sigv4 "aws:amz:ap-northeast-1:sqs" \
  -X POST "http://localhost:4566" \
  -d "Action=ListQueues&Version=2012-11-05"

# メッセージ受信
QUEUE_URL="http://localhost:4566/000000000000/task-queue"
AWS_ACCESS_KEY_ID=test AWS_SECRET_ACCESS_KEY=test \
curl --aws-sigv4 "aws:amz:ap-northeast-1:sqs" \
  -X POST "$QUEUE_URL" \
  -d "Action=ReceiveMessage&MaxNumberOfMessages=10&VisibilityTimeout=0&WaitTimeSeconds=2&Version=2012-11-05"

# メッセージ数の概算
AWS_ACCESS_KEY_ID=test AWS_SECRET_ACCESS_KEY=test \
curl --aws-sigv4 "aws:amz:ap-northeast-1:sqs" \
  -X POST "$QUEUE_URL" \
  -d "Action=GetQueueAttributes&AttributeName=ApproximateNumberOfMessages&Version=2012-11-05"
```

### LocalStack が `sqs.us-east-1.localhost.localstack.cloud` を返す場合

LocalStack の設定によってはキュー作成レスポンスの QueueUrl が
`http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/task-queue`
のようなドメイン形式になり、`localhost` と食い違って curl が失敗することがあります。対処はいずれかを選択してください。

- `docker-compose.yaml` の `localstack` サービスに `SQS_ENDPOINT_STRATEGY=path` を追加して再起動する
  （`http://localhost:4566/000000000000/queue-name` 形式に固定されます）。
- もしくはレスポンスに含まれる QueueUrl をそのまま使ってリクエストする（SigV4 のリージョン指定は `ap-northeast-1` のままで動作します）。
