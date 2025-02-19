package com.rdapps.gamepad.command.handler.subcommand;

import com.rdapps.gamepad.memory.ControllerMemory;
import com.rdapps.gamepad.protocol.JoyController;
import com.rdapps.gamepad.report.InputReport;
import com.rdapps.gamepad.report.OutputReport;

import java.util.Arrays;

import static com.rdapps.gamepad.log.JoyConLog.log;
import static com.rdapps.gamepad.report.InputReport.Type.SUBCOMMAND_REPLY_REPORT;

/**
 * https://github.com/dekuNukem/Nintendo_Switch_Reverse_Engineering/blob/master/bluetooth_hid_subcommands_notes.md#subcommand-0x11-spi-flash-write
 */
class SPIFLashWriteHandler implements SubCommandHandler {
    private final static String TAG = SPIFLashWriteHandler.class.getName();
    private final static byte ACK = (byte) 0x80;

    @Override
    public InputReport handleRumbleAndSubCommand(JoyController joyController, OutputReport outputReport) {
        InputReport subCommandReply = new InputReport(SUBCOMMAND_REPLY_REPORT);
        subCommandReply.fillAckByte(ACK);
        subCommandReply.fillSubCommand(outputReport.getSubCommandId());
        byte[] data = outputReport.getData();
        int eepromLocation = 0;
        for (int i = 0; i < 4; i++) {
            int a = data[10 + i] & 0xFF;
            eepromLocation |= a << (i * 8);
        }
        int len = data[14] & 0xFF;

        log(TAG, "EEPROM Location: " + Integer.toHexString(eepromLocation) + " Write Length: " + len);


        ControllerMemory controllerMemory = joyController.getControllerMemory();
        if (len > 0) {
            controllerMemory.write(eepromLocation, Arrays.copyOfRange(data, 15, len));
        }
        return subCommandReply;
    }
}
