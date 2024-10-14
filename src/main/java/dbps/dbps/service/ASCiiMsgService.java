package dbps.dbps.service;


import dbps.dbps.Simulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dbps.dbps.Constants.SIZE_COLUMN;
import static dbps.dbps.Constants.SIZE_ROW;


public class ASCiiMsgService {

    private static final String FILE_NAME = "messages.txt";
    public static Stage makeMsgWindow;

    private static ASCiiMsgService instance = null;

    private final LogService logService;
    ConfigService configService;

    private ASCiiMsgService() {
        logService = LogService.getLogService();
        configService = ConfigService.getInstance();
    }

    public static ASCiiMsgService getInstance() {
        if (instance == null) {
            instance = new ASCiiMsgService();
        }
        return instance;
    }

    //메세지 txt 파일에 저장
    public void saveMessages(int num, String msg){
        configService.setProperty("ASCMsg"+num, msg);
    }

    //메세지 초기화 확인
    public void resetMsg(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            for (int i = 1; i < 10; i++) {
                if (i==1){
                    configService.setProperty("ASCMsg"+i, "![000Hello world!]");
                } else if (i==2) {
                    configService.setProperty("ASCMsg"+i, "![000/P000/C1Hello!]");
                } else if (i == 3) {
                    configService.setProperty("ASCMsg"+i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
                }
                configService.setProperty("ASCMsg"+i, "");
            }
        }
    }

    /**
     * @param transmitMsgs
     */

    public void preview(TextField transmitMsgs, HashMap<String, String> textEffect) {
        String inputText = transmitMsgs.getText();

        //메세지 미리보기
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/preview.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);  // 모달창 설정

        stage.setTitle("Preview Window "+SIZE_ROW+" X "+SIZE_COLUMN);
        Canvas canvas = new Canvas(SIZE_COLUMN*16*2, SIZE_ROW*16*2);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, SIZE_COLUMN*2*16, SIZE_ROW*2*16);
        //한글인지 영어인지 체크
        for (int i = 0; i < inputText.length(); i++) {
            String filePath = "D:/Staff/50. 소프트웨어팀/02 Program/DabitSimulator/DABITSimulator_V2.3.0/Sys/Font/ENG 08x16-DABIT(표준).fnt";
            boolean[][] bitmap = readCharacterBitmap(filePath, inputText.charAt(i));
            if (bitmap != null) {
                int startX = (SIZE_COLUMN*16*2) * i;

                // 글자 배경색 설정
                gc.setFill(Color.YELLOW);
                gc.fillRect(startX, 50, SIZE_COLUMN * 16 * 2, SIZE_ROW * 16 * 2);

                // 글자 비트맵을 그리기
                drawCharacter(gc, bitmap, startX, 0);
            } else {
                System.out.println("Failed to load character bitmap for index: " + i);
            }
        }

        // Scene 생성 및 크기 지정
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), SIZE_COLUMN*16*2, SIZE_ROW*16*2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);

        // 모달 창 표시
        stage.showAndWait();

    }

    private boolean[][] readCharacterBitmap(String filePath, int charIndex) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            // 파일의 헤더(16바이트)를 건너뜀
            fis.skip(16);

            // 각 글자의 비트맵 데이터는 16바이트씩 저장됨
            int charSize = 16; // 글자 하나당 16바이트 (8x16 비트맵)
            long offset = (long) charIndex * charSize; // 타겟 글자의 시작 위치 (long으로 캐스팅)
            fis.skip(offset); // 타겟 글자의 위치로 이동

            // 글자 비트맵 데이터 읽기
            byte[] charBitmapData = new byte[charSize];
            int bytesRead = fis.read(charBitmapData);

            if (bytesRead != charSize) {
                System.out.println("Failed to read character data.");
                return null;
            }

            // 비트맵 데이터를 2차원 배열로 변환
            boolean[][] bitmap = new boolean[SIZE_ROW*16*2][SIZE_COLUMN*16*2];
            for (int row = 0; row < 16; row++) {
                byte rowData = charBitmapData[row];
                for (int col = 0; col < 8; col++) {
                    // 열 순서가 역순일 때 수정 (왼쪽부터 오른쪽으로 비트를 읽음)
                    bitmap[row][col] = (rowData & (1 << (7 - col))) != 0; // 비트 패턴 추출
                }
            }

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 비트맵 데이터를 Canvas에 그리는 메서드
    private void drawCharacter(GraphicsContext gc, boolean[][] bitmap, int startX, int startY) {
        gc.setFill(Color.BLACK); // 글자 색상 설정
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 8; col++) {
                if (bitmap[row][col]) {
                    // 각 픽셀의 정확한 위치에 사각형 그리기 (크기: PIXEL_SIZE x PIXEL_SIZE)
                    gc.fillRect(startX + col * 2, startY + row * 2, 2, 2);
                }
            }
        }
    }

    //


    /**
     * 리팩토링
     */

    //메세지 불러오기
    public List<String> loadMessages() {
        List<String> messages = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            String value = configService.getProperty("ASCMsg" + i);
            messages.add(value);
        }

        return messages;
    }

    //메세지 만들기 창 띄우기
    public void makeOwnMsg() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/makeOwnMsg.fxml"));
            AnchorPane root = fxmlLoader.load();

            makeMsgWindow = new Stage();
            makeMsgWindow.setTitle("메세지 만들기");

            Scene scene = new Scene(root, 550, 600);
            makeMsgWindow.setScene(scene);
            makeMsgWindow.setResizable(false);

            makeMsgWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
