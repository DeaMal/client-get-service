# client-get-service

### Service is to for get client entities in database by filters

---

#### Start db

#### Docker build image:

make `.jar`:

    mvn clean install

For docker build you need account on https://hub.docker.com/

    docker login

    docker build . -t <your_account>/client-get-service:0.0.1

    docker push <your_account>/client-get-service:0.0.1

#### Local docker run

You need find your IP using `ipconfig` for set in `DATASOURCE_HOST`

    ipconfig getifaddr en0

docker run:

    docker run -ti --rm -e DATASOURCE_HOST=192.168.56.1 -p 8081:8080 <your_account>/client-create-service:0.0.1

#### Running local Kubernetes clusters

Instructions in `shopDataBase` repository

#### Start kubernetes pods

    kubectl apply -f .\k8s\deployment.yaml

#### Using the service

To use the service, use `Postman` or a similar application, you can use `cURL`.

`POST` request:

    localhost:8888/client/get

Example of the request body. You can use any combination of parameters, except the `email` field. Search by `email` is carried out in strict accordance, so it must be unique.
```
{
    "firstname": "John"
    "lastname": "Black"
    "phone": "+7(913)"
    "phone_extra": "7662"
    "email": "mr.grey@spyder.hd"
    "create_at": {
        "begin": "2024-08-03T17:33:43.761",
        "end": "2024-08-03T17:33:43.761"
    }
    "update_at": {
        "begin": "2024-08-02",
        "end": "2024-08-03T17:33:43"
    }
    "address": "big"
    "notes": "man"
    "last_order_date": {
        "begin": "2024-08-02",
        "end": "2024-08-03"
    }
    "total_orders": {
        "min": "4",
        "max": "5"
    }
    "total_spent": {
        "min": "0",
        "max": "15"
    }
}
```

`GET` request:

    localhost:8888/client/get/2

To use the service without `kubernetes` and `docker` use swagger interface:

    http://localhost:8080/swagger-ui.html

for `docker` without `kubernetes`:

    http://localhost:8081/swagger-ui.html