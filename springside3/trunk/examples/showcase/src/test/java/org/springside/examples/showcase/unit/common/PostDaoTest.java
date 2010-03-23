package org.springside.examples.showcase.unit.common;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.dao.ReplyDao;
import org.springside.examples.showcase.common.dao.SubjectDao;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.Reply;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.examples.showcase.common.entity.User;

/**
 * PostDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
public class PostDaoTest extends BaseTxTestCase {

	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private UserDao userDao;

	@Test
	public void getSubjectDetail() {
		Subject subject = subjectDao.getDetailWithReply(1L);
		subjectDao.getSession().evict(subject);

		assertEquals(1, subject.getReplyList().size());
		assertEquals("Hello World!!", subject.getContent());
		assertEquals("Good Morning!!", subject.getReplyList().get(0).getContent());
	}

	@Test
	public void createSubject() {
		Subject subject = new Subject();
		subject.setTitle("Good Night");
		subject.setContent("Good Night!!");
		subject.setModifyTime(new Date());

		User user = userDao.get(1L);
		subject.setUser(user);

		subjectDao.save(subject);
		subjectDao.flush();
		subject = subjectDao.getDetail(subject.getId());
		assertEquals("Good Night!!", subject.getContent());
	}

	@Test
	public void updateSubject() {
		Subject subject = subjectDao.getDetail(1L);
		subject.setTitle("Good Afternoon");
		subject.setContent("Good Afternoon!!!");
		subject.setModifyTime(new Date());

		subjectDao.save(subject);
		subjectDao.flush();
		subject = subjectDao.getDetail(subject.getId());
		assertEquals("Good Afternoon!!!", subject.getContent());
	}

	@Test
	public void deleteSubject() {
		subjectDao.delete(1L);
		subjectDao.flush();
		Subject subject = subjectDao.findUniqueBy("id", 1L);
		assertNull(subject);
	}

	@Test
	public void createReply() {
		Reply reply = new Reply();
		reply.setTitle("GoodAfternoon");
		reply.setContent("Good Afternoon!!!");
		reply.setModifyTime(new Date());

		User user = userDao.get(1L);
		reply.setUser(user);

		Subject subject = subjectDao.get(1L);
		reply.setSubject(subject);

		replyDao.save(reply);
		replyDao.flush();
	}

	@Test
	public void updateReply() {
		Reply reply = replyDao.getDetail(2L);
		reply.setTitle("GoodEvening");
		reply.setContent("Good Evening!!!");

		replyDao.save(reply);
		replyDao.flush();
	}

	@Test
	public void deleteReply() {
		replyDao.delete(2L);
	}
}
