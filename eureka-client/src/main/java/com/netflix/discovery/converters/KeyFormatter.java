package com.netflix.discovery.converters;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.netflix.discovery.EurekaClientConfig;

/**
 * Due to backwards compatibility some names in JSON/XML documents have to be formatted
 * according to a given configuration rules. The formatting functionality is provided by this class.
 *
 * @author Tomasz Bak
 */
@Singleton
public class KeyFormatter {

    public static final String DEFAULT_REPLACEMENT = "__";

    private static final KeyFormatter DEFAULT_KEY_FORMATTER = new KeyFormatter(DEFAULT_REPLACEMENT);

    private final String replacement;

    public KeyFormatter(String replacement) {
        this.replacement = replacement;
    }

    @Inject
    public KeyFormatter(EurekaClientConfig eurekaClientConfig) {
        if (eurekaClientConfig == null) {
            this.replacement = DEFAULT_REPLACEMENT;
        } else {
            this.replacement = eurekaClientConfig.getEscapeCharReplacement();
        }
    }

    public String formatKey(String keyTemplate) {
        // Use charAt loop to avoid creating a temporary char[] from toCharArray().
        int len = keyTemplate.length();
        StringBuilder sb = new StringBuilder(len + 1);

        String repl = this.replacement;
        boolean replIsNull = (repl == null);
        int replLen = replIsNull ? 4 : repl.length(); // "null" length is 4 when repl == null

        for (int i = 0; i < len; i++) {
            char c = keyTemplate.charAt(i);
            if (c == '_') {
                if (replIsNull) {
                    sb.append("null");
                } else if (replLen == 1) {
                    sb.append(repl.charAt(0));
                } else if (replLen == 0) {
                    // append nothing for empty replacement
                } else {
                    sb.append(repl);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static KeyFormatter defaultKeyFormatter() {
        return DEFAULT_KEY_FORMATTER;
    }
}
