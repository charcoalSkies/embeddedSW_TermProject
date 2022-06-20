//
// Created by Aegis on 6/20/2022.
//

#ifndef WINDOWCONTROLLER_SETSTEPMOTOR_H
#define WINDOWCONTROLLER_SETSTEPMOTOR_H

#include "includes.h"

#define STEP_DEVICE "/dev/fpga_step_motor"

void timer(int milliseconds);
int step_motor(int direction);

#endif //WINDOWCONTROLLER_SETSTEPMOTOR_H
