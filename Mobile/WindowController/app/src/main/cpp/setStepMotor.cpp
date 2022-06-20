//
// Created by Aegis on 6/20/2022.
//

#include "setStepMotor.h"
extern "C"
JNIEXPORT jint JNICALL
Java_com_remote_windowcontroller_DevicePage_SetMotorState(JNIEnv *env, jobject thiz, jint direction)
{
    // TODO: implement SetMotorState()
    jint result;

    __android_log_print(ANDROID_LOG_INFO, "StepMotor", "SetMotor");
    result = step_motor(direction);

    return result;
}

void timer(int milliseconds)
{
    int milliseconds_since = clock() * 1000 / CLOCKS_PER_SEC;
    int end = milliseconds_since + milliseconds;

    do{
        milliseconds_since = clock() * 1000 / CLOCKS_PER_SEC;
    } while (milliseconds_since < end);
}


int step_motor(int direction)
{
    int dev;
    uint8_t speed = 5;
    uint8_t action = 0;
    uint8_t motor_state[3];

    memset(motor_state, 0, sizeof(motor_state));

    motor_state[0] = (uint8_t)action;
    motor_state[1] = (uint8_t)direction;
    motor_state[2] = (uint8_t)speed;

    dev = open(STEP_DEVICE, O_RDWR);

    if(dev<0)
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", dev);
    else
    {
        __android_log_print(ANDROID_LOG_INFO, "Motor move", "Driver = %d", dev);
        action = 1;
        motor_state[0] = action;
        write(dev, motor_state, sizeof(motor_state));

        timer(390);

        action = 0;
        __android_log_print(ANDROID_LOG_INFO, "Motor stop", "Driver = %d", dev);
        motor_state[0] = action;
        write(dev, motor_state, sizeof(motor_state));
        close(dev);
    }
    return 0;
}

