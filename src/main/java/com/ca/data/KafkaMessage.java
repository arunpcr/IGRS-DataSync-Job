package com.ca.data;

public class KafkaMessage {
	String topic;
	String message;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "KofkaMessage [topic=" + topic + ", message=" + message + "]";
	}
	

}
