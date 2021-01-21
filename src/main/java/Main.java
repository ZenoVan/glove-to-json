import java.io.IOException;
/**
 * Created by FanZeQiu on 2021/1/19
 */
public class Main {

    private static final String PATH = "src/main/resources";
    private static final String[] FILE_NAMES = new String[]{"glove.6B.50d.txt", "glove.6B.100d.txt", "glove.6B.200d.txt", "glove.6B.300d.txt"};
//    private static final String[] FILE_NAMES = new String[]{"glove.6B.200d.txt"};

    public static void main(String[] args) throws IOException {
        TxtProcessor processor = new TxtProcessor();
        processor.setPath(PATH);
        for (String fileName : FILE_NAMES) {
            long startTime =  System.currentTimeMillis();
            System.out.println("开始：" + fileName);

            processor.setFileName(fileName);
            processor.output(processor.txtToJson());

            long endTime =  System.currentTimeMillis();
            long usedTime = (endTime-startTime)/1000;
            System.out.println("结束：" + fileName + " 耗时：" + usedTime + "s");
//            System.out.println("结束：" + fileName);

        }
    }

}
