# async-job-executor
非同期実行サービス

# Async Task Platform

This project is an asynchronous task execution platform.

Users register tasks via a Java-based API.
Tasks are executed asynchronously by a Go-based worker.
Each execution is tracked with execution history and logs.

## Architecture (WIP)
- API: Java
- Worker: Go
- Execution model: Async / Queue-based
- Database: PostgreSQL (planned)
