package core.basesyntax.service;

import java.util.List;

public interface FileReaderService {
    List<String> lines(String filePath);
}
