package train_reservation;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrainData {

    public static void saveReservation(String trainInfo, String seatInfo) {
        String filename = "reservation.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(trainInfo + ", " + seatInfo);
            writer.newLine();
            System.out.println("예약 정보가 저장되었습니다.");
        } catch (IOException e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
    
    // reservation.txt 파일에서 데이터를 읽어오는 메서드
    public static String loadReservations() {
        String filename = "reservation.txt";
        StringBuilder data = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
        }

        return data.toString();
    }
    //reservation.txt 파일 내용 삭제 기능
    public static void cancelAllReservations() {
        String filename = "reservation.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(""); // 파일 내용을 비웁니다.
            System.out.println("모든 예약 정보가 삭제되었습니다.");
        } catch (IOException e) {
            System.err.println("파일 쓰기 오류: " + e.getMessage());
        }
    }


}


