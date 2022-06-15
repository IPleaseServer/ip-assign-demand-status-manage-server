## Logics
### IpAssignDemandCancelSequence
**input** message that type IpAssignDemandCancel

-> **logic** verify that target DemandId
_> **logic** verify that target DemandStatus is Cancelable
-> **logic** remove that target DemandStatusData to Persistence

-> when onError **output** message that type IpAssignDemandCancelErrorOnStatus

-> when onSuccess **output** message that type SendAlarm

IpAssignDemandCancel MessageSpec
```json5
{
  "id":17,
  "issuerId":1,
  "title":"다른사람이 제 IP를 사용중입니다.",
  "description":"오늘 아침에, 교내 네트워크에 접속하려하였으나, 아무리 재시도해보아도 접속이 안됩니다. IP를 재할당해주실 수 있으신가요??",
  "usage":"USE_NETWORK",
  "expireAt":"2023-03-01"
}
```

IpAssignDemandCancelErrorOnStatus MessageSpec
```json5
{
  "id":17,
  "issuerId":1,
  "title":"다른사람이 제 IP를 사용중입니다.",
  "description":"오늘 아침에, 교내 네트워크에 접속하려하였으나, 아무리 재시도해보아도 접속이 안됩니다. IP를 재할당해주실 수 있으신가요??",
  "usage":"USE_NETWORK",
  "expireAt":"2023-03-01"
}
```

### IpAssignDemandCreateSequence
**input** message that type IpAssignDemandCreate

-> **logic** verify that target DemandId
-> **logic** convert target DemandId target DemandStatusData
-> **logic** add that target DemandStatusData to Persistence

-> when onError **output** message that type IpAssignDemandErrorOnStatus

-> when onSuccess **output** message that type SendAlarm

IpAssignDemandCreate MessageSpec
```json5
//ROUTING_KEY = ipAssignDemandCreate
{
  "demandId": 13,
  "issuerId": 25
}
```

IpAssignDemandErrorOnStatus MessageSpec
```json5
//ROUTING_KEY = ipAssignDemandErrorOnStatus
{
  "demandId": 13,
  "issuerId": 12,
  "message": "이미 id가 13인 신청에 대한 정보가 존재합니다!"
}
```

SendAlarm RequestSpec
```json5
//RPC = sendAlarm()
{
  "type": "FCM",
  "receiverId": 43,
  "title": "예약이 등록됬어요!",
  "content": "담당 선생님이 예약을 확인하시면 다시 알려드릴게요 :)"
}
```
