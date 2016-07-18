/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.tranql.connector.jdbc;

import java.sql.SQLException;
import java.util.Collection;

import org.tranql.connector.ExceptionSorter;

/**
 * @version $Rev: 800 $ $Date: 2010-11-02 21:14:25 +0100 (Tue, 02 Nov 2010) $
 */
public class ConfigurableSQLStateExceptionSorter implements ExceptionSorter {

    private final Collection allowed;

    public ConfigurableSQLStateExceptionSorter(Collection allowed) {
        this.allowed = allowed;
    }

    public boolean isExceptionFatal(Exception e) {
        if (e instanceof SQLException) {
            SQLException se = (SQLException) e;
            String sqlState = se.getSQLState();
            return checkSQLState(sqlState);
        }
        //If we don't know about it, assume it's fatal
        return true;
    }

    protected boolean checkSQLState(String sqlState) {
        return !allowed.contains(sqlState);
    }

    public boolean rollbackOnFatalException() {
        //we can't be sure we've covered all bases here.
        return true;
    }
}
