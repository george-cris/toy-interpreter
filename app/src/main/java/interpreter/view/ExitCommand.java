package interpreter.view;

public class ExitCommand extends Command {
    private final Runnable exit;
    public ExitCommand(String key, String description, Runnable exit) {
        super(key, description);
        this.exit = exit;
    }
    @Override
    public void execute() {
        exit.run();
    }
}
