using mi_ocr_worker_win_app_entity;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class ChannelUtil {
        public static bool IsDefined(string value)
        {
            if (ChannelType.T0003604.Equals(value))
            {
                return true;
            }
            else if (ChannelType.J000360401001.Equals(value))
            {
                return true;
            }
            else if (ChannelType.R000360403040.Equals(value))
            {
                return true;
            }
            return false;
        }
    }
}
