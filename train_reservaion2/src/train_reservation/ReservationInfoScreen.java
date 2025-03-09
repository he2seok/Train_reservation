package train_reservation;

import javax.swing.*;
import java.awt.*;

public class ReservationInfoScreen extends JFrame {

    private JTextField priceTextField;
    private JTextField arrivalStationTextField;
    private JTextField arrivalTimeTextField;
    private JTextField departureStationTextField;
    private JTextField departureTimeTextField;
    private JTextField trainTypeTextField;
    private JTextField trainNumberTextField;
    private JTextField seatTextField; // 좌석 정보 텍스트 필드 추가

    public ReservationInfoScreen() {
        setTitle("예약 조회");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("예약 조회");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel topInfoPanel = new JPanel(new GridLayout(8, 1)); // 텍스트 필드가 8개가 되도록 수정

        priceTextField = new JTextField();
        priceTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("가격", priceTextField));

        arrivalStationTextField = new JTextField();
        arrivalStationTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("도착역", arrivalStationTextField));

        arrivalTimeTextField = new JTextField();
        arrivalTimeTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("도착시간", arrivalTimeTextField));

        departureStationTextField = new JTextField();
        departureStationTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("출발역", departureStationTextField));

        departureTimeTextField = new JTextField();
        departureTimeTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("출발시간", departureTimeTextField));

        trainTypeTextField = new JTextField();
        trainTypeTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("열차종류", trainTypeTextField));

        trainNumberTextField = new JTextField();
        trainNumberTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("열차번호", trainNumberTextField));

        seatTextField = new JTextField(); // 좌석 정보 텍스트 필드 초기화
        seatTextField.setEditable(false);
        topInfoPanel.add(createLabeledTextField("좌석", seatTextField)); // 좌석 정보 텍스트 필드 추가

        add(topInfoPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("뒤로가기");
        JButton cancelReservationButton = new JButton("예약 취소"); // 예약 취소 버튼 추가

        backButton.addActionListener(e -> {
            setVisible(false);
            dispose();

            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        });

        cancelReservationButton.addActionListener(e -> {
            // 예약 취소 버튼을 누르면 좌석 정보 텍스트 필드의 내용을 지웁니다.
            priceTextField.setText("");
            arrivalStationTextField.setText("");
            arrivalTimeTextField.setText("");
            departureStationTextField.setText("");
            departureTimeTextField.setText("");
            trainTypeTextField.setText("");
            trainNumberTextField.setText("");
            seatTextField.setText("");

            // reservation.txt 파일의 모든 예약 정보를 삭제합니다.
            TrainData.cancelAllReservations();
        });

        bottomPanel.add(backButton);
        bottomPanel.add(cancelReservationButton);
        add(bottomPanel, BorderLayout.PAGE_END);
        
        // 파일에서 데이터를 읽어와서 각 필드에 설정
        String reservationData = TrainData.loadReservations();
        if (!reservationData.isEmpty()) {
            String[] infoParts = reservationData.split(" ");
            setReservationInfo(infoParts[0], infoParts[1], infoParts[2], infoParts[3], infoParts[4], infoParts[5], infoParts[6], infoParts[7]);
        } else {
            // 파일에서 데이터를 읽어오지 못한 경우에 대한 처리
            // 예를 들어, 기본값을 설정하거나 메시지를 표시할 수 있습니다.
        }

        
        setVisible(true);
    }
    
    // setReservationInfo 메서드 정의
    public void setReservationInfo(String price, String arrivalStation, String arrivalTime,
                                   String departureStation, String departureTime,
                                   String trainType, String trainNumber, String seat) {
        priceTextField.setText(price);
        arrivalStationTextField.setText(arrivalStation);
        arrivalTimeTextField.setText(arrivalTime);
        departureStationTextField.setText(departureStation);
        departureTimeTextField.setText(departureTime);
        trainTypeTextField.setText(trainType);
        trainNumberTextField.setText(trainNumber);
        seatTextField.setText(seat);
    }
    
    private JPanel createLabeledTextField(String label, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel labelComponent = new JLabel(label + ": ");
        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReservationInfoScreen::new);
    }
}