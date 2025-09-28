package org.example.util;

import java.io.*;
import java.nio.file.*;

public class Csv implements AutoCloseable {
    private final PrintWriter out;

    public Csv(String path, String header) throws IOException {
        Path p = Paths.get(path);
        Path dir = p.getParent();
        if (dir != null) Files.createDirectories(dir);
        boolean isNew = Files.notExists(p);
        out = new PrintWriter(new BufferedWriter(new FileWriter(p.toFile(), true)));
        if (isNew && header != null) out.println(header);
    }

    public void row(Object... cells) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            if (i > 0) sb.append(',');
            String s = String.valueOf(cells[i]).replace("\"", "\"\"");
            if (s.contains(",") || s.contains("\"") || s.contains("\n")) sb.append('"').append(s).append('"');
            else sb.append(s);
        }
        out.println(sb.toString());
        out.flush();
    }

    @Override public void close() { out.close(); }
}

