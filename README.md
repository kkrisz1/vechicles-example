# vechicles-example

## vehicles-backend-mongodb

### MongoDB Setup: single-node cluster

_Note: Only for testing purpose_

1. `docker-compose up -d`

    ```
    version: '3.1'
    
    services:
      mongo:
        image: mongo
        container_name: mongo
        hostname: mongo
        restart: always
        command: --replSet replocal
        ports:
          - 27017:27017
    ```

1. `docker exec -it mongo mongo test --eval "rs.initiate({_id: 'replocal', members: [{_id: 0, host: 'mongo:27017'}] })"`

## vehicles-backend-kafka

### create topics

```shell
kafka-topics --bootstrap-server localhost:9092 --create --topic vehicle.position --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic vehicle.beacon --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic vehicle.position-by-geo-hash --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic vehicle.beacon-by-geo-hash --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic vehicle.position-by-geo-hash.aggregate --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic vehicle.beacon-vehicles --partitions 1 --replication-factor 1
```
