package train_reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReservationInquiry {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private TrainReservationInquiryGUI trainReservationInquiryGUI; // TrainReservationInquiryGUI 참조
    private SeatList seatList; // SeatList 참조 (예약을 위한 좌석 선택 창)
    private JTextArea dataListArea;// 데이터를 표시할 JTextArea
    private String selectedTrainInfo; // 선택한 열차 정보
    private String selectedSeat; // 선택한 좌석 정보

    public ReservationInquiry(TrainReservationInquiryGUI trainReservationInquiryGUI) {
        this.trainReservationInquiryGUI = trainReservationInquiryGUI;

        frame = new JFrame("열차 예약 조회");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        dataListArea = new JTextArea(10, 50); // 행과 열의 수 설정
        dataListArea.setEditable(false); // 편집 불가능하게 설정
        JScrollPane scrollPaneForDataList = new JScrollPane(dataListArea);
        frame.add(scrollPaneForDataList, BorderLayout.SOUTH); // 프레임의 아래쪽에 추가

        // 테이블 모델 설정
        String[] columnNames = {"가격", "도착역", "도착시간", "출발역", "출발시간", "열차종류", "열차번호"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // 스크롤 패인에 테이블 추가
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton reserveButton = new JButton("예약");
        JButton cancelButton = new JButton("취소");

        // 예약 버튼 이벤트 리스너
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedTrainInfo = getTrainInfoFromSelectedRow(selectedRow); // 선택된 열차 정보 가져오기
                    seatList = new SeatList(ReservationInquiry.this, selectedTrainInfo); // SeatList 인스턴스 생성
                    seatList.display(); // SeatList 창 표시
                    frame.setVisible(false); // 현재 창 숨김
                } else {
                    // 열차를 선택하지 않았을 경우의 처리
                    JOptionPane.showMessageDialog(frame, "열차를 선택해주세요.");
                }
            }
        });

        // 취소 버튼 이벤트 리스너
        cancelButton.addActionListener(e -> {
            frame.setVisible(false); // 현재 창 숨김
            trainReservationInquiryGUI.display(); // TrainReservationInquiryGUI 창 표시
        });

        buttonPanel.add(reserveButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // 화면 중앙에 위치
    }

    public void display() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    // 선택된 행에서 열차 정보를 문자열로 가져오는 메서드
    private String getTrainInfoFromSelectedRow(int row) {
        StringBuilder infoBuilder = new StringBuilder();
        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            infoBuilder.append(tableModel.getValueAt(row, col)).append(" ");
        }
        return infoBuilder.toString().trim();
    }

    // API 호출 결과를 테이블에 추가하는 메서드
    public void addDataToTable(List<String> data) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for (String row : data) {
                    String[] values = row.split(","); // 쉼표로 분리
                    tableModel.addRow(values); // 테이블에 행 추가
                }
            }
        });
    }

    public void addDataToList(List<String> data) {
        StringBuilder sb = new StringBuilder();
        for (String rowData : data) {
            sb.append(rowData).append("\n"); // 각 데이터 행 추가
        }
        dataListArea.setText(sb.toString()); // JTextArea에 데이터 설정
    }

    // SeatList 클래스에서 선택한 열차 정보와 좌석 정보를 받는 메서드
    public void getSelectedTrainInfo(String trainInfo, String seat) {
        selectedTrainInfo = trainInfo;
        selectedSeat = seat;
    }
}
