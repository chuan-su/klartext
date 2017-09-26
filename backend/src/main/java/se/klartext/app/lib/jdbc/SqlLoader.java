package se.klartext.app.lib.jdbc;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class SqlLoader {

    private final Class<?> klass;

    public SqlLoader(Class<?> klass) {
        this.klass = klass;
    }

    public String load(String name) {
        URL resource = Resources.getResource(klass, String.format("%s.sql",name));
        try {
            return Resources.toString(resource, Charsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
