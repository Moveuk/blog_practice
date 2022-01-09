<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id }" />
		<div class="form-group">
			<label for="username">Username :</label>
			<input value="${principal.user.username }" type="text" class="form-control" placeholder="Enter username"
				id="username" readonly="readonly">
		</div>
		<c:choose>
			<c:when test="${empty principal.user.oauth }">
				<div class="form-group">
					<label for="pwd">Password :</label>
					<input type="password" class="form-control" placeholder="Enter password" id="password">
				</div>
				<div class="form-group">
					<label for="email">Email :</label>
					<input value="${principal.user.email }" type="email" class="form-control" placeholder="Enter email" id="email">
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="email">Email :</label>
					<input value="${principal.user.email }" type="email" class="form-control" placeholder="Enter email" id="email"
						readonly>
				</div>
			</c:otherwise>
		</c:choose>
	</form>
	<button id="btn-update" class="btn btn-primary">회원 정보 수정</button>
</div>

<script type="text/javascript" src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>