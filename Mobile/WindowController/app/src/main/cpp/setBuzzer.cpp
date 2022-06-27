
//
// Created by Aegis on 6/20/2022.
//

#include "setBuzzer.h"


extern "C"
JNIEXPORT jint JNICALL
Java_com_remote_windowcontroller_DevicePage_SetBuzzer(JNIEnv *env, jobject thiz, jint sound) {
    // TODO: implement SetBuzzer()
    setBuzzer(sound);
    return 0;
}

int setBuzzer(int sound)
{
    int dev;
    unsigned char data;

    data = (char) sound;
    dev = open(BUZZER_DEVICE, O_RDWR);
    if(dev < 0)
    {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", sound);
        return -1;
    }
    else
    {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Success", "Driver = %d", sound);
        write(dev, &data, 1);
        close(dev);
        return 0;
    }
}