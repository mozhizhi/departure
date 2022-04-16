package persi.sumu.departure.core.util;

import java.util.UUID;

/**
 * @author mobai
 * @version 1.0
 * @Description 通用
 * @date 2022/4/16 14:27
 */
public class DepartureUtil {

    /**
     * 文件类型是否支持
     * @param suffix 文件后缀
     * @param type   文件类型
     * @return true：支持 false：不支持
     */
    public static boolean isFileTypeSupport(String suffix, String type) {
        boolean flag = true; // 默认支持
        if(ConstantUtil.QN_FILE_TYPE_IMAGE.equals(type)) {// 图片类型
            if(!".jpg".equals(suffix) && !".png".equals(suffix) && !".jpeg".equals(suffix) && !".gif".equals(suffix) &&
                    !".JPG".equals(suffix) && !".PNG".equals(suffix) && !".JPEG".equals(suffix) && !".GIF".equals(suffix)) {
                flag = false;
            }
        } else if(ConstantUtil.QN_FILE_TYPE_VIDEO.equals(type)) {
            if(!".mp4".equals(suffix) && !".mov".equals(suffix) && !".m4v".equals(suffix) && !".3gp".equals(suffix) &&
                    !".avi".equals(suffix) && !".m3u8".equals(suffix) && !".webm".equals(suffix) &&
                    !".MP4".equals(suffix) && !".MOV".equals(suffix) && !".M4V".equals(suffix) && !".3GP".equals(suffix) &&
                    !".AVI".equals(suffix) && !".M3U8".equals(suffix) && !".WEBM".equals(suffix)) {
                flag = false;
            }
        } else {
            // TODO 不支持的媒体类型，可扩展
            flag = false;
        }
        return flag;
    }

    /**
     * 校验文件大小
     * @param targetSize 目标文件size
     * @param maxSize    文件最大限制size
     * @return true：校验通过 false：校验不通过
     */
    public static boolean checkFileSize(long targetSize, long maxSize) {
        if(targetSize <= maxSize) {
            return true;
        }
        return false;
    }

    /**
     * 获取生成上传至七牛云的文件全名
     * @param suffix 文件名后缀
     * @return 上传至七牛云的文件全名
     */
    public static String getUUIDKeyAsFileName(String suffix) {
        StringBuffer retBuffer = new StringBuffer();
        retBuffer.append(UUID.randomUUID().toString().replaceAll("-", ""));
        retBuffer.append(suffix);
        return String.valueOf(retBuffer);
    }

}
