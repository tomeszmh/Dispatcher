package com.carussel.dispatcher.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.carussel.dispatcher.entity.AccessLog;
import com.carussel.dispatcher.repositoryimpl.RepositoryImpl;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	RepositoryImpl repositoryImpl;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model) {
		ArrayList<ArrayList<ArrayList<Object>>> accessLogList = new ArrayList<ArrayList<ArrayList<Object>>>();
		
		
		ArrayList<Date> weekDates = new ArrayList<Date>();

		ArrayList<String> logTypes = new ArrayList<String>();
		logTypes.add("Access Log");
		logTypes.add("Error Log");
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 10, 7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = new Date(cal.getTimeInMillis());
		int j = 0;
		accessLogList.add(new ArrayList<ArrayList<Object>>());
		accessLogList.add(new ArrayList<ArrayList<Object>>());

		accessLogList.get(0).add(new ArrayList<Object>());
		accessLogList.get(0).add(new ArrayList<Object>());
		accessLogList.get(1).add(new ArrayList<Object>());
		accessLogList.get(1).add(new ArrayList<Object>());
		for (AccessLog serverAccessLog : repositoryImpl.getServers()) {

			cal.set(2015, 10, 7);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			date = new Date(cal.getTimeInMillis());
			for (int i = 0; i < 7; i++) {
				accessLogList.get(j).get(0)
						.add(repositoryImpl.getTop20AccessLogRecordsByServerName(serverAccessLog.getHost(), date));
				accessLogList.get(j).get(1)
						.add(repositoryImpl.getTop20ErrorLogRecordsByServerName(serverAccessLog.getHost(), date));
				if (j == 0)
					weekDates.add(date);

				cal.add(Calendar.DATE, -1);
				date = new Date(cal.getTimeInMillis());
			}
			j++;
		}

		model.addObject("logTypeStrings", logTypes);
		model.addObject("logRecords", accessLogList);
		model.addObject("dates", weekDates);
		model.addObject("servers", repositoryImpl.getServers());

		return model;
	}
}