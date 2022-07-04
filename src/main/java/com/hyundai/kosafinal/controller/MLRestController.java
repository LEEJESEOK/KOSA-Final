package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.VipDTO;
import com.hyundai.kosafinal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ml2")
public class MLRestController {
    @Autowired
    MemberService service;

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


    @RequestMapping(value = "/vip", method = RequestMethod.POST)
    public Map<String, Object> vip(@RequestBody Map<String, Object> params) {
        VipDTO vip = new VipDTO();
        Map<String, Object> result = new HashMap<String, Object>();
        vip = service.findByEmail2((String) params.get("email"));
        System.out.println("고생했다.");
        int cnt_1 = vip.getCnt_1();
        int period = vip.getPeriod();
        int flag = vip.getFlag();

        result.put("cnt_1", cnt_1);
        result.put("period", period);
        result.put("flag", flag);

        return result;
    }

}

