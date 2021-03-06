package org.efix.util.parse;

import org.efix.util.ByteSequenceWrapper;
import org.efix.util.MutableInt;
import org.efix.util.buffer.Buffer;
import org.efix.util.buffer.BufferUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteSequenceParserTest extends AbstractParserTest {

    @Test
    public void shouldParseSequences() {
        shouldParse("1");
        shouldParse("12");
        shouldParse("So I'm sorry \n");
    }

    @Test
    public void shouldFailParseSequences() {
        shouldFailParse("work");
        shouldFailParse("=");
        shouldFailParse("");
    }

    protected static void shouldParse(String string) {
        Buffer buffer = BufferUtil.fromString(string + (char) SEPARATOR);
        MutableInt offset = new MutableInt();
        int end = buffer.capacity();

        ByteSequenceWrapper sequence = new ByteSequenceWrapper();
        ByteSequenceParser.parseByteSequence(SEPARATOR, buffer, offset, end, sequence);
        String actual = sequence.toString();

        assertEquals(string, actual);
        assertEquals(offset.get(), end);

        offset.set(0);
        ByteSequenceParser.parseByteSequence(SEPARATOR, buffer, offset, end);

        assertEquals(offset.get(), end);
    }

    protected static void shouldFailParse(String string) {
        Parser<Object> sequenceParser = (separator, buffer, offset, end) -> {
            ByteSequenceParser.parseByteSequence(separator, buffer, offset, end, new ByteSequenceWrapper());
            return null;
        };

        Parser<Object> emptyParser = (separator, buffer, offset, end) -> {
            ByteSequenceParser.parseByteSequence(separator, buffer, offset, end);
            return null;
        };

        shouldFailParse(string, sequenceParser, emptyParser);
    }

}
