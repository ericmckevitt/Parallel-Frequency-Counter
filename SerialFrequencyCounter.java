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

    public static void countFrequencies(byte[] input) {

        // Define a 26 element array to store the frequency of each letter
        int[] frequency = new int[26];

        for (byte b : input) {
            if (b < 'a' || b > 'z')
                continue;
            int index = Character.toLowerCase(b) - 'a';
            frequency[index]++;
        }

        // Print the frequency of each letter
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] == 0)
                continue;
            System.out.println((char) (i + 'a') + ": " + frequency[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        String filename = args[0];

        byte[] input = parseInputFile(filename);

        countFrequencies(input);
    }
}
