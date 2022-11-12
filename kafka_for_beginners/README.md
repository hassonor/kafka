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
* Data is kept only for a limited time (_default_ is 1 week)
* Once the data is written to a partition, **it can't be changed** (_immutability_)
* Data is assigned randomly to a partition unless a key is **provided** 

#### Brokers
* A Kafka cluster is composed of multiple brokers (servers)
* Each broker is identified with its ID (Integer)
* Each broker contains certain topic partitions
* After connecting to any broker (called a bootstrap broker), you will be connected to the _entire cluster_
* A **good** number to get started is 3 brokers, but some big clusters have **over** 100 brokers