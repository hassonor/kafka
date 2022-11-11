#### Install Apache Kafka on Linux Machine:
#### Prerequisites:
* Linux operating system
* Java 8 JDK installed
* Scala 2.11.x installed

1. wget https://downloads.apache.org/kafka/3.3.1/kafka_2.13-3.3.1.tgz
2. tar xzf kafka_2.13-3.3.1.tgz
3. sudo mv kafka_2.13-3.1.1.tgz /usr/local/kafka

#### Apache Kafka Topics
* Central Kafka abstraction
* Named feed or category of messages
  * Producers produce to a topic
  * Consumers consume from a topic
* Logical entity
* Physically represented as a log

#### The Offset
* A placeholder:
  * Last read message position
  * Maintained by the Kafka Consumer
  * Corresponds to the message identifier

#### Message Retention Policy
* Apache Kafka retains all published messages regardless of consumption
* Retention period is configurable
  * Default is **168** hours or **seven** days
* Retention period is defined on a per-topic basis
* Physical storage resources can constrain message retention


#### Transaction or Commit Logs
* Source of truth
* Physically stored and maintained
* Higher-order data structures derive from the log
  * Tables, indexes, views, etc.
* Point of recovery
* **Apache Kafka is public-subscribe messaging rethought as distributed commit log.**

### Kafka partitions
___
* Each topic has one or more partitions
* A partition is the basis for which Kafka can:
  * Scale 
  * Become fault-tolerant
  * Achieve higher levels of throughput
* Each partition is maintained on at least one of more Brokers
* Creating a Topic: Single Partition:
```shell
~$ bin/kafka-topics.sh --create --topic my_topic \
> --zookeeper localhost:2181 \ 
> --partitions 1 \
> --replication-factor 1
```
* **In general**, the scalability of Apache Kafka is determined by the number of partitions being managed by multiple brokers nodes.

#### Partitioning Trade-offs
* The more partitions the greater the Zookeeper overhead
  * With large partition numbers ensure proper ZK capacity
* Message ordering can become complex
  * Single partition for global ordering
  * Consumer-handling for ordering
* The more partitions the longer the leader fail-over time