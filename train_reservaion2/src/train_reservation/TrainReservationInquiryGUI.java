package train_reservation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

// 열차 조회 GUI 클래스
public class TrainReservationInquiryGUI {

    private JFrame frame;
    private ReservationInquiry reservationInquiry;
    private SubwayCategory subwayCategory;
    private SubwayCode subwayCode;

   
    public TrainReservationInquiryGUI() {
        subwayCategory = new SubwayCategory(); 
        subwayCode = new SubwayCode(); 
        frame = new JFrame("열차조회");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1)); 

        // 첫 번째 줄: 열차 종류 콤보박스
        JPanel trainTypePanel = new JPanel(new FlowLayout());
        JComboBox<String> comboBox1 = new JComboBox<>();
        for (String trainType : subwayCategory.sub_category.keySet()) {
            comboBox1.addItem(trainType);
        }
        trainTypePanel.add(new JLabel("열차종류:"));
        trainTypePanel.add(comboBox1);

        // 두 번째 줄: 출발지 콤보박스
        JPanel departureStationPanel = new JPanel(new FlowLayout());
        JComboBox<String> comboBox2 = new JComboBox<>();
        for (String departureStation : subwayCode.sub_map.keySet()) {
            comboBox2.addItem(departureStation);
        }
        departureStationPanel.add(new JLabel("출발지:"));
        departureStationPanel.add(comboBox2);

        // 세 번째 줄: 도착지 콤보박스
        JPanel arrivalStationPanel = new JPanel(new FlowLayout());
        JComboBox<String> comboBox3 = new JComboBox<>();
        for (String arrivalStation : subwayCode.sub_map.keySet()) {
            comboBox3.addItem(arrivalStation);
        }
        arrivalStationPanel.add(new JLabel("도착지:"));
        arrivalStationPanel.add(comboBox3);

        // 네 번째 줄: 출발일 입력
        JPanel departureDatePanel = new JPanel(new FlowLayout());
        JTextField departureDate = new JTextField(10); // 출발일 입력 필드
        departureDatePanel.add(new JLabel("출발일:(YYYYMMDD)"));
        departureDatePanel.add(departureDate);

        // 다섯 번째 줄: 빈 패널 (레이아웃 조정을 위해)
        JPanel emptyPanel = new JPanel();

        // 여섯 번째 줄: 조회 및 취소 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton reserveButton = new JButton("조회");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(reserveButton);
        buttonPanel.add(cancelButton);

        // 조회 버튼 이벤트 리스너
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 항목 가져오기
                String selectedTrainType = comboBox1.getSelectedItem().toString();
                System.out.println("열차 종류: " + selectedTrainType);
                String selectedDepartureStation = comboBox2.getSelectedItem().toString();
                System.out.println("출발지: " + selectedDepartureStation);
                String selectedArrivalStation = comboBox3.getSelectedItem().toString();
                System.out.println("도착지: " + selectedArrivalStation);
                String departureDateStr = departureDate.getText();
                System.out.println("출발일: " + departureDateStr);

                // 코드 변환
                String trainTypeCode = subwayCategory.sub_category.get(selectedTrainType);
                String departureStationCode = subwayCode.sub_map.get(selectedDepartureStation);
                String arrivalStationCode = subwayCode.sub_map.get(selectedArrivalStation);

                // 열차 운행 정보 조회
                TrainOperationInquiry trainOperationInquiry = new TrainOperationInquiry();
                try {
                    List<String> operationInfo = trainOperationInquiry.TrainOperationInquiry(trainTypeCode, departureStationCode, arrivalStationCode, departureDateStr);

                    // 조회 결과 표시
                    reservationInquiry = new ReservationInquiry(TrainReservationInquiryGUI.this);
                    reservationInquiry.addDataToTable(operationInfo);
                    reservationInquiry.display();
                    frame.setVisible(false);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 취소 버튼 이벤트 리스너
        cancelButton.addActionListener(e -> {
            MainScreen mainScreen = new MainScreen();
            frame.dispose(); // 현재 창 닫기
            mainScreen.setVisible(true); // MainScreen 화면 보이기
        });

        // 패널들을 프레임에 추가
        frame.add(trainTypePanel);
        frame.add(departureStationPanel);
        frame.add(arrivalStationPanel);
        frame.add(departureDatePanel);
        frame.add(emptyPanel);
        frame.add(buttonPanel);

        // 프레임 사이즈 설정 및 화면에 표시
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // 화면 중앙에 위치
    }

    // 창 표시 메서드
    public void display() {
        frame.setVisible(true);
    }

    // 프레임 getter 메서드
    public JFrame getFrame() {
        return frame;
    }
}

