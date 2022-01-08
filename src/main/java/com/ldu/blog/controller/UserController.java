package com.ldu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldu.blog.model.KakaoProfile;
import com.ldu.blog.model.OAuthToken;
import com.ldu.blog.model.User;
import com.ldu.blog.service.UserService;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용 - 인증이 없어도 이용 가능한 페이지를 구분.
// 그냥 주소가 / 이면 index.jsp 이용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {

	// application.yml 에서 정의해놓은 oAuth 용 키
	@Value("${oAuth.key}")
	private String oAuthKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {// Data를 리턴해주는 컨트롤러 함수

		// Post 방식으로 key=value 데이터를 요청 (카카오 서버로)
		RestTemplate rt = new RestTemplate();

		// HttpHeaders 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "118f5ed022c828aef7f707a393e2b8b9");
		params.add("redirect_uri", "http://localhost:8282/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트로 담기( 다음줄의 exchange가 파라미터로 HttpEntity를 요구함)
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답을 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// 날라온 토큰(json 형식)을 객체로 받음.
		// Gson, Json Simple, ObjectMapper 등을 사용하여 객체를 받을 수 있음.
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;

		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 액세스 토큰 : " + oAuthToken.getAccess_token());

		// 위 http entity 만들기 다시 반복

		// Post 방식으로 key=value 데이터를 요청 (카카오 서버로)
		RestTemplate rt2 = new RestTemplate();

		// HttpHeaders 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트로 담기( 다음줄의 exchange가 파라미터로 HttpEntity를 요구함)
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답을 받음.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, String.class);

		// 날라온 카카오톡 정보(response2)를 우리가 만든 model 객체로 받음.
		// Gson, Json Simple, ObjectMapper 등을 사용하여 객체를 받을 수 있음.
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;

		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// User 값에 넣어주어야하는 정보들. username, password, email
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

		// 중복 금지를 위한 이메일_아이디
		System.out.println("블로그 서버 유저네임 : "+ kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		// 서비스 내부 oAuth용 Key를 정의
		System.out.println("블로그 서버 패스워드 : "+oAuthKey);

		User kakaoUser = User.builder().
				username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(oAuthKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.build();
		
		// 이미 가입자인지 아닌지 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		// 비가입자면 회원가입
		if(originUser.getUsername() == null) {
			System.out.println("새로운 회원 가입 실행");
			userService.회원가입(kakaoUser);
		}

		// 아니면 로그인 처리
		// 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), oAuthKey));
		SecurityContextHolder.getContext().setAuthentication(authentication); // 시큐리티 컨텍스트에 authentication 등록
		
		return "redirect:/";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

}
