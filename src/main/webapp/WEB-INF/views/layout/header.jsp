<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>이동욱 포트폴리오</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<a class="navbar-brand" href="/blog">이동욱 블로그</a>
		<%-- 오른쪽 햄버거 바(반응형임 세로로 길어지면 열고 닫을 수 있도록) --%>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav">
				<li class="nav-item">
					<a class="nav-link" href="/blog/user/loginForm">로그인</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/blog/user/joinForm">회원가입</a>
			</ul>
		</div>
	</nav>
	<br />
	<%-- left bar
		<div class="card m-2" style="width: 400px">
			<img class="card-img-top" src="img_avatar1.png" alt="Card image">
			<div class="card-body">
				<h4 class="card-title">John Doe</h4>
				<p class="card-text">Some example text.</p>
				<a href="#" class="btn btn-primary">See Profile</a>
			</div>
		</div>
	 --%>

	<%-- script -> 인터프리터 언어 맨마지막에 하는게 좋으며 제이쿼리를 쓰기 위하여 header에 둠. --%>
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>