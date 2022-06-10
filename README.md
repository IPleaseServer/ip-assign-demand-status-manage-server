## Logics
### IpAssignDemandCreateSequence
**input** message that type IpAssignDemandCreate

-> when onError **output** message that type IpAssignDemandErrorOnStatus

-> when onSuccess **output** grpc SendAlarm 

IpAssignDemandCreate MessageSpec
```json5
//ROUTING_KEY = ipAssignDemandCreate
{
  "id": 13, //demandId
  "issuerId": 25
}
```

IpAssignDemandErrorOnStatus MessageSpec
```json5
//ROUTING_KEY = ipAssignDemandErrorOnStatus
{
  "id": 13, //demandId
  "message": "이미 id가 13인 신청에 대한 정보가 존재합니다!"
}
```

SendAlarm RequestSpec
```json5
//RPC = sendAlarm()
{
  "title": "예약이 등록됬어요!",
  "content": "담당 선생님이 예약을 확인하시면 다시 알려드릴게요 :)"
}
```