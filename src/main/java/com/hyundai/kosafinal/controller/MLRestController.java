package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.LoginLogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.service.MLService;
import com.hyundai.kosafinal.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class MLRestController {
    @Autowired
    MLService mservice;

    @Autowired
    ProductReviewService rservice;

    @RequestMapping(value = "/secession", method = RequestMethod.POST)
    public boolean vip(@RequestBody Map<String, Object> params) throws JSONException, IOException {
        MemberDTO member = new MemberDTO();
        LoginLogDTO log = new LoginLogDTO();

        List<MemberDTO> ad = mservice.findbyMember((String) params.get("email"));
        List<LoginLogDTO> result = mservice.findbyLog((String) params.get("email"));
        JSONArray jsonArray = new JSONArray();




        for (LoginLogDTO data : result) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            JSONObject fjson = new JSONObject();
            fjson.put("LOG_ID", data.getLog_id());
            fjson.put("CUSTOMER_ID", data.getCustomer_id());
            fjson.put("LOGIN_DATE", format.format(data.getLogin_date().getTime()));
            jsonArray.put(fjson);
        }
        JSONObject json = new JSONObject();
        json.put("result", jsonArray);
        System.out.println("json :" +json);

        jsonArray = new JSONArray();
        for (MemberDTO data : ad) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject fjson = new JSONObject();
            fjson.put("email", data.getEmail());
            fjson.put("sign_up_date", format.format(data.getSignUpDate().getTime()));
            fjson.put("status", data.getStatus());
            if (data.getStatus() == 1)
                fjson.put("end_date", format.format(data.getEnd_date().getTime()));
            jsonArray.put(fjson);
        }

        json.put("member", jsonArray);


        System.out.println(json);


        String inputLine = null;
        StringBuffer stringBuffer = new StringBuffer();

        URL url = new URL("http://112.221.225.165:40101/vip"); //URL객체 생성

        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url주소를 가지고 Http 커넥션 객체 생성

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);


        //데이터 전송
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bWriter.write(json.toString());
        bWriter.flush();
        System.out.println("Spring -> Flask 데이터 전송 성공!");


        //전송된 결과를 읽어옴
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        System.out.println("114줄입니다.");
        while ((inputLine = bReader.readLine()) != null) {
            stringBuffer.append(inputLine);
            System.out.println("while문" + stringBuffer); //stringBuffer에 데이터 담겨있음
        }

        System.out.println(stringBuffer);
        System.out.println(stringBuffer.getClass().getName());
        bWriter.close();
        bReader.close();
        conn.disconnect();

        if(stringBuffer.toString().equals("[0]")){
            return false;
        }

        else{
            System.out.println(stringBuffer);
            return true;
        }

    }//sendJSON()
}

//test1.html ajax 와 소통함.
