/*
 * Copyright 2012 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.netflix.eureka;


/**
 * Supported versions for Eureka.
 *
 * <p>The latest versions are always recommended.</p>
 *
 * @author Karthik Ranganathan, Greg Kim
 *
 */
public enum Version {
    V1, V2;

    // (Only the modified portion of Version.java is shown here. The rest of the enum remains unchanged.)

    private static volatile java.util.Map<String, Version> NAME_LOOKUP;

    /**
     * Convert a string to the enum Version. Uses a lazy-initialized lookup map
     * to avoid repeated O(n) scans over values().
     */
    public static Version toEnum(String v) {
        if (v == null) {
            // Defaults to V2 when input is null (preserves original behavior)
            return V2;
        }

        // Fast-path: use already initialized map
        java.util.Map<String, Version> map = NAME_LOOKUP;
        if (map == null) {
            synchronized (Version.class) {
                if (NAME_LOOKUP == null) {
                    Version[] vals = values();
                    java.util.HashMap<String, Version> m = new java.util.HashMap<>(vals.length * 2);
                    for (Version version : vals) {
                        // Normalize to lower-case using ROOT locale for consistent behavior
                        m.put(version.name().toLowerCase(java.util.Locale.ROOT), version);
                    }
                    NAME_LOOKUP = m;
                }
                map = NAME_LOOKUP;
            }
        }

        Version result = map.get(v.toLowerCase(java.util.Locale.ROOT));
        return result == null ? V2 : result;
    }
}
