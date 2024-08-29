package dbps.dbps;


import com.fazecast.jSerialComm.SerialPort;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import java.io.IOException;
import java.nio.charset.Charset;

public class testMethod {
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
        int SPI_SETFONTSMOOTHING = 0x004B;
        int SPIF_UPDATEINIFILE = 0x01;
        int SPIF_SENDCHANGE = 0x02;

        boolean SystemParametersInfo(int uiAction, int uiParam, boolean pvParam, int fWinIni);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        // ClearType 켜기
        SerialPort port = SerialPort.getCommPort("COM6");
        port.setComPortParameters(115200, 8, 1, 0);
        port.openPort();

//        ![000/E0606/S2010/C3/G0 Hello가나다123!@#$!]
        String tmp1 = "![000/E";
        for (int i = 20; i < 56; i++) {
            String tmp2 = tmp1+String.format("%02d", i);
            String tmp3 = tmp2+String.format("%02d", i)+"/S1004";
            if (i == 10 || i == 11 || i == 16 || i == 17 ||
                    i == 22 || i == 23 || i == 28 || i == 29 ||
                    i == 30 || i == 31 || i == 32 || i == 33 || i == 34) {
                continue; // 조건에 해당하는 경우 루프의 나머지 부분을 건너뛰고 다음 반복으로 넘어감
            }
                for (int k = 0; k < 8; k++) {
                    String tmp4 = tmp3+"/C"+k;
                    for (int l = 0; l < 8; l++) {
                        String tmp5 = tmp4+"/G"+l+" Hello가나다123!@#$!]";

                        port.writeBytes(tmp5.getBytes(Charset.forName("EUC-KR")), tmp5.getBytes(Charset.forName("EUC-KR")).length);


                        System.out.println("보낸 메세지 = " + tmp5);

//보낸 메세지 = ![000/E0101/S2010/C0/G0 Hello가나다123!@#$!]

                        Thread.sleep(3000);

                    }
                }
            }
        }
    }