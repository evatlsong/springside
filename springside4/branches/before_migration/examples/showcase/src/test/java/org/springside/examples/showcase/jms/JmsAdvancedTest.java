package org.springside.examples.showcase.jms;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.advanced.AdvancedNotifyMessageListener;
import org.springside.examples.showcase.jms.advanced.AdvancedNotifyMessageProducer;
import org.springside.modules.log.MockLog4jAppender;
import org.springside.modules.test.spring.SpringContextTestCase;
import org.springside.modules.utils.Threads;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-test.xml", "/jms/applicationContext-jms-advanced.xml" })
public class JmsAdvancedTest extends SpringContextTestCase {

	@Autowired
	private AdvancedNotifyMessageProducer notifyMessageProducer;

	@Resource
	private JmsTemplate advancedJmsTemplate;

	@Resource
	private Destination advancedNotifyTopic;

	@Test
	public void queueMessage() {
		Threads.sleep(1000);
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendQueue(user);
		Threads.sleep(1000);
		assertNotNull(appender.getFirstLog());
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn, ObjectType:user", appender.getFirstLog()
				.getMessage());
	}

	@Test
	public void topicMessage() {
		Threads.sleep(1000);
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendTopic(user);
		Threads.sleep(2000);
		assertNotNull(appender.getFirstLog());
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn, ObjectType:user", appender.getFirstLog()
				.getMessage());
	}

	@Test
	public void topicMessageWithWrongType() {
		Threads.sleep(1000);
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		advancedJmsTemplate.send(advancedNotifyTopic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {

				MapMessage message = session.createMapMessage();
				message.setStringProperty("objectType", "role");
				return message;
			}
		});

		Threads.sleep(1000);
		assertTrue(appender.getAllLogs().isEmpty());
	}
}
