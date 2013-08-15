/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.aesh.cl;

import junit.framework.TestCase;
import org.jboss.aesh.cl.exception.CommandLineParserException;
import org.jboss.aesh.cl.internal.OptionInt;

import java.util.List;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class ParserGeneratorTest extends TestCase {

    public ParserGeneratorTest(String name) {
        super(name);
    }

    public void testClassGenerator() throws CommandLineParserException {

        Test1 test1 = new Test1();
        CommandLineParser parser = ParserGenerator.generateCommandLineParser(test1);

        assertEquals("a simple test", parser.getParameter().getUsage());
        List<OptionInt> options = parser.getParameter().getOptions();
        assertEquals("f", options.get(0).getShortName());
        assertEquals("foo", options.get(0).getName());
        assertEquals("e", options.get(1).getShortName());
        assertEquals("enable e", options.get(1).getDescription());
        assertTrue(options.get(1).hasValue());
        assertTrue(options.get(1).isRequired());
        assertEquals("bar", options.get(2).getName());
        assertFalse(options.get(2).hasValue());

        Test2 test2 = new Test2();
        parser = ParserGenerator.generateCommandLineParser(test2);
        assertEquals("more [options] file...", parser.getParameter().getUsage());
        options = parser.getParameter().getOptions();
        assertEquals("d", options.get(0).getShortName());
        assertEquals("V", options.get(1).getShortName());

        parser = ParserGenerator.generateCommandLineParser(Test3.class);
        options = parser.getParameter().getOptions();
        assertEquals("t", options.get(0).getShortName());
        assertEquals("e", options.get(1).getShortName());

    }
}

@CommandDefinition(name = "test", description = "a simple test")
class Test1 {
    @Option(shortName = 'f', name = "foo", description = "enable foo")
    private String foo;

    @Option(shortName = 'e', description = "enable e", required = true)
    private String e;

    @Option(description = "has enabled bar", hasValue = false)
    private Boolean bar;

}

@CommandDefinition(name = "test", description = "more [options] file...")
class Test2 {

    @Option(description = "display help instead of ring bell")
    private String display;

    @Option(shortName = 'V', description = "output version information and exit")
    private String version;
}

@CommandDefinition(name = "test", description = "more [options] file...")
class Test3 {

    @Option(name = "target", description = "target directory")
    private String target;

    @Option(shortName = 'e', description = "test run")
    private String test;
}

