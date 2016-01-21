package com.carussel.dispatcher.repositoryimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.carussel.dispatcher.entity.AccessLog;
import com.carussel.dispatcher.entity.AccessLog_;
import com.carussel.dispatcher.entity.ErrorLog;
import com.carussel.dispatcher.entity.ErrorLog_;

@Repository
public class RepositoryImpl {
	private static final Logger logger = LoggerFactory.getLogger(RepositoryImpl.class);
	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public List<ErrorLog> getTop20ErrorLogRecordsByServerName(String serverName, Date date) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ErrorLog> query = criteriaBuilder.createQuery(ErrorLog.class);

		Root<ErrorLog> root = query.from(ErrorLog.class);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date datePlusOneDay = new Date(cal.getTimeInMillis());
		logger.info(date.toString());
		cal.add(Calendar.DATE, -2);
		date = new Date(cal.getTimeInMillis());
		logger.info(date.toString());

		Predicate datePredicateGreater = criteriaBuilder.greaterThan(root.get(ErrorLog_.logDate), date);
		Predicate datePredicateLess = criteriaBuilder.lessThan(root.get(ErrorLog_.logDate), datePlusOneDay);

		query.multiselect(root.get(ErrorLog_.errorLevel), root.get(ErrorLog_.message), criteriaBuilder.count(root));

		query.groupBy(root.get(ErrorLog_.errorLevel), root.get(ErrorLog_.message));

		query.where(criteriaBuilder.and(datePredicateGreater, datePredicateLess,
				criteriaBuilder.equal(root.get(ErrorLog_.hostName), serverName)));

		query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(root)));

		List<ErrorLog> errorLogList = entityManager.createQuery(query).setMaxResults(20).getResultList();
		logger.info(String.valueOf(errorLogList.size()));
		return errorLogList;

	}

	@Transactional
	public List<AccessLog> getTop20AccessLogRecordsByServerName(String serverName, Date date) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AccessLog> query = criteriaBuilder.createQuery(AccessLog.class);

		Root<AccessLog> root = query.from(AccessLog.class);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date datePlusOneDay = new Date(cal.getTimeInMillis());
		logger.info(date.toString());
		cal.add(Calendar.DATE, -2);
		date = new Date(cal.getTimeInMillis());
		logger.info(date.toString());

		Predicate datePredicateGreater = criteriaBuilder.greaterThan(root.get(AccessLog_.logdate), date);
		Predicate datePredicateLess = criteriaBuilder.lessThan(root.get(AccessLog_.logdate), datePlusOneDay);

		query.multiselect(root.get(AccessLog_.statusCode), root.get(AccessLog_.method), root.get(AccessLog_.url),
				criteriaBuilder.count(root));

		query.groupBy(root.get(AccessLog_.method), root.get(AccessLog_.statusCode), root.get(AccessLog_.url));

		query.where(criteriaBuilder.and(datePredicateGreater, datePredicateLess,
				criteriaBuilder.equal(root.get(AccessLog_.host), serverName)));

		query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(root)));

		return entityManager.createQuery(query).setMaxResults(20).getResultList();
	}

	public List<AccessLog> getServers() {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AccessLog> query = criteriaBuilder.createQuery(AccessLog.class);

		Root<AccessLog> root = query.from(AccessLog.class);
		query.groupBy(root.get(AccessLog_.host));
		query.multiselect(root.get(AccessLog_.host));
		return entityManager.createQuery(query).getResultList();
	}

}