package com.intuit.players.service;

import java.io.IOException;
import java.io.InputStream;

public interface DataLoaderService {
    void loadData(InputStream inputStream) throws IOException;

}
