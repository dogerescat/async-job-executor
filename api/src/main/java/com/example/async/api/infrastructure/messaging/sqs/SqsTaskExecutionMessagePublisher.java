package com.example.async.api.infrastructure.messaging.sqs;

import com.example.async.api.application.port.TaskExecutionMessagePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@Component
public class SqsTaskExecutionMessagePublisher implements TaskExecutionMessagePublisher {
  private final SqsTemplate sqsTemplate;
  private final String queueName;

  public SqsTaskExecutionMessagePublisher(SqsTemplate sqsTemplate,
      @Value("${app.sqs.queue.task}") String queueName) {
    this.sqsTemplate = sqsTemplate;
    this.queueName = queueName;
  }


  @Override
  public void publish(Long taskId) {
    // SQS にメッセージを送信するロジックを実装
    // 例: AmazonSQSClient を使用してメッセージを送信
    TaskExecutionMessageDto dto = new TaskExecutionMessageDto(taskId);
    sqsTemplate.send(to -> to.queue(queueName).payload(dto));
  }

}
