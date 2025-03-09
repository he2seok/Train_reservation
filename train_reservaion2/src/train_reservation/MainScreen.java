package train_reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreen extends JFrame implements ActionListener {

    private JFrame mainScreenFrame;

    public MainScreen() {
        mainScreenFrame = this;

        setTitle("Main 화면");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JLabel menuLabel = new JLabel("메뉴판");
        menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(menuLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton ticketButton = new JButton("승차권 조회");
        ticketButton.addActionListener(this);
        ticketButton.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(ticketButton);

        JButton reservationButton = new JButton("예약 조회");
        reservationButton.addActionListener(this);
        reservationButton.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(reservationButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("승차권 조회")) {
            openTrainReservationInquiryGUI();
        } else if (command.equals("예약 조회")) {
            openReservationInfoScreen();
        }
    }

    private void openTrainReservationInquiryGUI() {
        // TrainReservationInquiryGUI의 기능을 MainScreen에서 구현
        TrainReservationInquiryGUI inquiryGUI = new TrainReservationInquiryGUI();
        inquiryGUI.display();
        setVisible(false); // MainScreen 화면 숨기기
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainScreen();
        });
    }
    
    private void openReservationInfoScreen() {
        SwingUtilities.invokeLater(() -> {
            mainScreenFrame.dispose();
            ReservationInfoScreen reservationInfoScreen = new ReservationInfoScreen(); // MainScreen 인스턴스 전달
            reservationInfoScreen.setVisible(true); // 생성된 인스턴스를 보여줌
        });
    }


}
