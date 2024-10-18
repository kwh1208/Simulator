package dbps.dbps;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryFntViewer extends Application {

    private static String filePath = "D:/Staff/50. 소프트웨어팀/02 Program/DabitSimulator/DABITSimulator_V2.3.0/Sys/Font/ENG 08x16-DABIT(표준).fnt";
    private static final int CHAR_WIDTH = 8;
    private static final int CHAR_HEIGHT = 16;
    private static final int PIXEL_SIZE = 10; // 각 픽셀의 크기
    private static final int CHAR_SPACING = 0; // 글자 간 간격      w

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("8x16 Font Viewer - Moving ABC");

        Canvas canvas = new Canvas(400, 300); // 글자가 들어갈 수 있는 Canvas 크기 지정
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 배경 색상 설정 (기본 배경 색)
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, 400, 300);

        // 글자별 배경색 배열 (각 글자의 배경색 지정)
        Color[] backgroundColors = {Color.RED, Color.GREEN, Color.BLUE};

        // 파일에서 비트맵 데이터를 읽어와서 A, B, C의 비트맵을 각각 표시
        int[] charIndices = {65, 66, 67}; // 'A', 'B', 'C'의 ASCII 코드
        for (int i = 0; i < charIndices.length; i++) {
            boolean[][] bitmap = readCharacterBitmap(filePath, charIndices[i]);
            if (bitmap != null) {
                int startX = 50 + (CHAR_WIDTH + CHAR_SPACING) * i * 5;

                // 글자 배경색 설정
                gc.setFill(backgroundColors[i]);
                gc.fillRect(startX, 50, CHAR_WIDTH * PIXEL_SIZE, CHAR_HEIGHT * PIXEL_SIZE);

                // 글자 비트맵을 그리기
                drawCharacter(gc, bitmap, startX, 50);
            } else {
            }
        }

        Group root = new Group();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // TranslateTransition 애니메이션 추가 (오른쪽에서 왼쪽으로 이동)
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(5)); // 애니메이션 지속 시간 설정
        transition.setNode(canvas); // 이동할 Node 설정 (Canvas 전체 이동)
        transition.setFromX(400); // 시작 위치 (오른쪽 끝)
        transition.setToX(-400); // 끝 위치 (왼쪽 끝)
        transition.setCycleCount(TranslateTransition.INDEFINITE); // 무한 반복
        transition.setAutoReverse(true); // 반대 방향으로 자동 반복
        transition.play(); // 애니메이션 시작
    }

    // 8x16 크기의 특정 글자 비트맵을 읽어오는 메서드
    private boolean[][] readCharacterBitmap(String filePath, int charIndex) {
        File file = new File(filePath);
        if (!file.exists()) {
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

                return null;
            }

            // 비트맵 데이터를 2차원 배열로 변환
            boolean[][] bitmap = new boolean[CHAR_HEIGHT][CHAR_WIDTH];
            for (int row = 0; row < CHAR_HEIGHT; row++) {
                byte rowData = charBitmapData[row];
                for (int col = 0; col < CHAR_WIDTH; col++) {
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
        for (int row = 0; row < CHAR_HEIGHT; row++) {
            for (int col = 0; col < CHAR_WIDTH; col++) {
                if (bitmap[row][col]) {
                    // 각 픽셀의 정확한 위치에 사각형 그리기 (크기: PIXEL_SIZE x PIXEL_SIZE)
                    gc.fillRect(startX + col * PIXEL_SIZE, startY + row * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                }
            }
        }
    }
}
