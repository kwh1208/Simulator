package dbps.dbps;

import java.nio.charset.Charset;

public class UTF_16ref {
    public static void main(String[] args) {
        String text = "![000/C1ふん!]";
        byte[] result = new byte[text.length() * 2];  // 최대 크기
        int index = 0;

        for (char c : text.toCharArray()) {
            if (c >= 0x00 && c <= 0x7F) {
                // ASCII 문자 (영문, 숫자, 기호) -> 1바이트로 처리
                result[index++] = (byte) c;
            } else {
                // 한글 문자 -> EUC-KR로 2바이트 인코딩
                byte[] encoded = String.valueOf(c).getBytes(Charset.forName("UTF-16LE"));
                result[index++] = encoded[1];
                result[index++] = encoded[0];
            }
        }

        // 결과 배열을 정확한 길이로 잘라냅니다.
        byte[] finalResult = new byte[index];
        System.arraycopy(result, 0, finalResult, 0, index);

        // 바이트 배열 출력
        for (byte b : finalResult) {
            System.out.printf("%02X ", b);
        }
    }
}
