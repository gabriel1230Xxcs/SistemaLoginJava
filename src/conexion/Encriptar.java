package conexion;

import java.security.MessageDigest;

public class Encriptar {

    public static String md5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mensaje = md.digest(texto.getBytes());

            StringBuilder sb = new StringBuilder();

            for(byte b : mensaje){
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }
}