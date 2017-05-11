package anloneytest.com.test.utils;

import android.os.Environment;

/**
 * 常量类 包括保存的sharedpreference对应的key和第三方sdk的key
 *
 * @author anjingshuai
 */
public class Constants {
    //考试题目数
    public static final int TEST_COUNT = 50;
    //考试题库题目数
    public static final int ALL_TEST_COUNT = 163;

    public static final String TEST_SOURCE_NAME = "test.txt";

    /**
     * 保存图片的文件夹
     */
    public static final String RADIO_TEST_FILE_STORAGE = Environment
            .getExternalStorageDirectory() + "/radiotest/";
    /**
     * 保存图片的文件夹
     */
    public static final String IMAGE_SAVE_FILE_STORAGE = "saveImage/";
}
