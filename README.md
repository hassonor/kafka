### Kafka Theory
___
#### Topics, partitions and offsets
* Topics: a particular of data
  * Similar to a table in a database (without all the constraints)
  * You can have as many topics as you want
  * A topic is identified by its **name**
* Topics are split in **partitions**
  * Each partition is ordered
  * Each message within a partition gets an incremental id, called _**offset**_
* **Topic example** `truck_gps`:
  * Say you have a fleet of trucks, each truck reports its GPS position to Kafka.
  * You can have a topic `trucks_gps` that contains the position of all trucks.
  * Each truck will send a message to Kafka every 20 seconds, each message will
  contain the truck ID and the truck position (latitude and longitude)
  * We choose to create that topic with 10 partitions (arbitrary number)
* Offset only have a meaning for a specific partition.
  * E.g. offset 4 in partition 0 doesn't represent the same data as offset 4 in partition 1.
* Order is guaranteed only withing a partition (not across  partitions)

#### Brokers
* A Kafka cluster is composed of multiple brokers (servers)
* Each broker is identified with its ID (Integer)
* Each broker contains certain topic partitions
* After connecting to any broker (called a bootstrap broker), you will be connected to the _entire cluster_
* A **good** number to get started is 3 brokers, but some big clusters have **over** 100 brokers

#### Topic Replication 
____
#### Topic replication factor
* Topics should have a replication factor > 1 (usually **between** 2 **and** 3)
* This way if a broker is down, another broker can serve the data

#### Concept of Leader for a Partition
* At any time only **ONE** broker can be a leader for a given partition
* Only that leader can receive and serve data for a partition
* The other brokers will synchronize the data
* Therefore, each partition has one leader and multiple ISR (in-sync replica).

#### Producers and message keys
___
#### Producers
* Producers write data to topics (which is made of partitions)
* Producers automatically know to which broker and partition to write to
* In case of Broker failures, Producers will automatically recover
* Producers can choose to receive acknowledgment of data writes:
* **acks=0**: Producer won't wait for acknowledgment (possible data loss)
* **acks=1** _(default)_: Producer will wait for leader acknowledgment (limited data loss)
* **acks=all: Leader + replicas acknowledgment (no data loss)

#### Producers: Message keys
* Producers can choose to send a **key** with the message (string, number, etc...)
* If key=null, data is sent round-robin (broker 1 then 2 then 3).
* If key is send, then all messaged for that key will always go to the same partition
* A key is basically sent if you need message ordering for a specific field (ex: `truck_id`)

#### Consumer and consumer group
___
#### Consumers
* Consumers read data from a topic (identified by name)
* Consumers know which broker to read from
* In case of broker failures, consumers know hot to recover
* Data is read in order **within each partition**

#### Consumer Groups
* Consumers read data in consumer groups
* Each consumer withing a group read from exclusive partitions
* If you have more consumers that partitions, some consumers will be inactive
* **_Note:_** Consumers will automatically use a GroupCoordinator and a ConsumerCoordinator to assign a consumers to a partition 

#### Consumer Groups: What if yoo many consumers?
* If you have more consumers that partitions, some consumers will be inactive

#### Consumer Offsets and delivery semantics
___
#### Consumer Offsets
* **Kafka** stores the offsets at which a consumer group has been reading
* The offsets committed live in a **Kafka** _topic_ named ___consumer_offset_
* When a consumer in a group has processed data received from Kafka, it should be committing the offsets
* If a consumer dies, it will be able to read back from where it left off thanks to the committed consumer offsets!

#### Delivery semantics for consumers:
* Consumers choose when to commit offsets.
* There are 3 delivery semantics:
  * **At most once**:
    * offsets are committed as soon as the message is received
    * If the processing goes wrong, the message will be lost (it won't be read again).
  * **At least once (usually preferred)**:
    * offsets are committed after the message is processed.
    * If the processing goes wrong, the message will be read again.
    * This can result in duplicate processing of messages. Make sure your processing is **idempotent** (i.e. processing again
    the messages won't impact your systems)
  * **Exactly once**:
    * Can be achieved for Kafka => Kafka workflows using Kafka Streams API
    * For Kafka => External System workflows, use an **idempotent** consumer.