package com.hyundai.kosafinal.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.service.ProductReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ProductReviewController {

    private ProductReviewService productReviewService;

    @Value("${s3.bucket.address}")
    private String S3_BUCKET_ADDRESS;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @GetMapping("/product_review/{productId}")
    @ResponseBody
    public List<ProductReviewDTO> getProductReviewByProductId(@PathVariable("productId") String productId) {
        List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
        for (ProductReviewDTO p : list) {
            String s1 = p.getEmail().substring(0, 3);
            String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
            p.setEmail(s1 + s2);
            if (p.getImgURI() == null || p.getImgURI().equals("")) {
                continue;
            }
            p.setImgURI(S3_BUCKET_ADDRESS + p.getImgURI());
        }
        return list;
    }

    @GetMapping("/product_review/{productId}/image")
    @ResponseBody
    public List<ProductReviewDTO> getProductReviewByProductIdAndImage(@PathVariable("productId") String productId) {
        List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
        List<ProductReviewDTO> imageReviewList = new ArrayList<>();
        for (ProductReviewDTO p : list) {
            String s1 = p.getEmail().substring(0, 3);
            String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
            p.setEmail(s1 + s2);

            if (p.getImgURI() == null || p.getImgURI().equals("")) {
                continue;
            }
            p.setImgURI("https://kosa-aws-bucket.s3.ap-northeast-2.amazonaws.com/" + p.getImgURI());
            imageReviewList.add(p);
        }
        return imageReviewList;
    }

    @GetMapping("/product_review/{productId}/text")
    @ResponseBody
    public List<ProductReviewDTO> getProductReviewByProductIdAndText(@PathVariable("productId") String productId) {
        List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
        List<ProductReviewDTO> textReviewList = new ArrayList<>();
        for (ProductReviewDTO p : list) {
            String s1 = p.getEmail().substring(0, 3);
            String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
            p.setEmail(s1 + s2);
            if (p.getImgURI() == null || p.getImgURI().equals("")) {
                textReviewList.add(p);
                System.out.println(p.toString());
            }
        }
        return textReviewList;
    }

    @PostMapping("/product_review")
    public ProductReviewDTO saveProductReview(
            @RequestBody ProductReviewDTO productReviewDTO,
            @RequestPart(required = false) MultipartFile imgFile
    ) throws JSONException, IOException {
        log.info("리뷰 입력 창 ==========================");
        log.info("리뷰 입력 정보 : " + productReviewDTO.toString());
        log.info("이미지 파일 정보 : " + imgFile.getOriginalFilename());

        productReviewService.saveProductReview(productReviewDTO, imgFile);

        return productReviewDTO;
    }

    @PostMapping("/product_review/insert")
    public ProductReviewDTO sProductReview(
            MultipartHttpServletRequest request
    ) throws JSONException, IOException {
        log.info("리뷰 입력 창 ==========================");
        log.info("리뷰 입력 정보 : " + request.toString());
        System.out.println("리뷰삽입");
        ProductReviewDTO productReviewDTO = ProductReviewDTO.builder()
                .email(request.getParameter("email"))
                .productId(request.getParameter("productId"))
                .content(request.getParameter("content"))
                .rate(Integer.parseInt(request.getParameter("rate")))
                .age(request.getParameter("age"))
                .height(request.getParameter("height"))
                .bodyType(request.getParameter("bodyType"))
                .editDate(Date.valueOf(LocalDate.now()))
                .productSize(request.getParameter("productSize"))
                .productColorId(request.getParameter("productColorId"))
                .ctryLarge(request.getParameter("ctryLarge"))
                .ctryMedium(request.getParameter("ctryMedium"))
                .ctrySmall(request.getParameter("ctrySmall"))
                .title(request.getParameter("title"))
                .avgSize(request.getParameter("avgSize"))
                .build();

        List<MultipartFile> fileList = new ArrayList<MultipartFile>();

        // input file 에 아무것도 없을 경우 (파일을 업로드 하지 않았을 때 처리)
        if (!request.getFiles("imgFIle").isEmpty() && request.getFiles("imgFIle").get(0).getSize() != 0) {
            fileList = request.getFiles("imgFIle");
            log.info("이미지 파일 정보 : " + fileList.get(0).getOriginalFilename());
            productReviewService.saveProductReview(productReviewDTO, fileList.get(0));
            log.info("감성 분석을 진행합니다(파일없을 때) ===================");

            List<ProductReviewDTO> re = productReviewService.getProductReviewByProductId(productReviewDTO.getProductId());
            System.out.println("re" + re);
            JSONObject json = new JSONObject();

            for (ProductReviewDTO data : re) {
                json.put("content", data.getContent());
                System.out.println("json" + json);

            }
            System.out.println(json);

            String inputLine = null;
            StringBuffer stringBuffer = new StringBuffer();

            URL url = new URL("http://localhost:5000/review"); //URL객체 생성
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


            if (Integer.parseInt(stringBuffer.toString()) > 0.5) {
                productReviewDTO.setSentiment_percent(Integer.parseInt(stringBuffer.toString()) * 100);
                productReviewDTO.setSentiment_type("긍정");
            } else {
                productReviewDTO.setSentiment_percent((1 - Integer.parseInt(stringBuffer.toString())) * 100);
                productReviewDTO.setSentiment_type("부정");
            }


            System.out.println(stringBuffer);
            System.out.println(stringBuffer.getClass().getName());
            bWriter.close();
            bReader.close();
            conn.disconnect();
            return productReviewDTO;
        }

        productReviewService.saveProductReview(productReviewDTO, null);
        log.info("감성 분석을 진행합니다(파일 있을 때) ===================");

        List<ProductReviewDTO> re = productReviewService.getProductReviewByProductId(productReviewDTO.getProductId());
        System.out.println("re" + re);
        JSONObject json = new JSONObject();

        for (ProductReviewDTO data : re) {
            json.put("content", data.getContent());
            System.out.println("json" + json);

        }
        System.out.println(json);

        String inputLine = null;
        StringBuffer stringBuffer = new StringBuffer();

        URL url = new URL("http://localhost:5000/review"); //URL객체 생성
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
//      System.out.println("while문2   " + stringBuffer); //stringBuffer에 데이터 담겨있음
//      System.out.println(stringBuffer.getClass().getName());
//      System.out.println(stringBuffer);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringBuffer.toString());
        Map<String, Object> resultMap = objectMapper.treeToValue(jsonNode, Map.class);

        double sentiment_percent = (double) resultMap.get("sentiment_percent");

        System.out.println("제석이형" + sentiment_percent);
        // TODO sentiment_percent int로 변경
        if (sentiment_percent > 0.5) {
            System.out.println("248");
            productReviewDTO.setSentiment_percent(sentiment_percent * 100);
            productReviewDTO.setSentiment_type("긍정");

            System.out.println(productReviewDTO);
        } else {
            System.out.println("253");
            productReviewDTO.setSentiment_percent((1 - sentiment_percent) * 100);
            productReviewDTO.setSentiment_type("부정");
            System.out.println(productReviewDTO);
        }

        // TODO sp_review insert
        productReviewService.saveProductReview(productReviewDTO, null);
        bWriter.close();
        bReader.close();
        conn.disconnect();
        return productReviewDTO;
    }
}
