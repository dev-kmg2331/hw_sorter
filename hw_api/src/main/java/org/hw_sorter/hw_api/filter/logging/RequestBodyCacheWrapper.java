package org.hw_sorter.hw_api.filter.logging;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RequestBodyCacheWrapper extends HttpServletRequestWrapper {
    private final ByteArrayInputStream byteArrayInputStream;

    public RequestBodyCacheWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream is = super.getInputStream();
        byteArrayInputStream = new ByteArrayInputStream(is.readAllBytes());
    }

    @Override
    public ServletInputStream getInputStream() {
        byteArrayInputStream.reset();
        return new RequestBodyInputStream(byteArrayInputStream);
    }

    @Override
    public BufferedReader getReader() {
        byteArrayInputStream.reset();
        return new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
    }


    private static class RequestBodyInputStream extends ServletInputStream {

        private final InputStream is;

        public RequestBodyInputStream(InputStream bis) {
            is = bis;
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return is.read(b);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }
    }
}
