package com.carussel.dispatcher.scheduler;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.carussel.dispatcher.entity.AccessLog;
import com.carussel.dispatcher.entity.AccessLog_;
import com.carussel.dispatcher.entity.ErrorLog;
import com.carussel.dispatcher.entity.ErrorLog_;

@Component
public class LogRotator {
	private static final Logger logger = LoggerFactory.getLogger(LogRotator.class);

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	@Scheduled(cron = "0 0 2 * * *")
	public void reportCurrentTime() {
		logger.info("Logrotator scheduler start at: " + new Date().toString());
		Date date = LocalDate.now().minusDays(7).toDateTimeAtStartOfDay().toDate();

		deleteAccessLogLessThenDate(date);
		deleteErrorLogLessThenDate(date);
		logger.info("Logrotator scheduler ended at: " + new Date().toString());
	}

	public void deleteAccessLogLessThenDate(Date date) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<AccessLog> query = criteriaBuilder.createCriteriaDelete(AccessLog.class);

		Root<AccessLog> root = query.from(AccessLog.class);
		Predicate isLessDate = criteriaBuilder.lessThan(root.get(AccessLog_.logdate), date);

		query.where(isLessDate);
		entityManager.createQuery(query).executeUpdate();
		logger.info("Deleted log records from the table access_log before the date: " + date.toString());
	}

	public void deleteErrorLogLessThenDate(Date date) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<ErrorLog> query = criteriaBuilder.createCriteriaDelete(ErrorLog.class);

		Root<ErrorLog> root = query.from(ErrorLog.class);
		Predicate isLessDate = criteriaBuilder.lessThan(root.get(ErrorLog_.logDate), date);

		query.where(isLessDate);
		entityManager.createQuery(query).executeUpdate();
		logger.info("Deleted log records from the table error_log before the date: " + date.toString());
	}
}