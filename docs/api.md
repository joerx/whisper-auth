# API Documentation

Only auth API here, for full API docs, see https://github.com/joerx/whisper-api/blob/master/docs/api.md

## Issue Token

```sh
$ curl -XPOST localhost:9092/token -d'{"client_id": "12345", "client_secret": "hellosecret"}' -H"Content-type: application/json"
{
  "token": "..."
}
```

## Inspect Token

```sh
$ TOKEN=$(curl -XPOST localhost:9092/token -d'{"client_id": "12345", "client_secret": "hellosecret"}' -H"Content-type: application/json" | jq -r '.token') 
{
  "subject": "12345",
  "scopes": [
    "user",
    "admin"
  ],
  "issuer": "io.yodo/whisper",
  "not_before": "2019-11-30T12:36:38.000+0000",
  "expires_at": "2019-11-30T13:36:38.000+0000"
}
$ curl -H"Authorization: Bearer $TOKEN" localhost:9092/inspect
```
