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
