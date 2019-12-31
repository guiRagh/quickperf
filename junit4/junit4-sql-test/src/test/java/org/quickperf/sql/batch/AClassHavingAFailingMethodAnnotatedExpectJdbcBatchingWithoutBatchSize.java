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

package org.quickperf.sql.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quickperf.junit4.QuickPerfJUnitRunner;
import org.quickperf.sql.SqlTestBaseJUnit4;
import org.quickperf.sql.annotation.ExpectJdbcBatching;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Properties;

import static org.quickperf.sql.config.HibernateConfigBuilder.anHibernateConfig;

@RunWith(QuickPerfJUnitRunner.class)
public class AClassHavingAFailingMethodAnnotatedExpectJdbcBatchingWithoutBatchSize extends SqlTestBaseJUnit4 {

    @Override
    protected Properties getHibernateProperties() {
        return   anHibernateConfig()
                .withBatchSize(30)
                .build();
    }

    @Test
    @ExpectJdbcBatching()
    public void execute_a_not_batched_query() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        String nativeQuery = "INSERT INTO Book (title,id) VALUES ('Book title',1200)";
        Query query = entityManager.createNativeQuery(nativeQuery);
        query.executeUpdate();

        transaction.commit();
    }

}