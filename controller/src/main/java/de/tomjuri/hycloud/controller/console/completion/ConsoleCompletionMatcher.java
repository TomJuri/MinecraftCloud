/*
 * Copyright 2019-2023 CloudNetService team & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tomjuri.hycloud.controller.console.completion;

import org.jline.reader.Candidate;
import org.jline.reader.CompletingParsedLine;
import org.jline.reader.CompletionMatcher;
import org.jline.reader.LineReader;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ConsoleCompletionMatcher implements CompletionMatcher {

    private volatile List<Candidate> candidates;
    private volatile CompletingParsedLine parsedLine;

    @Override
    public void compile(Map<LineReader.Option, Boolean> options, boolean prefix, CompletingParsedLine line, boolean caseInsensitive, int errors, String originalGroupName) {
        this.candidates = null;
        this.parsedLine = line;
    }

    @Override
    public List<Candidate> matches(List<Candidate> candidates) {
        this.candidates = candidates;
        return candidates;
    }

    @Override
    public Candidate exactMatch() {
        // keep a local copy of the variables in case of concurrent calls
        var candidates = Objects.requireNonNull(this.candidates);
        var parsedLine = Objects.requireNonNull(this.parsedLine);

        // check if there is a 100% match
        var givenWord = parsedLine.word();
        for (var candidate : candidates) {
            if (candidate.complete() && givenWord.equalsIgnoreCase(candidate.value())) {
                return candidate;
            }
        }

        // no exact match
        return null;
    }

    @Override
    public String getCommonPrefix() {
        // keep a local copy of the candidates in case of concurrent calls
        var candidates = Objects.requireNonNull(this.candidates);

        String commonPrefix = null;
        for (var candidate : candidates) {
            if (candidate.complete()) {
                if (commonPrefix == null) {
                    // no common prefix yet
                    commonPrefix = candidate.value();
                } else {
                    // get the common prefix
                    int minLength = Math.min(commonPrefix.length(), candidate.value().length());
                    for (int i = 0; i < minLength; i++) {
                        if (commonPrefix.charAt(i) != candidate.value().charAt(i)) {
                            return commonPrefix.substring(0, i);
                        }
                    }
                    commonPrefix = commonPrefix.substring(0, minLength);
                }
            }
        }
        return commonPrefix;
    }
}
