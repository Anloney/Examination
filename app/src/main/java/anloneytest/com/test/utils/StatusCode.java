package anloneytest.com.test.utils;

/**
 * 状态码 包括requestcode 或者 0 1情况
 *
 * @author anjingshuai
 */
public class StatusCode {
    /**
     * 设备为android 0为android，1为ios
     */
    public static final String OS_ANDROID = "0";

    /*服务器返回的状态码 成功*/
    public static final String SUCCESS_TASK = "0000";
    /**
     * session过期  没有该账户
     */
    public static final String SESSION_OUTOFDATE_TASK = "1003";
    /**
     * 通用的都是0是   1否
     */
    public static final String IS_COMMON_PARAMS = "0";
    public static final String NO_COMMON_PARAMS = "1";//0是A类证书  1是身份证
    public static final String PHOTO_ONLY_COMMON_PARAMS = "2";//一寸照 只有选择相册的时候 不裁剪
    public static final String RESIDENCE_PHOTO_COMMON_PARAMS = "3";//暂住证 居住证照片
    /**
     * 审核状态 0  通过 1   未通过  2  待审核  3  未提交报名 4 已经考过试了，现在是考B级，
     * 此时也需要提交认证，现在还未提交  5已经考过B级试了 不能再考试
     */
    public static final String PASSED = "0";
    public static final String UNPASSED = "1";
    public static final String TO_VERIFY = "2";
    public static final String UN_COMMIT = "3";
    public static final String UN_COMMIT_PASSED = "4";
    public static final String ATTENDED_TEST = "5";
    /* 请求码*/
    public static final int MODIFY_CITY_REQUEST_CODE = 11;
    public static final int COMMON_REQUEST_CODE = 12;
    /*拍照和相册的请求码*/
    public static final int TAKE_PHOTO_REQUEST_CODE = 13;
    public static final int SELECT_ALBUM_REQUEST_CODE = 14;
    /*省份和城市的请求码*/
    public static final int PROVINCE_REQUEST_CODE = 15;
    public static final int CITY_REQUEST_CODE = 16;


    /**
     * activity的照片拍照裁剪的requestCode，
     */
    public static final int MEDIA_CAMERA_REQUEST_CODE = 17;
    public static final int MEDIA_IMAGE_REQUEST_CODE = 18;
    public static final int MEDIA_CROP_REQUEST_CODE = 19;
    /*修改信息提交后的resultcode*/
    public static final int COMMIT_INFO_RESULT_CODE = 20;
    /*在所有题目列表页面选择一个题目resultcode*/
    public static final int SELECT_SUBJECT_RESULT_CODE = 21;
    /*选择一个民族后resultcode*/
    public static final int SELECT_NATION_RESULT_CODE = 22;
}
