# Project Journal App
In my Project Journal App demo project, I have integrated Redis and Kafka for below use cases - 

**Redis -**<br/>
I am caching the 3rd party Weather API data for 4 hrs. So every time we do not need to call the Weather API to get the weather information.

```
To execute the Redis caching code in WeatherController.java, hit below get request from postman -
GET : localhost:8080/weather?city=montreal
```
<br/>


**Apache Kafka -**<br/>
Every weekend morning 9AM we send the sentiments email by Spring Scheduler So if the JMS (Java Mail Sender) API is down we will lose those users because we will not be able to send the mails. 
Therefore the spring scheduler will push all thoses users detail into the kafka topic, and kafka consumer will pick those users information from the kafka topic and will send the emails.

```
MyScheduler.java
- This class has a scheduler which will run every 2 minutes(for testing, we can change the cron expression) to fetch few uses.
- We will calculate the setiments of these users
- We will push all these users and their sentiments to Kafka Topic.

KafkaConsumer.java
- Once we will push messages in kafka topic this class has a listener so here we will get all those messages.
- We will calculate the users email Id, email subject and email body
- We will send email to the user with its sentiment. (for sending email we have autowired JavaMailSender)
```

---

**MongoDB Atlas -**<br/>
For MongoDB on cloud, rather than installing MongoDB on local <br/>
> https://cloud.mongodb.com
<br/>


**Weather REST APIs -**<br/>
Register account on weatherstack to get the FREE REST Weather APIs for making different type of REST calls from your project -
> https://weatherstack.com <br/>
> https://weatherstack.com/documentation <br/>
> http://api.weatherstack.com/current?access_key=<YOUR_ACCESS_KEY>&query=mumbai <br/>
<br/>


**Redis for caching -**<br/>
&emsp;Steps to install Redis on local -
1. Type wsl in windows and it will start linux subsystem then run below commands -
2. curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg
3. echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list
4. sudo apt-get update
5. sudo apt-get install redis
6. sudo service redis-server start
7. redis-cli
<br/>


**JMS (Java Mail Sender) -**<br/>
To send email from Java code using Java Mail Sender (JMS) create a app token in your gmail account using below URL - <br/>
> https://myaccount.google.com/apppasswords
<br/>


**Scheduler in spring boot**<br/>
This is to generate the cron expression to run a Cron job in SpringBoot <br/>
> http://www.cronmaker.com
<br/>


**Apache Kafka**<br/>
```
1. start zookeeper                        default port : 2181
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

2. start broker(kafka server)             default port : 9092
bin\windows\kafka-server-start.bat config\server.properties

3. Topic 
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic my-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
bin\windows\kafka-topics.bat --describe --topic my-topic --bootstrap-server localhost:9092

4. Producer 
bin\windows\kafka-console-producer.bat --topic my-topic --bootstrap-server localhost:9092 --property "key.separator=-" --property "parse.key=true"
	Note: The format for sending messages should be -
		key1-value1
		key2-value2


5. Consumer
bin\windows\kafka-console-consumer.bat --topic my-topic --bootstrap-server localhost:9092 --property "key.separator=-" --property "print.key=true" --group console-consumer-group-01 --from-beginning 

6. Consumer group - 
bin\windows\kafka-consumer-groups.bat --list --bootstrap-server localhost:9092

```	
