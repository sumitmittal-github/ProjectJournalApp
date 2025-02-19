# Project Journal App
In our Project Journal App demo project we have integrated Redis and Kafka for below use cases - 

**Redis -**<br/>
We are caching the 3rd party Weather API data for 4 hrs. So every time we do not need to call the API to get the weather information 

**Apache Kafka -**<br/>
Every weekend morning 9AM we send the sentiments email by Spring Scheduler So if the JMS (Java Mail Sender) API is down we will lose those users because we will not be able to send the mails. So the spring scheduler will push all thoses users detail into the kafka topic, and kafka consumer will pick those users information from the kafka topic and will send the emails.

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
1. start zookeeper  				:star_of_david: default port : 2181
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

2. start broker(kafka server)      	:star_of_david: default port : 9092
bin\windows\kafka-server-start.bat config\server.properties

3. Topic 
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic my-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
bin\windows\kafka-topics.bat --describe --topic my-topic --bootstrap-server localhost:9092

4. Producer 
bin\windows\kafka-console-producer.bat --topic my-topic --bootstrap-server localhost:9092

5. Consumer
bin\windows\kafka-console-consumer.bat --topic my-topic --from-beginning --bootstrap-server localhost:9092

6. Consumer group - 
bin\windows\kafka-consumer-groups.bat --list --bootstrap-server localhost:9092

```	
