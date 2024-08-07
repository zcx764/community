package com.cx.community.controller;

import com.cx.community.dto.AccessTokenDTO;
import com.cx.community.dto.GithubUser;
import com.cx.community.mapper.UserMapper;
import com.cx.community.model.User;
import com.cx.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

   @Autowired
   private GithubProvider githubProvider;
   @Value("${github.client.id}")
   private String clientId;
    @Value("${github.client.secret}")
   private String clientSecret;
    @Value("${github.redirect.uri}")
   private String redirectUri;
    @Autowired(required = false)
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String Callback(@RequestParam(name = "code")String code, @RequestParam(name = "state")String state,HttpServletResponse response ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accesstoken = githubProvider.getAcessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accesstoken);
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId());
            user.setGetCreate(System.currentTimeMillis());
            user.setGetModified(user.getGetCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else
            return "redirect:/";


    }
}
