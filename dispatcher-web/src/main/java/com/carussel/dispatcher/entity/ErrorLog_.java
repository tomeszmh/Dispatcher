package com.carussel.dispatcher.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ErrorLog.class)
public abstract class ErrorLog_ {

	public static volatile SingularAttribute<ErrorLog, String> hostName;
	public static volatile SingularAttribute<ErrorLog, String> system;
	public static volatile SingularAttribute<ErrorLog, Date> logDate;
	public static volatile SingularAttribute<ErrorLog, String> module;
	public static volatile SingularAttribute<ErrorLog, String> ipAddress;
	public static volatile SingularAttribute<ErrorLog, Long> count;
	public static volatile SingularAttribute<ErrorLog, Long> id;
	public static volatile SingularAttribute<ErrorLog, String> message;
	public static volatile SingularAttribute<ErrorLog, String> errorLevel;

}

