package com.superdjm.potocol;

public interface Protocol {

    int DEFAULT_VERSION = 1;

    int getVersion();

    Byte getOp();

    enum Op {
        LOGIN_REQUEST((byte) 1, LoginRequestProtocol.class),
        LOGIN_RESULT((byte) 2, LoginResultProtocol.class),
        SINGLE_MESSAGE((byte) 3, SingleMessageProtocol.class),
        COMMON_RESPONSE((byte) 4, CommonResponseProtocol.class);

        Op(byte code, Class<? extends Protocol> clz) {
            this.code = code;
            this.clz = clz;
        }

        private byte code;

        private Class<? extends Protocol> clz;

        public Class<? extends Protocol> getClz() {
            return clz;
        }

        public byte getCode() {
            return code;
        }

        static Op valueOf(byte code) {
            for (Op tmp : Op.values()) {
                if (tmp.code == code) {
                    return tmp;
                }
            }
            throw new IllegalArgumentException("invalid code");
        }
    }
}
