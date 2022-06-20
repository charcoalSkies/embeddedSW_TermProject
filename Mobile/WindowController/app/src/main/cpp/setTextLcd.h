//
// Created by Aegis on 6/20/2022.
//

#ifndef WINDOWCONTROLLER_SETTEXTLCD_H
#define WINDOWCONTROLLER_SETTEXTLCD_H

#include "includes.h"

#define LINE_BUFF 16
#define MAX_BUFF 32
#define FPGA_TEXT_LCD_DEVICE "/dev/fpga_text_lcd"

int SetTextLcd(const char* str1, const char* str2);

#endif //WINDOWCONTROLLER_SETTEXTLCD_H

