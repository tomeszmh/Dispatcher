<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Log Collector</title>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/custom.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
	
</script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="panel-group" id="accordion">
		<c:forEach var="server" items="${logRecords}" varStatus="item">
			<div class="panel panel-default serverPanel">
				<div class="panel-heading">
					<ul class="nav nav-pills">
						<li role="presentation" class="active"><a
							data-toggle="collapse" data-parent="#accordion"
							href="#${servers.get(item.getIndex()).host} ">
								${servers.get(item.getIndex()).host}</a></li>
					</ul>
				</div>
				<div id="${servers.get(item.getIndex()).host}"
					class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="acc">
							<div class="panel panel-default">
								<div class="panel-heading">
									<ul class="nav nav-pills">
										<li class="active"><a
											href="#accessLog${servers.get(item.getIndex()).host}"
											data-toggle="pill" data-parent="#acc">Access Log</a></li>
										<li><a
											href="#errorLog${servers.get(item.getIndex()).host}"
											data-toggle="pill" data-parent="#acc">Error Log</a></li>
										<li>
									</ul>
								</div>
								<div class="tab-content">
									<input type="text" data-role="date">
									<div id="accessLog${servers.get(item.getIndex()).host}"
										class="tab-pane fade in active">
										<div class="panel-group" id="acc">
											<div class="panel panel-collapse active">
												<div class="panel-heading">
													<ul class="nav nav-pills">
														<c:forEach var="date" items="${dates}" varStatus="loop">
															<li ${loop.getIndex()==0 ? 'class="active"' : ''}><a
																data-toggle="pill"
																href="#dates${servers.get(item.getIndex()).host}${loop.getIndex()}"><fmt:formatDate
																		value="${date}" pattern="yyyy-MM-dd" /></a></li>
														</c:forEach>
													</ul>
												</div>
												<div class="tab-content">
													<c:forEach var="types"
														items="${logRecords.get(item.getIndex()).get(0)}"
														varStatus="innerLoop">
														<div
															${innerLoop.getIndex()==0 ? ' class="tab-pane fade in active"' : 'class="tab-pane fade"'}
															id="dates${servers.get(item.getIndex()).host}${innerLoop.getIndex()}">
															<table class="table table-bordered table-hover logTable">
																<thead>
																	<tr>
																		<td></td>
																		<td>Count</td>
																		<td>Status Code</td>
																		<td>Url</td>
																		<td>Method</td>
																	</tr>
																</thead>
																<c:forEach var="accessLog"
																	items="${logRecords.get(item.getIndex()).get(0).get(innerLoop.getIndex())}"
																	varStatus="accessLogLoop">
																	<tbody>
																		<tr>
																			<td>${accessLogLoop.getIndex()+1}</td>
																			<td>${accessLog.count}</td>
																			<td>${accessLog.statusCode}</td>
																			<td>${accessLog.url}</td>
																			<td>${accessLog.method}</td>
																		</tr>
																	</tbody>
																</c:forEach>
															</table>
														</div>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>

									<div id="errorLog${servers.get(item.getIndex()).host}"
										class="tab-pane fade">
										<div class="panel-group" id="acc">
											<div class="panel panel-collapse active">
												<div class="panel-heading">
													<ul class="nav nav-pills">
														<c:forEach var="date" items="${dates}" varStatus="loop">
															<li ${loop.getIndex()==0 ? 'class="active"' : ''}><a
																data-toggle="pill"
																href="#dateserror${servers.get(item.getIndex()).host}${loop.getIndex()}"><fmt:formatDate
																		value="${date}" pattern="yyyy-MM-dd" /></a></li>
														</c:forEach>
													</ul>
												</div>
												<div class="tab-content">
													<c:forEach var="types"
														items="${logRecords.get(item.getIndex()).get(1)}"
														varStatus="innerLoop">
														<div
															${innerLoop.getIndex()==0 ? ' class="tab-pane fade in active"' : 'class="tab-pane fade"'}
															id="dateserror${servers.get(item.getIndex()).host}${innerLoop.getIndex()}">
															<table class="table table-bordered table-hover logTable">
																<thead>
																	<tr>
																		<td></td>
																		<td>Count</td>
																		<td>Message</td>
																		<td>Level</td>
																	</tr>
																</thead>

																<c:forEach var="accessLog"
																	items="${logRecords.get(item.getIndex()).get(1).get(innerLoop.getIndex())}"
																	varStatus="accessLogLoop">
																	<tbody>
																		<tr>
																			<td>${accessLogLoop.getIndex()+1}</td>
																			<td>${accessLog.count}</td>
																			<td>${accessLog.message}</td>
																			<td>${accessLog.errorLevel}</td>
																		</tr>
																	</tbody>
																</c:forEach>
															</table>
														</div>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>