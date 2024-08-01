# client-get-service

### Service is to for get client entities in database by filters

---

#### Start db

#### Docker build image:

You need find your IP using `ipconfig` for set in `DATASOURCE_HOST`

    ipconfig getifaddr en0

make .jar:

    mvn clean install

For docker build you need account on https://hub.docker.com/

    docker login

    docker build . -t <your_account>/client-get-service:0.0.1

#### Running local Kubernetes clusters

#### Start pods

Set your IP in `deployment.yaml`

  ```
  kubectl apply -f .\k8s\deployment.yaml
  ```