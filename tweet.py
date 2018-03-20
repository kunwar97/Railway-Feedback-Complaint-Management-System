#!/usr/bin/env python
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
from kafka import SimpleProducer, KafkaClient
import json
access_token = "864680156-TSZZROpEeM6Cws1IRkeGL7z21Y9aEJGkRkuNlWAB"
access_token_secret =  "11OhRdINI6NTqshGu4ZkmVpZURGe60nlzWVF9Z3EQ4pjF"
consumer_key =  "P1oA48Q4H8FAOuzV0hBKuuIM6"
consumer_secret =  "MAZfodXX1gumAZjgHf9VydGPcaC4XfASQBcHZtwlE8gvv8JNNS"

class StdOutListener(StreamListener):
    def on_data(self, data):
        producer.send_messages("test", data.encode('utf-8','ignore'))
	data=json.loads(data)
        try:
	   print (data["text"])
	except:
	   print(data["text"])
        return True
    # def on_error(self, status):
    #     print (status)


kafka = KafkaClient("localhost:9092,34.218.14.113:9092,34.218.217.165:9092")
producer = SimpleProducer(kafka)
l = StdOutListener()
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
stream = Stream(auth, l)
stream.filter(track=['RailMinIndia','rail minister','suresh prabhu','northern railways'], stall_warnings=True, languages = ['en'])
