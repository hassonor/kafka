#### Install Apache Kafka on Linux Machine:
___
#### Prerequisites:

* Linux operating system
* Java 8 JDK installed
* Scala 2.11.x installed

1. wget https://downloads.apache.org/kafka/3.3.1/kafka_2.13-3.3.1.tgz
2. tar xzf kafka_2.13-3.3.1.tgz
3. sudo mv kafka_2.13-3.1.1.tgz /usr/local/kafka

#### Apache Kafka Topics
___
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

### Replication Factor
___
* Reliable work distribution
  * Redundancy of messages
  * Cluster resiliency
  * Fault-tolerance
* Guarantees
  * N-1 broker failure tolerance
  * 2 or 3 minimum
* Configured on a per-topic basis
* Viewing Topic State command: `bin/kafka-topics.sh --describe --topic my_topic --zookeeper localhost:2181`

#### Example: Fault-tolerance and Resiliency in Apache Kafka
*  Multi-broker Kafka setup,Single Partition Topic,Replication Factor of 3
1. Start Zookeeper 
2. Start the 3 Brokers By:
 ```shell 
  1. Terminal 1: bin/kafka-server-start.sh config/server-0.properties
  2. Terminal 2: bin/kafka-server-start.sh config/server-1.properties
  3. Terminal 3: bin/kafka-server-start.sh config/server-2.properties
 ```
3. Create topic with relocation factor of 3 and 1 partition:
```shell
  bin/kafka-topics.sh \
   --create --topic replicated_topic \
   --zookeeper localhost:2181 \
   --replication-factor 3 \
   --partitions 1
```
4. Get Details about created topic (3):
```shell
bin/kafka-topics.sh \
--describe --topic replicated_topic \
--zookeeper localhost:2181
```
5. Run the Producer:
```shell
bin/kafka-console-producer.sh \
--broker-list localhost:9092 \
--topic relicated_topic
Type -> Or Hasson was here 1 -> Enter
Type -> Or Hasson was here 2 -> Enter
Type -> Or Hasson was here 3 -> Enter
```
6. Run the Consumer: 
```shell
bin/kafka-console-consumer.sh \
--zookeeper localhost:2181 \
--topic replicatred_topic \
--from-beginning
```

### Kafka Producer Demo:
___
#### Kafka Producer: Required Properties
* **bootstrap.servers**:
  * Cluster membership: partition leaders, etc.
* **key and value serializers**:
  * Classes used for message serialization and deserialization

#### ProducerRecord: Required Properties
* **topic**:
  * Topic to which the ProducerRecord will be sent
* **value**:
  * The message content (matching the serializer type for value)
* KafkaProducer instanced can **ONLY** send producerRecords that match the **key**
and **value** serializers types it is configured with.

#### ProducerRecord: Optional Properties:
* **partition** 
  * specific partition within the topic to send ProducerRecord
* **timestamp**
  * The Unix timestamp applied to the record
* **key**
  * a value to be used as the basis of determining the partitioning strategy to be employed by the Kafka Producer.
* **BEST PRACTICE**: Define a Key
  * **Two useful purposes**:
    * Additional information in the message
    * Can determine what partitions the message will be written to
  * **Downside:**
    * Additional overhead
    * Depends on the serializer type used

#### Micro-batching in Apache Kafka
* At scale, efficiency is everything.
* Small, fast batches of messages:
  * Sending (Producer)
  * Writing (Broker)
  * Reading (Consumer)
* Modern operating system functions: 
  * Pagecache
  * Linux sendfile() system call (kernel)
* Amortization of the constant cost

#### Delivery Guarantees
* Broker acknowledgement("acks")
  * 0: fire and forget
  * 1: leader acknowledged
  * 2: replication quorum acknowledged
* Broker responds with error
  * "retries"
  * "retry.backoff.ms"

#### Ordering Guarantees
* Message order by partition
  * No global order across partitions
* Can get complicated with errors
  * retries, retry.backoff.ms
  * max.in.flight.request.per.connection
* Delivery semantics
  * At-most-once, at least-once, only-once

