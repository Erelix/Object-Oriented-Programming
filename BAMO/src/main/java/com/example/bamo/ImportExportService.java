package com.example.bamo;

import java.io.IOException;

public interface ImportExportService {
    void importData(String fileName) throws IOException;
    void exportData(String fileName) throws IOException;
}
