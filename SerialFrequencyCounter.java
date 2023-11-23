import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SerialFrequencyCounter {

    public static byte[] parseInputFile(String inputFile) throws IOException {
        File file = new File(inputFile);
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            byte[] barray = new byte[(int) file.length()];
            ByteBuffer bb = ByteBuffer.wrap(barray);
            fileChannel.read(bb);
            return barray;
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        System.out.println("Reading file: " + filename);
    }
}
