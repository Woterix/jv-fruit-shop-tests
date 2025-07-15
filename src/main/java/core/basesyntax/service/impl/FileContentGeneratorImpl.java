package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileContentGenerator;
import java.util.stream.Collectors;

public class FileContentGeneratorImpl implements FileContentGenerator {
    private static final String FILE_HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String contentGen() {
        if (Storage.STORAGE.isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }
        String result = Storage.STORAGE.entrySet().stream()
                .map(s -> s.getKey() + COMMA + s.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
        return FILE_HEADER + System.lineSeparator() + result;
    }
}
