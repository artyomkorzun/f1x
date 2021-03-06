package org.efix.log;

import org.efix.util.buffer.Buffer;


public final class EmptyMessageLog implements MessageLog {

    public static final EmptyMessageLog INSTANCE = new EmptyMessageLog();

    private EmptyMessageLog() {
    }

    @Override
    public void log(boolean inbound, long time, Buffer message, int offset, int length) {
    }

    @Override
    public void open() {
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {
    }

}
