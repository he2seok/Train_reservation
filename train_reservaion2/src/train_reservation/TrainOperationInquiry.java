package train_reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.util.HashMap;

//열차운행정보api
public class TrainOperationInquiry {

    public List<String> TrainOperationInquiry(String trainType, String departureStation, String arrivalStation, String date) throws IOException {
        List<String> trainOperation = new ArrayList<>();

        StringBuilder urlBuilder = new StringBuilder(""); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + ""); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("depPlaceId", "UTF-8") + "=" + URLEncoder.encode(departureStation, "UTF-8")); /*출발기차역ID [상세기능3. 시/도별 기차역 목록조회]에서 조회 가능*/ //departureStation 필
        urlBuilder.append("&" + URLEncoder.encode("arrPlaceId", "UTF-8") + "=" + URLEncoder.encode(arrivalStation, "UTF-8")); /*도착기차역ID [상세기능3. 시/도별 기차역 목록조회]에서 조회 가능*/ //arrivalStation 필
        urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*출발일(YYYYMMDD)*/ //date 필
        urlBuilder.append("&" + URLEncoder.encode("trainGradeCode", "UTF-8") + "=" + URLEncoder.encode(trainType, "UTF-8")); /*차량종류코드*/ //trainType선
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        // HTTP 응답 코드 확인
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // XML 응답을 StringBuilder 객체에 저장
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // XML 파싱
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(sb.toString())));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String adultcharge = eElement.getElementsByTagName("adultcharge").item(0).getTextContent();
                    String arrplacename = eElement.getElementsByTagName("arrplacename").item(0).getTextContent();
                    String arrplandtime = eElement.getElementsByTagName("arrplandtime").item(0).getTextContent();
                    String depplacename = eElement.getElementsByTagName("depplacename").item(0).getTextContent();
                    String depplandtime = eElement.getElementsByTagName("depplandtime").item(0).getTextContent();
                    String traingradename = eElement.getElementsByTagName("traingradename").item(0).getTextContent();
                    String trainno = eElement.getElementsByTagName("trainno").item(0).getTextContent();

                    String rowData = String.join(",", adultcharge, arrplacename, arrplandtime, depplacename, depplandtime, traingradename, trainno);
                    trainOperation.add(rowData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainOperation;
    }
}