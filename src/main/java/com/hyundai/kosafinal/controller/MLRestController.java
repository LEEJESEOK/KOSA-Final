package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.LogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
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
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView Test() {
        ModelAndView mav = new ModelAndView();

        String url = "http://127.0.0.1:5000/tospring";
        String sb = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            System.out.println("========br======" + sb.toString());
            if (sb.toString().contains("ok")) {
                System.out.println("test");
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mav.addObject("test1", sb.toString()); // "test1"는 jsp파일에서 받을때 이름,


        //sb.toString은 value값(여기에선 test)
        mav.addObject("fail", false);
        mav.setViewName("test");   // jsp파일 이름
        return mav;
    }


    @RequestMapping(value = "/secession", method = RequestMethod.POST)
    public boolean vip(@RequestBody Map<String, Object> params) throws JSONException, IOException {
        MemberDTO member = new MemberDTO();
        LogDTO log = new LogDTO();

        List<MemberDTO> ad = mservice.findbyMember((String) params.get("email"));
        List<LogDTO> result = mservice.findbyLog((String) params.get("email"));
        JSONArray jsonArray = new JSONArray();




        for (LogDTO data : result) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            JSONObject fjson = new JSONObject();
            fjson.put("LOG_ID", data.getLog_id());
            fjson.put("CUSTOMER_ID", data.getCustomer_id());
            fjson.put("USEDATE", format.format(data.getUsedate().getTime()));
            jsonArray.put(fjson);
        }
        JSONObject json = new JSONObject();
        json.put("result", jsonArray);


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

        URL url = new URL("http://localhost:5000/vip"); //URL객체 생성

        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url주소를 가지고 Http 커넥션 객체 생성

        System.out.println(conn.toString());
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

//    @RequestMapping(value = "/review", method = RequestMethod.POST)
//    public String review(@RequestBody Map<String, Object> params) throws JSONException, IOException {
//        ProductReviewDTO reviewDTO=new ProductReviewDTO();
//        List<ProductReviewDTO> re = rservice.getProductReviewByProductId((String) params.get("product_id"));
//        System.out.println("re"+re);
//
//
//        JSONObject json = new JSONObject();
//        for (ProductReviewDTO data : re) {
//            json.put("content", data.getContent());
//            System.out.println("json"+json);
//
//        }
//
//
//
//        System.out.println(json);
//
//        String inputLine = null;
//        StringBuffer stringBuffer = new StringBuffer();
//
//        URL url = new URL("http://localhost:5000/review"); //URL객체 생성
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url주소를 가지고 Http 커넥션 객체 생성
//
//        System.out.println(conn.toString());
//        conn.setDoOutput(true);
//        conn.setDoInput(true);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json");
//        conn.setRequestProperty("Accept-Charset", "UTF-8");
//        conn.setConnectTimeout(10000);
//        conn.setReadTimeout(10000);
//
//
//        //데이터 전송
//        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//
//        bWriter.write(json.toString());
//        bWriter.flush();
//        System.out.println("Spring -> Flask 데이터 전송 성공!");
//        System.out.println("데이터 값" +json.toString());
//
//        //전송된 결과를 읽어옴
//        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        System.out.println("114줄입니다.");
//        while ((inputLine = bReader.readLine()) != null) {
//            stringBuffer.append(inputLine);
//            System.out.println("while문" + stringBuffer); //stringBuffer에 데이터 담겨있음
//        }
//
//        System.out.println(stringBuffer);
//        System.out.println(stringBuffer.getClass().getName());
//        bWriter.close();
//        bReader.close();
//        conn.disconnect();
//
//
//        return "1";
//    }

}

