package com.uam.pracowniaprogramowaniaprojekt.service;

import java.io.IOException;

public interface ImportExportDataService {

    String exportData() throws IOException;

    void importData(String data) throws IOException;
}
