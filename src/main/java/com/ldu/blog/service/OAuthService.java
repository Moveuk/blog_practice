package com.ldu.blog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldu.blog.model.KakaoProfile;
import com.ldu.blog.model.OAuthToken;
import com.ldu.blog.model.User;

import lombok.RequiredArgsConstructor;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
@RequiredArgsConstructor
public class OAuthService {

	@Value("${oAuth.key}") // application.yml 에서 정의해놓은 oAuth 용 키
	private String oAuthKey;
	@Value("${oAuth.kakao.requestToken.grantType}")
	private String kakaoGrantType;	
	@Value("${oAuth.kakao.requestToken.clientId}")
	private String kakaoClientId;	
	@Value("${oAuth.kakao.requestToken.redirectUri}")
	private String kakaoRedirectUri;
	@Value("${oAuth.kakao.requestToken.uri}")
	private String kakaorequestTokenUri;
	@Value("${oAuth.kakao.requestProfile.uri}")
	private String kakaoRequestProfileUri;

	public User 카카오로그인요청(String code){
		
		// Post 방식으로 key=value 데이터를 요청 (카카오 서버로)
		RestTemplate rt = new RestTemplate();

		// HttpHeaders 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", kakaoGrantType);
		params.add("client_id", kakaoClientId);
		params.add("redirect_uri", kakaoRedirectUri);
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트로 담기( 다음줄의 exchange가 파라미터로 HttpEntity를 요구함)
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답을 받음.
		ResponseEntity<String> response = rt.exchange(kakaorequestTokenUri, HttpMethod.POST,
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

		// Post 방식으로 key=value 데이터를 요청 (카카오 서버로)
		RestTemplate rt2 = new RestTemplate();

		// HttpHeaders 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트로 담기( 다음줄의 exchange가 파라미터로 HttpEntity를 요구함)
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답을 받음.
		ResponseEntity<String> response2 = rt2.exchange(kakaoRequestProfileUri, HttpMethod.POST,
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
				.oauth("kakao")
				.build();
		
		return kakaoUser;
	}



}
