# Bitcoin Price Index

## 未來可能改進方向

- coindesk api回傳資料可改以非同步方式寫入並暫存 (in file, memory or DB)，資料轉換API可直接取用資料，不用呼叫資料轉換API時都要重新向coindesk取得最新資料。
- 定義錯誤代碼對照表，目前代碼直接寫死