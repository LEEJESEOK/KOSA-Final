package com.hyundai.kosafinal.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.kosafinal.domain.PageDTO;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.domain.SelectProductReviewCriteria;
import com.hyundai.kosafinal.service.MemberService;
import com.hyundai.kosafinal.service.ProductReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ProductReviewController {

    private ProductReviewService productReviewService;

    private MemberService memberService;

    @Value("${s3.bucket.address}")
    private String S3_BUCKET_ADDRESS;

    public ProductReviewController(ProductReviewService productReviewService, MemberService memberService) {
        this.productReviewService = productReviewService;
        this.memberService = memberService;
    }

    @PostMapping("/product_review/{productId}")
    @ResponseBody
    public Map<String, Object> getProductReviewByProductId(@PathVariable("productId") String productId, @RequestBody SelectProductReviewCriteria selectProductReviewCriteria) {
        Map<String, Object> map = new HashMap<>();
        selectProductReviewCriteria.setProductId(productId);
        int reviewCnt = productReviewService.getProductReviewCountById(selectProductReviewCriteria);

        List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(selectProductReviewCriteria);
        for (ProductReviewDTO p : list) {

            //사용자 등급 조회
            String grade = changUserGradeForm(memberService.findGrade(p.getEmail()));
            p.setGrade(grade);

            String s1 = p.getEmail().substring(0, 3);
            String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
            p.setEmail(s1 + s2);
            if (p.getImgURI() == null || p.getImgURI().equals("")) {
                continue;
            }
            p.setImgURI(S3_BUCKET_ADDRESS + p.getImgURI());
        }

        PageDTO pageDTO = new PageDTO(selectProductReviewCriteria, reviewCnt);
        map.put("page", pageDTO);
        map.put("list", list);
        return map;
    }

    @PostMapping("/product_review/{productId}/image")
    @ResponseBody
    public Map<String, Object> getProductReviewByProductIdAndImage(@PathVariable("productId") String productId, @RequestBody SelectProductReviewCriteria selectProductReviewCriteria) {
        Map<String, Object> map = new HashMap<>();
        selectProductReviewCriteria.setProductId(productId);
        int reviewCnt = productReviewService.getProductReviewCountByIdAndImg(selectProductReviewCriteria);

        List<ProductReviewDTO> list = productReviewService.getProductReviewByProductIdAndImg(selectProductReviewCriteria);
        List<ProductReviewDTO> imageReviewList = new ArrayList<>();

        for (ProductReviewDTO p : list) {

            //사용자 등급 조회
            String grade = changUserGradeForm(memberService.findGrade(p.getEmail()));
            p.setGrade(grade);

            System.out.println("일반 DTO : " + p);
            String s1 = p.getEmail().substring(0, 3);
            String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
            p.setEmail(s1 + s2);

            if (p.getImgURI() == null || p.getImgURI().equals("")) {
                continue;
            }
            p.setImgURI(S3_BUCKET_ADDRESS + p.getImgURI());
            imageReviewList.add(p);
            System.out.println("이미지 추가 할 수 있는 DTO : " + p);
        }

        PageDTO pageDTO = new PageDTO(selectProductReviewCriteria, reviewCnt);
        map.put("page", pageDTO);
        map.put("list", imageReviewList);
        return map;
    }

    @PostMapping("/product_review/{productId}/text")
    @ResponseBody
    public Map<String, Object> getProductReviewByProductIdAndText(@PathVariable("productId") String productId, @RequestBody SelectProductReviewCriteria selectProductReviewCriteria) {
        Map<String, Object> map = new HashMap<>();
        selectProductReviewCriteria.setProductId(productId);
        int reviewCnt = productReviewService.getProductReviewCountByIdAndText(selectProductReviewCriteria);

        List<ProductReviewDTO> list = productReviewService.getProductReviewByProductIdAndText(selectProductReviewCriteria);
        List<ProductReviewDTO> textReviewList = new ArrayList<>();
        for (ProductReviewDTO p : list) {
            //사용자 등급 조회
            String grade = changUserGradeForm(memberService.findGrade(p.getEmail()));
            p.setGrade(grade);

            String s1 = p.getEmail().substring(0, 3);
            String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
            p.setEmail(s1 + s2);
            if (p.getImgURI() == null || p.getImgURI().equals("")) {
                textReviewList.add(p);
                System.out.println(p.toString());
            }
        }

        PageDTO pageDTO = new PageDTO(selectProductReviewCriteria, reviewCnt);
        map.put("page", pageDTO);
        map.put("list", textReviewList);
        return map;
    }

    @PostMapping("/product_review")
    public ProductReviewDTO saveProductReview(@RequestBody ProductReviewDTO productReviewDTO, @RequestPart(required = false) MultipartFile imgFile) throws JSONException, IOException {
        log.info("리뷰 입력 창 ==========================");
        log.info("리뷰 입력 정보 : " + productReviewDTO.toString());
        log.info("이미지 파일 정보 : " + imgFile.getOriginalFilename());

        productReviewService.saveProductReview(productReviewDTO, imgFile);

        return productReviewDTO;
    }

    @PostMapping("/product_review/insert")
    public ProductReviewDTO sProductReview(MultipartHttpServletRequest request) throws JSONException, IOException {
        log.info("리뷰 입력 창 ==========================");
        log.info("리뷰 입력 정보 : " + request.toString());
        System.out.println("리뷰삽입");
        ProductReviewDTO productReviewDTO = ProductReviewDTO.builder().email(request.getParameter("email")).productId(request.getParameter("productId")).content(request.getParameter("content")).rate(Integer.parseInt(request.getParameter("rate"))).age(request.getParameter("age")).height(request.getParameter("height")).bodyType(request.getParameter("bodyType")).editDate(Date.valueOf(LocalDate.now())).productSize(request.getParameter("productSize")).productColorId(request.getParameter("productColorId")).ctryLarge(request.getParameter("ctryLarge")).ctryMedium(request.getParameter("ctryMedium")).ctrySmall(request.getParameter("ctrySmall")).title(request.getParameter("title")).avgSize(request.getParameter("avgSize")).build();

        List<MultipartFile> fileList = new ArrayList<MultipartFile>();

        // input file이 있을때(파일을 업로드 했을때 처리)
        if (!request.getFiles("imgFIle").isEmpty() && request.getFiles("imgFIle").get(0).getSize() != 0) {
            fileList = request.getFiles("imgFIle");
            log.info("이미지 파일 정보 : " + fileList.get(0).getOriginalFilename());
            log.info("감성 분석을 진행합니다(파일없을 때) ===================");


            JSONObject json = new JSONObject();

            json.put("content", productReviewDTO.getContent());

            String inputLine = null;
            StringBuffer stringBuffer = new StringBuffer();

            URL url = new URL("http://112.221.225.165:40101/review"); //URL객체 생성
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
            System.out.println("데이터 값" + json.toString());

            //전송된 결과를 읽어옴
            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            System.out.println("114줄입니다.");
            while ((inputLine = bReader.readLine()) != null) {
                stringBuffer.append(inputLine);
                System.out.println("while문1" + stringBuffer); //stringBuffer에 데이터 담겨있음
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(stringBuffer.toString());
            System.out.println(stringBuffer.toString().getClass().getName());
            Map<String, Object> resultMap = objectMapper.treeToValue(jsonNode, Map.class);
            double sentiment_percent = (double) resultMap.get("sentiment_percent");
            if (sentiment_percent > 0.75) {
                productReviewDTO.setSentiment_percent(sentiment_percent * 100);
                productReviewDTO.setSentiment_type("긍정");
            }
            else if(sentiment_percent>0.45 && sentiment_percent<0.75) {
                productReviewDTO.setSentiment_percent(sentiment_percent * 100);
                productReviewDTO.setSentiment_type("보통");
            }
            else if(sentiment_percent<0.45) {
                productReviewDTO.setSentiment_percent((1 - sentiment_percent) * 100);
                productReviewDTO.setSentiment_type("부정");
            }
            productReviewService.saveProductReview(productReviewDTO, fileList.get(0));

            System.out.println(stringBuffer);
            System.out.println(stringBuffer.getClass().getName());
            bWriter.close();
            bReader.close();
            conn.disconnect();
            return productReviewDTO;
        }
//else문
        log.info("감성 분석을 진행합니다(파일 없을 때) ===================");
        System.out.println("251");

        JSONObject json = new JSONObject();

        json.put("content", productReviewDTO.getContent());
        System.out.println(json);

        String inputLine = null;
        StringBuffer stringBuffer = new StringBuffer();

        URL url = new URL("http://112.221.225.165:40101/review"); //URL객체 생성
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
        System.out.println("데이터 전송값 파이썬으로 전송 :" + bWriter);
        bWriter.flush();
        System.out.println("Spring -> Flask 데이터 전송 성공!");
        System.out.println("데이터 값" + json.toString());

        //전송된 결과를 읽어옴
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        System.out.println("114줄입니다.");
        while ((inputLine = bReader.readLine()) != null) {
            stringBuffer.append(inputLine);

        }
        System.out.println("전송된 결과를 읽어옴" + stringBuffer.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringBuffer.toString());


        Map<String, Object> resultMap = objectMapper.treeToValue(jsonNode, Map.class);

        double sentiment_percent = (double) resultMap.get("sentiment_percent");

        System.out.println("sentiment_percent: " + sentiment_percent);
        // TODO sentiment_percent int로 변경
        if (sentiment_percent > 0.75) {
            productReviewDTO.setSentiment_percent(sentiment_percent * 100);
            productReviewDTO.setSentiment_type("긍정");
        }
        else if(sentiment_percent>0.45 && sentiment_percent<0.75) {
            productReviewDTO.setSentiment_percent(sentiment_percent * 100);
            productReviewDTO.setSentiment_type("보통");
        }
        else if(sentiment_percent<0.45) {
            productReviewDTO.setSentiment_percent((1 - sentiment_percent) * 100);
            productReviewDTO.setSentiment_type("부정");
        }

        // TODO sp_review insert
        productReviewService.saveProductReview(productReviewDTO, null);
        bWriter.close();
        bReader.close();
        conn.disconnect();
        return productReviewDTO;
    }

    public String changUserGradeForm(String num) {
        String s = "";
        switch (num) {
            case "1":
                s = "FRIEND";
                break;
            case "2":
                s = "CREW";
                break;
            case "3":
                s = "MANIA";
                break;
            case "4":
                s = "STAR";
                break;
            case "5":
                s = "THE STAR";
                break;
        }
        return s;
    }

    @PostMapping("/product_review/rate/sentiment")
    public ResponseEntity<Map<String, Object>> getProductReviewSentimentRate(@RequestBody Map<String, Object> requestMap) {

        String id = (String) requestMap.get("id");
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<ProductReviewDTO> reviewList = productReviewService.getProductReviewById(id);

        int positive = 0, normal = 0, negative = 0;
        for (ProductReviewDTO productReview : reviewList) {
            switch (productReview.getSentiment_type()) {
                case "긍정":
                    positive++;
                    break;
                case "부정":
                    negative++;
                    break;
                case "보통":
                    normal++;
                    break;
            }
        }

        Map<String, Integer> sentimentRateMap = new HashMap<>();
        sentimentRateMap.put("positive", positive);
        sentimentRateMap.put("normal", normal);
        sentimentRateMap.put("negative", negative);


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("data", sentimentRateMap);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
