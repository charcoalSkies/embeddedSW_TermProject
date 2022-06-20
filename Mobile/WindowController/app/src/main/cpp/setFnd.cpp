//
// Created by Aegis on 6/20/2022.
//

#include "setFnd.h"

extern "C"
JNIEXPORT jint JNICALL
Java_com_remote_windowcontroller_DevicePage_SetFnd(JNIEnv *env, jobject thiz, jstring val)
{
    // TODO: implement SetFnd()
    jint result;
    const char *str =(*env).GetStringUTFChars(val,NULL);
    __android_log_print(ANDROID_LOG_INFO,"FpgaFndExample","Driver = %s",val);
    result = setFnd(str);
    (*env).ReleaseStringUTFChars(val,str);
    return result;
}

int setFnd(const char* str){
    int dev;
    unsigned char data[4];
    int str_size;

    memset(data,0,sizeof(data));

    str_size =(strlen(str));

    if(str_size > MAX_DIGIT)
        str_size = MAX_DIGIT;

    for(int i=0;i<str_size;i++)
    {
        if((str[i]<0x30)||(str[i]>0x39))
            return 1;

        data[i] = str[i]-0x30;
    }

    dev = open(FND_DEVICE,O_RDWR);
    if(dev < 0)
    {
        __android_log_print(ANDROID_LOG_INFO,"Device Open Error","Driver = %s",str);
        return -1;
    }
    else
    {
        __android_log_print(ANDROID_LOG_INFO,"Device Open Succsess","Driver = %d",str);
        write(dev,&data,4);
        close(dev);
        return  0;
    }
}

