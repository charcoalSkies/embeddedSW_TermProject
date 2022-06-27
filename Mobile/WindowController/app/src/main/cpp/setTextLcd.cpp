
#include "setTextLcd.h"

extern "C"
JNIEXPORT jint JNICALL
Java_com_remote_windowcontroller_DevicePage_SetTextLcd(JNIEnv *env, jobject thiz, jstring str1, jstring str2)
{
    jint result;
    const char* pstr1 = (*env).GetStringUTFChars(str1,NULL);
    __android_log_print(ANDROID_LOG_INFO,"FpgaExample","value = %s",pstr1);

    const char* pstr2 = (*env).GetStringUTFChars(str2,NULL);
    __android_log_print(ANDROID_LOG_INFO,"FpgaExample","value = %s",pstr2);

    SetTextLcd(pstr1,pstr2);

    (*env).ReleaseStringUTFChars(str1, pstr1);
    (*env).ReleaseStringUTFChars(str2, pstr2);
    return result;
}


int SetTextLcd(const char* str1 ,const char* str2)
{
    int dev;
    int str_size;

    unsigned char string[32];
    memset(string,0,sizeof(string));

    dev = open(FPGA_TEXT_LCD_DEVICE,O_RDWR);
    if(dev<0)
    {
        __android_log_print(ANDROID_LOG_INFO,"Device Open Error","Driver = %d",dev);
        return -1;
    }
    else
    {
        str_size = strlen(str1);
        if (str_size > 0)
        {
            strncat(reinterpret_cast<char *>(string),str1, str_size);
            memset(string + str_size, ' ', LINE_BUFF - str_size);
        }
        str_size = strlen(str2);

        if (str_size > 0)
        {
            strncat(reinterpret_cast<char *>(string), str2, str_size);
            memset( string + LINE_BUFF + str_size, ' ', LINE_BUFF - str_size);
        }
        write(dev, string, MAX_BUFF - 1 );
        close(dev);
        return 0;
    }
}


