# Railway-Feedback-Complaint-Management-System
This is a big data project that targets the real time management of railways using technologies like Kafka,Spark and Machine Learning

### Project Links:

* [Live Project ](http://35.167.125.98/index.php) (Temporary Hosting, May not remain active forlong time!)

## Pre-Requisites:

* Setup Spark Cluster of atleast 3 Nodes [(Spark Installation)](https://data-flair.training/blogs/install-apache-spark-multi-node-cluster/)

* Install Kakfa & Zookeeper [(Kakfa Installation)](https://www.tutorialspoint.com/apache_kafka/apache_kafka_installation_steps.htm) [(Zookeeper Installation)](https://www.tutorialspoint.com/zookeeper/zookeeper_installation.htm)

* Install Apache-2 (MySQL + Apache Server), PHP-7 on Linux [(LAMP Installation)](https://www.digitalocean.com/community/tutorials/how-to-install-linux-apache-mysql-php-lamp-stack-on-ubuntu-16-04)

* Python 3

## STEPS:

(All the following commands have to be executed on terminal)

**STEP-1:** Start zookeeper:

```
$ zkServer.sh start
```

**STEP-2:** Start kafka:

```
$ bin/kafka-server-start.sh config/server.properties
```

**STEP-3:** Create kafka topic (only once):

```
$ bin/kafka-topics.sh --create --zookeeper --partitions 1 --topic twitter --zookeeper localhost:2181 --replication-factor 1
```

**STEP-4:** Start kafka-consumer:

```
$ bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic twitterstream --from-beginning
```

**STEP-5:** Run stream_data.py file to check tweets are coming or not:

```
$ python kafka_file/stream_data.py
```

**STEP-6:** Next step is to run train_model.py file to train our model, it would produce file "IRModel":

```
$ spark-submit train_model.py
```

**STEP-7:** Create database in MySQL as "twitter" and table with schema:

```
CREATE TABLE tweets (id int AUTO_INCREMENT PRIMARY KEY, tweet varchar(140), username varchar(50), pnr bigint(10), prediction int(1), tweet_id bigint(10), response_status int(1));
```

**STEP-8:** Run live_processing.py file to start real-time tweet classification:

```
$ spark-submit --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.1.0 live_processing.py
```

**STEP-9:** Finally open ```php_files/index.php``` file to interact with UI and manage tweets in real-time.
