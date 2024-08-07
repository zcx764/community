package com.cx.community.provider;

import com.alibaba.fastjson.JSON;
import com.cx.community.dto.AccessTokenDTO;
import com.cx.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GithubProvider {
    public String getAcessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
          //  System.out.println(JSON.toJSONString(accessTokenDTO));
        //System.out.println(body);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string =  response.body().string();
                String[] split = string.split("&");
                String token = split[0].split("=")[1];
               // System.out.println(string);
                return token;
        } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .header("Authorization","token "+accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            //System.out.println(githubUser);
            return githubUser;
        }
        catch (IOException e){
        }
        return null;
    }
}
