# Bitcoin Price Index

## 說明

- 分為三個目錄
  - bpi: 提供Bitcoin Price Index查詢，根據幣別對應資料設定以及Coindesk API的最新資料，組裝成新的格式回傳給使用者
  - currency_mapping: 提供幣別對應維護功能 (CRUD)
    - general: 專案通用性設定、資料結構以及工具，如LoggingUtils

## Logging

- 使用AOP方式，紀錄HTTP Request以及Response內容

## 未來可能改進方向

- coindesk api回傳資料可改以非同步方式寫入並暫存 (in file, memory or DB)，資料轉換API可直接取用資料，不用呼叫資料轉換API時都要重新向coindesk取得最新資料。
- 定義錯誤代碼對照表，目前代碼直接寫死
- 需要處理DB資料庫相關例外
- 實作OpenAPI spec
- 提升Unit Test測試覆蓋率