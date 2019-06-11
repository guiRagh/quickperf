/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2019-2019 the original author or authors.
 */

package org.quickperf.sql.select.exactlysame;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.experimental.results.PrintableResult;

public class DisableExactlySameSqlSelectTest {

    @Test public void
    should_not_fail_if_exactly_same_select_with_diff_param_values() {

        // GIVEN
        Class<?> testClass = AClassHavingAMethodAnnotatedWithDisableSameSqlForDifferentParamValues.class;

        // WHEN
        PrintableResult printableResult = PrintableResult.testResult(testClass);

        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(printableResult.failureCount())
                      .isEqualTo(0);
        softAssertions.assertAll();

    }

    @Test public void
    should_fail_if_same_selects_with_same_params_and_test_method_annotated_disable_same_sql_select() {

        // GIVEN
        Class<?> testClass = AClassHavingAMethodAnnotatedWithDisableSameSqlSelectWithSameParams.class;

        // WHEN
        PrintableResult printableResult = PrintableResult.testResult(testClass);

        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(printableResult.failureCount())
                      .isEqualTo(1);
        softAssertions.assertThat(printableResult.toString())
                      .contains("[PERF] Exactly same SELECT requests");
        softAssertions.assertAll();

    }

    @Test public void
    should_fail_if_two_same_selects_without_params_and_test_method_annotated_disable_same_sql_select() {

        // GIVEN
        Class<?> testClass = AClassHavingAMethodAnnotatedWithDisableSameSqlSelectWithoutParams.class;

        // WHEN
        PrintableResult printableResult = PrintableResult.testResult(testClass);

        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(printableResult.failureCount())
                      .isEqualTo(1);
        softAssertions.assertThat(printableResult.toString())
                      .contains("Exactly same SELECT requests");
        softAssertions.assertAll();

    }

    @Test public void
    should_pass_if_two_inserts_and_test_annotated_with_disable_same_select_sql() {

        // GIVEN
        Class<?> testClass = AClassHavingAMethodAnnotatedWithDisableSameSqlSelectWithInsertQueries.class;

        // WHEN
        PrintableResult printableResult = PrintableResult.testResult(testClass);

        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(printableResult.failureCount())
                      .isEqualTo(0);
        softAssertions.assertAll();

    }

}