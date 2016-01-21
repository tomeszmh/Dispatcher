package com.carussel.dispatcher.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AccessLog.class)
public abstract class AccessLog_ {

	public static volatile SingularAttribute<AccessLog, String> referer;
	public static volatile SingularAttribute<AccessLog, String> hostName;
	public static volatile SingularAttribute<AccessLog, String> method;
	public static volatile SingularAttribute<AccessLog, String> module;
	public static volatile SingularAttribute<AccessLog, Long> count;
	public static volatile SingularAttribute<AccessLog, String> userAgent;
	public static volatile SingularAttribute<AccessLog, String> url;
	public static volatile SingularAttribute<AccessLog, String> protocol;
	public static volatile SingularAttribute<AccessLog, String> system;
	public static volatile SingularAttribute<AccessLog, Long> size;
	public static volatile SingularAttribute<AccessLog, String> clientIp;
	public static volatile SingularAttribute<AccessLog, Date> logdate;
	public static volatile SingularAttribute<AccessLog, String> host;
	public static volatile SingularAttribute<AccessLog, Long> id;
	public static volatile SingularAttribute<AccessLog, Integer> statusCode;

}

