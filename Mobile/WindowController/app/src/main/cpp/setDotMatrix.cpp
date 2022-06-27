
#include "setDotMatrix.h"

extern "C"
JNIEXPORT jint JNICALL
Java_com_remote_windowcontroller_DevicePage_SetDotMatrix(JNIEnv *env, jobject thiz, jint face)
{
    // TODO: implement SetDotMatrix()
    __android_log_print(ANDROID_LOG_INFO,"FpgaDotExample","dot value = %d",face);
    setDotMatrix(face);

    return 0;
}


int setDotMatrix(int face)
{
    int dev;

    size_t str_size;

    str_size = sizeof(fpga_number[face]);

    dev = open(DOT_DEVICE,O_RDWR);
    if(dev<0)
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", face);
    else
    {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Success", "Driver = %d", face);

        switch (face)
        {
            case 0:
                write(dev,fpga_number[0],str_size);
                break;
            case 1:
                write(dev,fpga_number[1],str_size);
                break;
            case 2:
                write(dev,fpga_number[2],str_size);
                break;
            case 3:
                write(dev,fpga_number[3],str_size);
                break;
        }
        close(dev);
    }
    return 0;
}


