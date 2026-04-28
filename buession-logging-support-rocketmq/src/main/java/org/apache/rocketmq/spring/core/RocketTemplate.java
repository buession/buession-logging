/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.spring.core;

import com.buession.core.validator.Validate;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class RocketTemplate extends AbstractMessageSendingTemplate<String> implements InitializingBean, DisposableBean {

	private final static Logger logger = LoggerFactory.getLogger(RocketTemplate.class);

	private DefaultMQProducer producer;

	private Charset charset = StandardCharsets.UTF_8;

	private MessageQueueSelector messageQueueSelector = new SelectMessageQueueByHash();

	public DefaultMQProducer getProducer() {
		return producer;
	}

	public void setProducer(DefaultMQProducer producer) {
		this.producer = producer;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public MessageQueueSelector getMessageQueueSelector() {
		return messageQueueSelector;
	}

	public void setMessageQueueSelector(MessageQueueSelector messageQueueSelector) {
		this.messageQueueSelector = messageQueueSelector;
	}

	public void setAsyncSenderExecutor(ExecutorService asyncSenderExecutor) {
		this.producer.setAsyncSenderExecutor(asyncSenderExecutor);
	}

	/**
	 * <p> Send message in synchronous mode. This method returns only when the sending procedure totally completes.
	 * Reliable synchronous transmission is used in extensive scenes, such as important notification messages, SMS
	 * notification, SMS marketing system, etc.. </p>
	 * <p>
	 * <strong>Warn:</strong> this method has internal retry-mechanism, that is, internal implementation will retry
	 * {@link DefaultMQProducer#getRetryTimesWhenSendFailed} times before claiming failure. As a result, multiple
	 * messages may potentially delivered to broker(s). It's up to the application developers to resolve potential
	 * duplication issue.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param message
	 *        {@link Message}
	 *
	 * @return {@link SendResult}
	 */
	public SendResult syncSend(String destination, Message<?> message) {
		return syncSend(destination, message, producer.getSendMsgTimeout());
	}

	/**
	 * Same to {@link #syncSend(String, Message)} with send timeout specified in addition.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param message
	 *        {@link Message}
	 * @param timeout
	 * 		send timeout with millis
	 *
	 * @return {@link SendResult}
	 */
	public SendResult syncSend(String destination, Message<?> message, long timeout) {
		return syncSend(destination, message, timeout, 0);
	}

	/**
	 * syncSend batch messages
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param messages
	 * 		Collection of {@link Message}
	 *
	 * @return {@link SendResult}
	 */
	public <T extends Message> SendResult syncSend(String destination, Collection<T> messages) {
		return syncSend(destination, messages, producer.getSendMsgTimeout());
	}

	/**
	 * syncSend batch messages in a given timeout.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param messages
	 * 		Collection of {@link Message}
	 * @param timeout
	 * 		send timeout with millis
	 *
	 * @return {@link SendResult}
	 */
	public <T extends Message> SendResult syncSend(String destination, Collection<T> messages, long timeout) {
		if(Validate.isEmpty(messages)){
			logger.error("syncSend with batch failed. destination:{}, messages is empty ", destination);
			throw new IllegalArgumentException("`messages` can not be empty");
		}

		try{
			long now = System.currentTimeMillis();
			Collection<org.apache.rocketmq.common.message.Message> rmqMsgs = new ArrayList<>();
			for(T msg : messages){
				if(Objects.isNull(msg) || Objects.isNull(msg.getPayload())){
					logger.warn("Found a message empty in the batch, skip it");
					continue;
				}
				rmqMsgs.add(createRocketMqMessage(destination, msg));
			}

			SendResult sendResult = producer.send(rmqMsgs, timeout);
			long costTime = System.currentTimeMillis() - now;
			if(logger.isDebugEnabled()){
				logger.debug("send messages cost: {} ms, msgId:{}", costTime, sendResult.getMsgId());
			}
			return sendResult;
		}catch(Exception e){
			logger.error("syncSend with batch failed. destination:{}, messages.size:{} ", destination, messages.size());
			throw new MessagingException(e.getMessage(), e);
		}
	}

	/**
	 * Same to {@link #syncSend(String, Message)} with send timeout specified in addition.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param message
	 *        {@link Message}
	 * @param timeout
	 * 		send timeout with millis
	 * @param delayLevel
	 * 		level for the delay message
	 *
	 * @return {@link SendResult}
	 */
	public SendResult syncSend(String destination, Message<?> message, long timeout, int delayLevel) {
		if(Objects.isNull(message) || Objects.isNull(message.getPayload())){
			logger.error("syncSend failed. destination:{}, message is null ", destination);
			throw new IllegalArgumentException("`message` and `message.payload` cannot be null");
		}
		try{
			long now = System.currentTimeMillis();
			org.apache.rocketmq.common.message.Message rocketMsg = this.createRocketMqMessage(destination, message);
			if(delayLevel > 0){
				rocketMsg.setDelayTimeLevel(delayLevel);
			}
			SendResult sendResult = producer.send(rocketMsg, timeout);
			long costTime = System.currentTimeMillis() - now;
			if(logger.isDebugEnabled()){
				logger.debug("send message cost: {} ms, msgId:{}", costTime, sendResult.getMsgId());
			}
			return sendResult;
		}catch(Exception e){
			logger.error("syncSend failed. destination:{}, message:{}, detail exception info: ", destination, message,
					e);
			throw new MessagingException(e.getMessage(), e);
		}
	}

	/**
	 * Same to {@link #syncSend(String, Message)}.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param payload
	 * 		the Object to use as payload
	 *
	 * @return {@link SendResult}
	 */
	public SendResult syncSend(String destination, Object payload) {
		return syncSend(destination, payload, producer.getSendMsgTimeout());
	}

	/**
	 * Same to {@link #syncSend(String, Object)} with send timeout specified in addition.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param payload
	 * 		the Object to use as payload
	 * @param timeout
	 * 		send timeout with millis
	 *
	 * @return {@link SendResult}
	 */
	public SendResult syncSend(String destination, Object payload, long timeout) {
		Message<?> message = MessageBuilder.withPayload(payload).build();
		return syncSend(destination, message, timeout);
	}

	/**
	 * Same to {@link #asyncSend(String, Message, SendCallback)} with send timeout and delay level specified in
	 * addition.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param message
	 *        {@link Message}
	 * @param sendCallback
	 *        {@link SendCallback}
	 * @param timeout
	 * 		send timeout with millis
	 * @param delayLevel
	 * 		level for the delay message
	 */
	public void asyncSend(String destination, Message<?> message, SendCallback sendCallback, long timeout,
	                      int delayLevel) {
		if(Objects.isNull(message) || Objects.isNull(message.getPayload())){
			logger.error("asyncSend failed. destination:{}, message is null ", destination);
			throw new IllegalArgumentException("`message` and `message.payload` cannot be null");
		}
		try{
			org.apache.rocketmq.common.message.Message rocketMsg = this.createRocketMqMessage(destination, message);
			if(delayLevel > 0){
				rocketMsg.setDelayTimeLevel(delayLevel);
			}
			producer.send(rocketMsg, sendCallback, timeout);
		}catch(Exception e){
			logger.info("asyncSend failed. destination:{}, message:{} ", destination, message);
			throw new MessagingException(e.getMessage(), e);
		}
	}

	/**
	 * Same to {@link #asyncSend(String, Message, SendCallback)} with send timeout specified in addition.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param message
	 *        {@link Message}
	 * @param sendCallback
	 *        {@link SendCallback}
	 * @param timeout
	 * 		send timeout with millis
	 */
	public void asyncSend(String destination, Message<?> message, SendCallback sendCallback, long timeout) {
		asyncSend(destination, message, sendCallback, timeout, 0);
	}

	/**
	 * <p> Send message to broker asynchronously. asynchronous transmission is generally used in response time
	 * sensitive business scenarios. </p>
	 * <p>
	 * This method returns immediately. On sending completion, <code>sendCallback</code> will be executed.
	 * <p>
	 * Similar to {@link #syncSend(String, Object)}, internal implementation would potentially retry up to {@link
	 * DefaultMQProducer#getRetryTimesWhenSendAsyncFailed} times before claiming sending failure, which may yield
	 * message duplication and application developers are the one to resolve this potential issue.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param message
	 *        {@link Message}
	 * @param sendCallback
	 *        {@link SendCallback}
	 */
	public void asyncSend(String destination, Message<?> message, SendCallback sendCallback) {
		asyncSend(destination, message, sendCallback, producer.getSendMsgTimeout());
	}

	/**
	 * Same to {@link #asyncSend(String, Object, SendCallback)} with send timeout specified in addition.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param payload
	 * 		the Object to use as payload
	 * @param sendCallback
	 *        {@link SendCallback}
	 * @param timeout
	 * 		send timeout with millis
	 */
	public void asyncSend(String destination, Object payload, SendCallback sendCallback, long timeout) {
		Message<?> message = doConvert(payload, null, null);
		asyncSend(destination, message, sendCallback, timeout);
	}

	/**
	 * Same to {@link #asyncSend(String, Message, SendCallback)}.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param payload
	 * 		the Object to use as payload
	 * @param sendCallback
	 *        {@link SendCallback}
	 */
	public void asyncSend(String destination, Object payload, SendCallback sendCallback) {
		asyncSend(destination, payload, sendCallback, producer.getSendMsgTimeout());
	}

	/**
	 * asyncSend batch messages
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param messages
	 * 		Collection of {@link Message}
	 * @param sendCallback
	 *        {@link SendCallback}
	 */
	public <T extends Message> void asyncSend(String destination, Collection<T> messages, SendCallback sendCallback) {
		asyncSend(destination, messages, sendCallback, producer.getSendMsgTimeout());
	}

	/**
	 * asyncSend batch messages in a given timeout.
	 *
	 * @param destination
	 * 		formats: `topicName:tags`
	 * @param messages
	 * 		Collection of {@link Message}
	 * @param sendCallback
	 *        {@link SendCallback}
	 * @param timeout
	 * 		send timeout with millis
	 */
	public <T extends Message> void asyncSend(String destination, Collection<T> messages, SendCallback sendCallback,
	                                          long timeout) {
		if(Validate.isEmpty(messages)){
			logger.error("asyncSend with batch failed. destination:{}, messages is empty ", destination);
			throw new IllegalArgumentException("`messages` can not be empty");
		}

		try{
			Collection<org.apache.rocketmq.common.message.Message> rmqMsgs = new ArrayList<>();
			for(T msg : messages){
				if(Objects.isNull(msg) || Objects.isNull(msg.getPayload())){
					logger.warn("Found a message empty in the batch, skip it");
					continue;
				}
				rmqMsgs.add(createRocketMqMessage(destination, msg));
			}
			producer.send(rmqMsgs, sendCallback, timeout);
		}catch(Exception e){
			logger.error("asyncSend with batch failed. destination:{}, messages.size:{} ", destination,
					messages.size());
			throw new MessagingException(e.getMessage(), e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(producer != null){
			producer.start();
		}
	}

	@Override
	public void destroy() {
		if(Objects.nonNull(producer)){
			producer.shutdown();
		}
	}

	@Override
	protected void doSend(String destination, Message<?> message) {
		SendResult sendResult = syncSend(destination, message);
		if(logger.isDebugEnabled()){
			logger.debug("send message to `{}` finished. result:{}", destination, sendResult);
		}
	}

	@Override
	protected Message<?> doConvert(Object payload, Map<String, Object> headers, MessagePostProcessor postProcessor) {
		Message<?> message = super.doConvert(payload, headers, postProcessor);
		MessageBuilder<?> builder = MessageBuilder.fromMessage(message);
		builder.setHeaderIfAbsent(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN);
		return builder.build();
	}

	private org.apache.rocketmq.common.message.Message createRocketMqMessage(
			String destination, Message<?> message) {
		Message<?> msg = this.doConvert(message.getPayload(), message.getHeaders(), null);
		return convertToRocketMessage(getMessageConverter(), destination, msg);
	}

	private org.apache.rocketmq.common.message.Message convertToRocketMessage(
			MessageConverter messageConverter, String destination, org.springframework.messaging.Message<?> message) {
		Object payloadObj = message.getPayload();
		byte[] payloads;
		if(null == payloadObj){
			throw new RuntimeException("the message cannot be empty");
		}
		
		try{
			if(payloadObj instanceof String){
				payloads = ((String) payloadObj).getBytes(getCharset());
			}else if(payloadObj instanceof byte[]){
				payloads = (byte[]) message.getPayload();
			}else{
				String jsonObj = (String) messageConverter.fromMessage(message, payloadObj.getClass());
				if(null == jsonObj){
					throw new RuntimeException(String.format(
							"empty after conversion [messageConverter:%s,payloadClass:%s,payloadObj:%s]",
							messageConverter.getClass(), payloadObj.getClass(), payloadObj));
				}
				payloads = jsonObj.getBytes(getCharset());
			}
		}catch(Exception e){
			throw new RuntimeException("convert to RocketMQ message failed.", e);
		}
		return getAndWrapMessage(destination, message.getHeaders(), payloads);
	}

	private static org.apache.rocketmq.common.message.Message getAndWrapMessage(String destination,
	                                                                            MessageHeaders headers,
	                                                                            byte[] payloads) {
		if(Validate.isBlank(destination)){
			return null;
		}
		if(payloads == null || payloads.length < 1){
			return null;
		}
		String[] tempArr = destination.split(":", 2);
		String topic = tempArr[0];
		String tags = "";
		if(tempArr.length > 1){
			tags = tempArr[1];
		}
		org.apache.rocketmq.common.message.Message rocketMsg = new org.apache.rocketmq.common.message.Message(topic,
				tags, payloads);
		if(Objects.nonNull(headers) && !headers.isEmpty()){
			Object keys = headers.get(RocketMQHeaders.KEYS);
			// if headers not have 'KEYS', try add prefix when getting keys
			if(ObjectUtils.isEmpty(keys)){
				keys = headers.get(RocketMQHeaders.PREFIX + RocketMQHeaders.KEYS);
			}
			if(!ObjectUtils.isEmpty(keys)){ // if headers has 'KEYS', set rocketMQ message key
				rocketMsg.setKeys(keys.toString());
			}
			Object flagObj = headers.getOrDefault("FLAG", "0");
			int flag = 0;
			try{
				flag = Integer.parseInt(flagObj.toString());
			}catch(NumberFormatException e){
				// Ignore it
				if(logger.isInfoEnabled()){
					logger.info("flag must be integer, flagObj:{}", flagObj);
				}
			}
			rocketMsg.setFlag(flag);
			Object waitStoreMsgOkObj = headers.getOrDefault("WAIT_STORE_MSG_OK", "true");
			rocketMsg.setWaitStoreMsgOK(waitStoreMsgOkObj.equals("false") == false);
			headers.entrySet().stream()
					.filter(entry->!Objects.equals(entry.getKey(), "FLAG")
							&& Objects.equals(entry.getKey(), "WAIT_STORE_MSG_OK") == false) // exclude "FLAG",
					// "WAIT_STORE_MSG_OK"
					.forEach(entry->{
						if(!MessageConst.STRING_HASH_SET.contains(entry.getKey())){
							rocketMsg.putUserProperty(entry.getKey(), String.valueOf(entry.getValue()));
						}
					});
		}
		return rocketMsg;
	}

}