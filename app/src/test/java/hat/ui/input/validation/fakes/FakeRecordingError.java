package hat.ui.input.validation.fakes;

import hat.ui.input.error.InputError;
import java.util.Optional;

public final class FakeRecordingError implements InputError {
    private int times;
    private Optional<String> message = Optional.empty();

    @Override
    public void show() {
        this.times++;
        this.message = Optional.of("");
    }

    @Override
    public void show(final String msg) {
        this.times++;
        this.message = Optional.of(msg);
    }

    public int timesShown() {
        return this.times;
    }

    public Optional<String> lastMessage() {
        return this.message;
    }
}
