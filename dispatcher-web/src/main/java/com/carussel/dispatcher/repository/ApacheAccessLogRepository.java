package com.carussel.dispatcher.repository;

import org.springframework.data.repository.CrudRepository;

import com.carussel.dispatcher.entity.AccessLog;

public interface ApacheAccessLogRepository extends CrudRepository<AccessLog, Long> {

}