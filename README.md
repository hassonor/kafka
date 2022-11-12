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

