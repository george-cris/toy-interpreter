package interpreter.repository;

import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> programs = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState program) {
        this(program, null);
    }

    public Repository(PrgState program, String logFilePath) {
        programs.add(program);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(0);
    }

    @Override
    public void logPrgStateExec() throws RepositoryException {
        if (logFilePath == null) return;
        try {
            Files.writeString(Path.of(logFilePath), getCrtPrg().toLogString() + "\n",
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException | InvalidPathException e) {
            throw new RepositoryException("cannot write the log file " + logFilePath + ": " + e.getMessage());
        }
    }
}
