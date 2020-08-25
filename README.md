<a href="https://gitmoji.carloscuesta.me">
  <img src="https://img.shields.io/badge/gitmoji-%20😜%20😍-FFDD67.svg?style=flat-square" alt="Gitmoji">
</a>

## Kafka Beginners Course

Esse repositório foi criado adicionando os exercícios do curso [Kafka Beginners Course](https://www.udemy.com/course/apache-kafka).

O código segue quase todo na integra dos exercicios passados durante o curso com algumas pequenas modificações.

Eu adicionei um docker-compose para que não seja preciso uma configuração local do Kafka.
- Para iniciar os containers do Kafka:
```bash
docker-compose up -d
```
- Para parar os containers do Kafka:
```bash
docker-compose stop
```

Como o Kafka tá rodando dentro do container se for necessário rodar algum comando deve iniciado com `docker-compose exec kafka`.

Segue abaixo alguns exemplos:
- Criar um novo tópico:
```bash
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1
```

- Criar um consumidor:
```
docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:29092 --topic first_topic --group tutorial1 --property print.key=true --property key.separator='|' --from-beginning
```