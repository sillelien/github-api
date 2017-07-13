package org.kohsuke.github;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author Stephen Connolly
 */
public class PayloadRule implements TestRule {

    private final String type;

    private Class<?> testClass;

    private String resourceName;

    public PayloadRule(String type) {
        this.type = type;
    }

    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Payload payload = description.getAnnotation(Payload.class);
                resourceName = payload == null ? description.getMethodName() : payload.value();
                testClass = description.getTestClass();
                try {
                    base.evaluate();
                } finally {
                    resourceName = null;
                }
            }
        };
    }

    public InputStream asInputStream() throws FileNotFoundException {
        String name = resourceName.startsWith("/")
                ? resourceName + type
                : testClass.getSimpleName() + "/" + resourceName + type;
        InputStream stream = testClass.getResourceAsStream(name);
        if (stream == null) {
            throw new FileNotFoundException(String.format("Resource %s from class %s", name, testClass));
        }
        return stream;
    }

    public byte[] asBytes() throws IOException {
        try (InputStream input = asInputStream()){
            return Util.toByteArray(input);
        }
    }

    public String asString(Charset encoding) throws IOException {
        return new String(asBytes(), encoding.name());
    }

    public String asString(String encoding) throws IOException {
        return new String(asBytes(), encoding);
    }

    public String asString() throws IOException {
        return new String(asBytes(), Charset.defaultCharset().name());
    }

    public Reader asReader() throws FileNotFoundException {
        return new InputStreamReader(asInputStream(), Charset.defaultCharset());
    }

    public Reader asReader(String encoding) throws IOException {
        return new InputStreamReader(asInputStream(), encoding);
    }
    public Reader asReader(Charset encoding) throws FileNotFoundException {
        return new InputStreamReader(asInputStream(), encoding);
    }
}
