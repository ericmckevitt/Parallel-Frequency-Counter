import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FrequencyCounter {

    public static byte[] parseInputFile(String inputFile) throws IOException {
        File file = new File(inputFile);
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            byte[] barray = new byte[(int) file.length()];
            ByteBuffer bb = ByteBuffer.wrap(barray);
            fileChannel.read(bb);
            return barray;
        }
    }

    public static int[] countFrequenciesInChunk(byte[] input, int start, int end) {
        int[] localFrequency = new int[26];

        for (int i = start; i < end; i++) {
            byte b = input[i];
            if (b >= 'a' && b <= 'z') {
                localFrequency[b - 'a']++;
            }
        }

        return localFrequency;
    }

    public static void countFrequencies(byte[] input) throws ExecutionException, InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(availableProcessors);

        int chunkSize = input.length / availableProcessors;
        List<Future<int[]>> futures = new ArrayList<>();

        for (int i = 0; i < availableProcessors; i++) {
            int start = i * chunkSize;
            int end = (i == availableProcessors - 1) ? input.length : start + chunkSize;
            futures.add(executor.submit(() -> countFrequenciesInChunk(input, start, end)));
        }

        int[] globalFrequency = new int[26];
        for (Future<int[]> future : futures) {
            int[] localFrequency = future.get();
            for (int i = 0; i < globalFrequency.length; i++) {
                globalFrequency[i] += localFrequency[i];
            }
        }

        executor.shutdown();

        for (int i = 0; i < globalFrequency.length; i++) {
            if (globalFrequency[i] > 0) {
                System.out.println((char) (i + 'a') + ": " + globalFrequency[i]);
            }
        }
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String filename = args[0];

        byte[] input = parseInputFile(filename);

        countFrequencies(input);
    }
}
