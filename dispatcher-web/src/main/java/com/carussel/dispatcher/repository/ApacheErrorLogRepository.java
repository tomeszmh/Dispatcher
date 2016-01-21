package com.carussel.dispatcher.repository;

import org.springframework.data.repository.CrudRepository;

import com.carussel.dispatcher.entity.ErrorLog;

public interface ApacheErrorLogRepository extends CrudRepository<ErrorLog, Long> {

}
