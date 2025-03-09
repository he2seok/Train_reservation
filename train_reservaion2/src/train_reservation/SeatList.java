package train_reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatList {
    private JFrame frame;
    private ReservationInquiry reservationInquiry;
    private String trainInfo; // 열차 정보
    private JLabel trainInfoLabel; // 열차 정보를 표시할 레이블
    private String selectedSeat; // 선택된 좌석

    public SeatList(ReservationInquiry reservationInquiry, String trainInfo) {
        this.reservationInquiry = reservationInquiry;
        this.trainInfo = trainInfo; // 열차 정보 설정

        frame = new JFrame("좌석 목록");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 열차 정보를 표시할 레이블 초기화
        trainInfoLabel = new JLabel("열차 정보: " + trainInfo);
        frame.add(trainInfoLabel, BorderLayout.NORTH);

        // 좌석 버튼 초기화 및 패널에 추가
        JPanel seatPanel = new JPanel(new GridLayout(4, 15, 1, 1));
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 15; col++) {
                String seatNumber = (char) ('A' + row) + String.valueOf(col + 1);
                JButton seatButton = new JButton(seatNumber);
                seatButton.addActionListener(e -> {
                    selectedSeat = seatNumber;
                    trainInfoLabel.setText("열차 정보: " + trainInfo + ", 선택한 좌석: " + selectedSeat);
                });
                seatPanel.add(seatButton);
            }
        }

        // 하단 버튼을 위한 패널 생성
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton reserveButton = new JButton("예약");
        JButton cancelButton = new JButton("취소");

        // 예약 버튼 액션 리스너
        reserveButton.addActionListener(e -> {
            if (selectedSeat != null && !selectedSeat.isEmpty()) {
                TrainData.saveReservation(trainInfo, selectedSeat); // TrainData 클래스를 이용해 파일에 저장
                JOptionPane.showMessageDialog(frame, "예약이 완료되었습니다.");
            } else {
                JOptionPane.showMessageDialog(frame, "좌석을 선택해주세요.");
            }
        });

        // 취소 버튼 액션 리스너
        cancelButton.addActionListener(e -> {
            frame.setVisible(false); // SeatList 창 숨기기
            reservationInquiry.display(); // ReservationInquiry 창 다시 표시
            reservationInquiry.getSelectedTrainInfo(trainInfo, selectedSeat); // 선택한 열차 정보와 좌석 정보를 ReservationInquiry로 전달
        });

        bottomPanel.add(reserveButton);
        bottomPanel.add(cancelButton);

        frame.add(seatPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
    }

    public void display() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}

