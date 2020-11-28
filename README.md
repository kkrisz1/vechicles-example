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
        restart: always
        command: --replSet replocal
        ports:
          - 27017:27017
    ```

1. `docker exec -it mongo mongo --shell`
1. `rs.initiate({_id: "replocal", members: [{_id: 0, host: "127.0.0.1:27017"}] })`
