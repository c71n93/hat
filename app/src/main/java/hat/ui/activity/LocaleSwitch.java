package hat.ui.activity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class LocaleSwitch {
    private final List<Locale> locales;

    public LocaleSwitch(final Locale... locales) {
        this.locales = Arrays.asList(locales);
    }

    public void toggle() {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(this.nextLocale()));
    }

    private Locale nextLocale() {
        final Locale current = this.currentLocale();
        for (int i = 0; i < this.locales.size(); i++) {
            if (this.locales.get(i).equals(current)) {
                return this.locales.get((i + 1) % this.locales.size());
            }
        }
        return this.locales.get(0);
    }

    private Locale currentLocale() {
        final LocaleListCompat locales = AppCompatDelegate.getApplicationLocales();
        if (locales.isEmpty()) {
            return Locale.getDefault();
        }
        final Locale current = locales.get(0);
        return current == null ? Locale.getDefault() : current;
    }
}
